import axios from 'axios';
import { Link } from 'react-router-dom';
import React, {useEffect,useState } from 'react';

export default function PostCard(props){
    const [imgs,setImgs]=useState([""]);
    const [postData,setpostData]=useState(null);
    //发起axios请求获取帖子详细信息
    /* 
    collectionNum: 0
    collectioned: false
    commentNum: 0
    imgs: ["101"]
    likeNum: 0
    liked: false
    postdate: "2021-10-05 10:24:57"
    posttext: ""
    status: 200
    userid: "1901420313"
    */
    async function getPostDetail(){
        let response= await axios('/apis/getPostData?postid='+props.postid);
        if(response.data.status===200){
            const temp=[];
            for(let i=0;i<response.data.imgs.length;i++){
                temp.push(response.data.imgs[i]);
            }
            setImgs(temp);
            setpostData(response.data);
        }
    }
    useEffect(()=>{
        getPostDetail();
        return ()=>{
            
        };
    },[]);
    return(
        <div className="FindPage-right-block">
            <img onClick={()=>props.imgClick(postData)} className="FindPage-block-img slago-hover-p" src={"/apis/getPostImg?id="+imgs[0]} alt=""></img>
            <PostTextContentCard postdata={postData}/>
        </div>
    );
}

//帖子卡片的文字及作者信息部分
function PostTextContentCard(props){
    if(props.postdata===null){
        return <div className="FindPage-postCard-content-container"></div>;
    }else{
        return <div className="FindPage-postCard-content-container">
            <div style={{width:'100%',height:'60px',overflow:'hidden'}}>
                <span style={{padding:'5px',textIndent:'28px',letterSpacing:'1px'}}>{props.postdata.posttext}</span>
            </div>
            <div style={{padding:'5px'}}>
                <Link target="_blank" to={{pathname:'/User/'+props.postdata.userid.toString()}}>
                <img className="FindPage-postCard-headimg" src={"/apis/getUserHeadImg?id="+props.postdata.userid} alt={props.postdata.userid}/>
                </Link>
                <span style={{letterSpacing:'2px'}}>{props.postdata.userid}</span>
            </div>
        </div>;
    }
}