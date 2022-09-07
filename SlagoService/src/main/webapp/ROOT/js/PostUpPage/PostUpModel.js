(function(){
    //建立命名空间
    let namespace={};
    //图片添加点击事件
    namespace.click=function(addDivNode){
    //获得此节点下的input-file
    let input_file=addDivNode.children[1];
    //为input_file监听文件上传事件
    input_file.onchange=function(){
        //获得img标签节点
        let img_node=this.parentNode.children[0];
        if(this.files!=undefined&&this.files.length>0&&this.files&&this.files[0]){
            if(this.files[0].getAsDataURL){
                img_node.src=this.files[0].getAsDataURL;
            }else{
                img_node.src=window.URL.createObjectURL(this.files[0]);
            }
            //更新输入图片style
            //获得真实图片高度与宽度
            let imgSize={
                width:img_node.naturalWidth,
                height:img_node.naturalHeight
            };
            //决策
            if(imgSize.width>=imgSize.height){//横长
                img_node.style.height="100%";
                img_node.style.width="auto";
            }else{//竖长
                img_node.style.width="100%";
                img_node.style.height="auto";
            }


        }else if(input_file.value){
            img_node.src=input_file.value;
        }else{
            //将图片还原为加号
            img_node.src="./img/67.png";
            //还原style
            img_node.style.width="60px";
            img_node.style.height="60px";
        }
    }
    input_file.click();
};
//数据上传事件,发布按钮点击事件
namespace.dataPost=function(dom){
    dom=dom.parentNode;//dom为按钮的父节点
    let img_file_list=[];
    let rows=[dom.parentNode.children[3].children[0],
    dom.parentNode.children[3].children[1] ];
    for(let i=0;i<2;i++){
        let row=rows[i];
        for(let i=0;i<row.children.length;i++){
            img_file_list.push(row.children[i].children[1]);
        }
    }
    //console.log(img_file_list); img_file_list 是input dom list
    //得到6个input标签,到img_file_list
    //遍历input标签
    //创建formData
    let formData=new FormData();
    let now_index=0;
    for(let i=0;i<img_file_list.length;i++){
        if(img_file_list[i].files&&img_file_list[i].files.length>0){
            //推进formData
            formData.append("img"+now_index.toString(),img_file_list[i].files[0]);
            now_index++;
        }else if(img_file_list[i].value){
            //推进formData
            formData.append("img"+now_index.toString(),img_file_list[i]);
            now_index++;
        }
    }
    //判断是否满足上传要求
    if(now_index<=0){//不满足
        alert("发布帖子的要求为：最少分享一张图片");
        return null;
    }
    //获取textarea内容
    let textarea_node=dom.parentNode.children[2].children[0];
    //textarea.value 加入formData
    formData.append("content",textarea_node.value);
    //调用上传属性
    this.ajax(formData);
};
namespace.ajax=function(formData){
    Slago.LoadPage.hover();
    axios.request({
        url:Slago.Data.ServerData.IP+"uploadpost",
        method:'post',
        header:{'Content-Type': 'multipart/form-data'},
        data:formData
    }).then(response=>{
        if(response.data.result&&response.data.result==true) {
            Slago.LoadPage.trans();
            setTimeout("Slago.LoadPage.move();", 500);
            //0.6秒后返回上级
            setTimeout("Slago.LoadPage.trans();setTimeout('Slago.PageStack.pop();',500);", 600);
        }else{
            alert("发布帖子的要求为：最少分享一张图片");
            Slago.LoadPage.move();
        }
    }).catch(error=>{//上传失败
        Slago.LoadPage.move();
    })
};
    //加入模块
    SlagoModel.PostUpPage.PostUpModel=namespace;
})();