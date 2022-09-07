import React, { Component } from 'react';
//图片动态加载器
class ImgLoader extends Component {
    state = { 
        InputValues:[
            {num:1,value:null,imgurl:"/img/add.png",clickState:'add',inputdom:null}
        ],
        index:1
    }

    stateOnChange=()=>{
        let returnList=[...this.state.InputValues].filter(o=>o.clickState!=='add');
        this.props.ImgLoaderCallback(returnList);
    }

    inputClick=(d)=>{
        let {InputValues,index}=this.state;
        //找到点击的那一条信息
        let v=InputValues.filter(o=>o.num===d.num);
        //检测clickState状态
        if(v[0].clickState==='add'){
            //为input绑定事件
            v[0].inputdom.onchange=()=>{
                let flag=false;
                //检测是否输入了文件
                if(v[0].inputdom.files!==undefined&&v[0].inputdom.files.length>0&&v[0].inputdom.files&&v[0].inputdom.files[0]){
                    if(v[0].inputdom.files[0].getAsDataURL){
                        v[0].imgurl=v[0].inputdom.files[0].getAsDataURL;
                    }else{
                        v[0].imgurl=window.URL.createObjectURL(v[0].inputdom.files[0]);
                    }
                    v[0].value=v[0].inputdom.files[0];
                    flag=true;
                }else if(v[0].inputdom.value){
                    v[0].imgurl=v[0].inputdom.value;
                    v[0].value=v[0].inputdom;
                    flag=true;
                }
                if(flag){
                    //新增一个按钮
                    if(InputValues.length<6){
                        index++;
                        InputValues.push({num:index,value:null,imgurl:"/img/add.png",clickState:'add',inputdom:null});
                    }
                    v[0].clickState='del';//改变点击状态
                    this.setState({InputValues,index});//改变图片显示状态
                }
            }
            v[0].inputdom.click();
        }else if(v[0].clickState==='del'){//应该删除这张图片
            let temp=InputValues.filter(o=>o.num!==v[0].num);
            //检测最后一个是否为add状态如果不是则新添一个
            if(temp.length===0||temp[temp.length-1].clickState!=='add'){
                index++;
                temp.push({num:index,value:null,imgurl:"/img/add.png",clickState:'add',inputdom:null});
            }
            this.setState({InputValues:temp,index});
        }
    }

    componentDidUpdate(){
        this.stateOnChange();//通知上级
    }

    render() { 
        return (<React.Fragment>
            <div className="slago-width-100 slago-flex-w slago-ju-sa" style={{}}>
                {
                    this.state.InputValues.map(d=>{
                        return(
                        <div key={d.num} className="slago-flex-jc-ac slago-div-bs slago-div-br slago-hover-p" style={{width:'130px',height:'130px',margin:'10px'}}>
                            <img  onClick={()=>this.inputClick(d)} className="slago-div-br" 
                            style={{width:'130px',height:'130px',objectFit:'cover'}}
                             src={d.imgurl} alt="图"/>
                            <input type="file" ref={el=>d.inputdom=el} accept="image/*" style={{display:'none'}}/>
                        </div>);
                    })
                }
            </div>
        </React.Fragment>);
    }
}
 
export default ImgLoader;