import React, { Component } from 'react';
import '../../css/PostUpLoad.css';
import ImgLoader from './ImgLoader';
import axios from 'axios';
import { Suspension } from '../comon/Suspension';
function checkContent(imgs){
    return imgs.length>0;
}
class PostUpLoad extends Component {
    state = { 

    }
    //图片加载器回调
    ImgLoaderCallback=(list)=>{
        this.imgValueList=list;
        console.log(this.imgValueList);
    };
    toUpload=(e)=>{
        let fontContent=this.textareaDOM.value;
        let formData=new FormData();
        if(!checkContent(this.imgValueList)){
            alert("最少上传一张图片");
            return;
        }
        //检测content与img的要求是否符合要求
        formData.append("content",fontContent);
        for(let i=0;i<this.imgValueList.length;i++){
            formData.append("img"+i.toString(),this.imgValueList[i].value);
        }
        Suspension.showLoading();//显示加载状态
        //发起ajax请求
        axios.request({
            url:"/apis/uploadpost",
            method:'post',
            header:{'Content-Type': 'multipart/form-data'},
            data:formData
        }).then(response=>{
            if(response.data.result&&response.data.result===true){
                //console.log("上传成功");
                this.props.replace("/UserPage");
            }
            Suspension.clear();
        }).catch(error=>{//上传失败
            Suspension.clear();
        })
    }
    constructor(){
        super();
        this.imgValueList=[];
        this.textareaDOM=null;
    }
    render() { 
        return (<div className="slago-width-100" style={{padding:'10px'}}>
            <textarea ref={el=>this.textareaDOM=el} className="slago-width-100 PostUpLoad-textarea" placeholder="分享美好的瞬间">
            </textarea>
            <ImgLoader ImgLoaderCallback={this.ImgLoaderCallback}/>
            <div className="slago-flex slago-ju-r" style={{width:'100%',padding:'10px'}} >
                <div onClick={this.toUpload} className="slago-flex-jc-ac slago-hover-p  slago-div-bs" style={{width:'70px',height:'40px',borderRadius:'25px',
                    backgroundColor:'rgb(245, 99, 99)',color:'#fafafa',fontSize:'1.1em'}}>上传</div>
            </div>
        </div>
        );
    }
}
    
export default PostUpLoad;
