#include<stdio.h>
#include<string.h>
#define LINE_LENGTH_MAX 1000
/*
Contents:
根据当前目录下的model.js
内的IMPORT内容打包到
./build/app.js内 
*/

/*关闭文件 */
void fileClose(FILE*filePointer){
	fclose(filePointer);
}

/*初始化app.js */ 
void InitApp(FILE*app){
	printf(">>初始化app.js\n");
	const char*LANG1="window.SlagoModel={};";
	int LANG1_Length=strlen(LANG1);
	int i=0;
	for(i=0;i<LANG1_Length;i++){
		fputc(LANG1[i], app);
	}
}

/*去除字符串开头的空格*/
void DeleteLeftBlankSpace(char*Line){
	if(Line==NULL){return;}
	int LineLength=strlen(Line);
	if(LineLength<=1){return;}//空字符串"\0"
	int noBlank=-1;
	int i=0;
	for(i=0;i<LineLength;i++){
		if(Line[i]!=' '){
			noBlank=i;
			break;
		}
	}
	if(noBlank==-1){//这一行全为空格 
		Line[0]='\0';
	}else{//去除前面的空格 
		int before=0;
		int i=noBlank;
		while(Line[i]!='\0'){
			Line[before++]=Line[i++];
		}
		Line[before]='\0';
	}
}


/*判断一个字符串是否为另一个字符串的开头子串*/
int StringLeftChildTest(char*Line,const char*KeyWord){
	if(strlen(Line)<strlen(KeyWord)){
		return 0;
	}
	int result=1;
	int i_Line=0;
	int j_Line=0;
	while(i_Line<strlen(Line)&&j_Line<strlen(KeyWord)){
		if(Line[i_Line++]!=KeyWord[j_Line++]){
			result=0;
		}
	}
	return result;
}

void FilePushToApp(const char*file,FILE*app){
	FILE*fp=fopen(file,"r");
	if(fp==NULL){printf(">>打开%s失败\n",file);return;}
	/*将file文件内的内容追加到app.js*/
	char buffer[LINE_LENGTH_MAX]={'\0'};
	/*将文件路径追加到app.js相应代码之前*/
	fputs("\n//=>",app);
	fputs(file,app);
	fputc('\n',app);
	while( fgets(buffer, LINE_LENGTH_MAX, fp) != NULL ) {
    	fputs(buffer,app);
    }
    //关闭打开的文件
	fileClose(fp);
}

/*关键词检测*/
int KeyWordTest(char*Line,FILE*app){
	/*
	KeyWords List
	return      keyword
	0             none
	1             IMPORT
	2             SlagoModel
	3             //
	*/
	if(StringLeftChildTest(Line,"\\\0")){
		//检测是否为注释
		return 3; 
	}
	if(StringLeftChildTest(Line,"IMPORT\0")){
		//提取文件路径
		/*
			IMPORT("./js/FindPage/Header.js");
			找到第一个 " 向后迭代存储 直到第二个 " 
		*/
		int i=0;
		int flag=0;
		char buffer[LINE_LENGTH_MAX];
		int before=0;
		while(i<strlen(Line)){
			if(Line[i]=='"'&&flag){
				break;//提取完毕出口 
			}
			if(flag){
				buffer[before++]=Line[i];
			}
			if(Line[i]=='"'){
				flag=1;
			}
			i++;
		}
		buffer[before]='\0';
		/*写入文件*/
		FilePushToApp(buffer,app);
		return 1;
	}
	if(StringLeftChildTest(Line,"SlagoModel\0")){
		//直接将这一行数据写入到app.js
		fputc('\n',app);
		fputs(Line, app);
		return 2;
	}
	return 0;//default
}

/*读取分析model.js*/
void ReadModel(FILE*model,FILE*app){
	char Line[LINE_LENGTH_MAX];//每行最多1000字符
	/*KeyWords List
		IMPORT SlagoModel
	*/
	//逐行读取model.js
	//循环读取文件的每一行数据
	int min=6;//keyword min length is 6
    while( fgets(Line, LINE_LENGTH_MAX, model) != NULL ) {
    	int LineLength=strlen(Line);
    	DeleteLeftBlankSpace(Line);
        if(strlen(Line)<min+1){//不处理这一行，整体长度不满足最短keyword length 
        	continue;
		}else{//keyword test
			int result=KeyWordTest(Line,app);
		}
    } 
}
int main(int argc,char**argv){
	FILE*model=fopen("./js/model.js","r");
	if(model!=NULL){
		printf(">>model.js打开成功\n");
	}else{
		printf(">>ERROR:model.js打开失败\n");
		return -1;
	}
	FILE*app=fopen("./build/app.js","w");
	if(app!=NULL){

		printf(">>./build/app.js打开成功\n");
	}else{
		printf(">>ERROR:./build/app.js打开失败\n");
		return -1;
	}
	InitApp(app);
	ReadModel(model,app);
	fileClose(model);
	fileClose(app);
	printf(">>打包完毕\n");
	printf(">>");
	char end;
	scanf("%c",&end); 
	return 0;
}
