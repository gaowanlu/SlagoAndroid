import React, { Component,useEffect,useState } from 'react';
import '../../css/FindPage.css';
import axios from 'axios';
import { Link } from 'react-router-dom';
import PostPopWindow from './PostPopWindow';
import PostsStream from './PostStream';


function FindPage(props){
    //左侧choose bar状态
    const [chooseBarState,setChooseBarState]=useState([
        {index:1,name:'推荐',choosed:true},
        {index:2,name:'关注',choosed:false},
        {index:3,name:'热点',choosed:false},
        {index:4,name:'娱乐',choosed:false},
        {index:5,name:'知识',choosed:false},
        {index:6,name:'二次元',choosed:false},
    ]);//默认为推荐

    //现在选中的choose bar
    const [nowChoosed,setNowChoosed]=useState("推荐");
    const [postlist,setPostlist]=useState([]);
    //popWindow显示状态
    const [popWindowDisplayStatus,setPopWindowDisplayStatus]=useState(false);
    //需要在悬浮窗口显示的帖子的信息
    const [popWindowPostData,setPopWindowPostData]=useState(undefined);



    //不能在判断、循环、嵌套函数中使用钩子
    //useEffect可以提到单独的函数中去，可以将useEffect做成可复用的
    let lock=true;
    async function getFindPosts(){
        if(lock===true){
            lock=false;
            let response= await axios('/apis/getFindPosts?size=10');
            if(response.data.status===200){
                let temp=[...postlist];
                for(let i=0;i<response.data.result.length;i++){
                    temp.push(response.data.result[i]);
                }
                setPostlist(temp);
            }
        }
        lock=true;
    }

    window.onscroll=()=>{
        //变量scrollTop是滚动条滚动时，距离顶部的距离
        let scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
        //变量windowHeight是可视区的高度
        let windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
        //变量scrollHeight是滚动条的总高度
        let scrollHeight = document.documentElement.scrollHeight||document.body.scrollHeight;
        //滚动条到底部的条件:加载更多条件，提前加载，免得用户等待加载
        if(scrollTop+windowHeight>=scrollHeight-200){
            if(lock===true){
                getFindPosts()
            }
        }
    }

    useEffect(()=>{
        //发起axios请求获取推荐列表
        getFindPosts();
        return ()=>{
            window.onscroll=null;//组件被撤去时停止下滑加载
        }
    },[]);

    //左侧点击选择分类事件
    const choose=(e)=>{
        setNowChoosed(e.target.innerText);
        let barState=[...chooseBarState];
        barState.forEach(obj=>{
            if(obj.name===e.target.innerText){
                obj.choosed=true;
            }else{
                obj.choosed=false;
            }
        })
        setChooseBarState(barState);
    }

    //改变帖子详情悬浮窗口的显示状态
    const popWindowChange=(postData=undefined)=>{
        setPopWindowDisplayStatus(!popWindowDisplayStatus);
        if(postData!==undefined){//更新需要查看的帖子的信息,以便通过props传递给PostPopWindow
            setPopWindowPostData(postData);
            //console.log(postData);
        }
    }
    
    return(<React.Fragment>
        <div className="FindPage-container">
            
            {popWindowDisplayStatus&&<PostPopWindow postData={popWindowPostData} closeCallback={popWindowChange}/>}

            <div className="FindPage-left-container">
                <div className="FindPage-chooselist-container" >
                    {
                        chooseBarState.map(item=>{
                            //选中效果
                            let blockClassName="slago-hover-p ";
                            blockClassName+=item.choosed?"FindPage-left-block-choosed":"FindPage-left-block";
                            return <React.Fragment key={item.index}>
                                <div className={blockClassName} onClick={choose}>
                                    {item.name}
                                </div>
                                {item.index%3===0&&<div className="FindPage-left-block-line"></div>}
                            </React.Fragment>;
                        })
                    }
                    <div style={{width:'100%',height:'150px'}}></div>
                </div>
            </div>
            <div className="FindPage-right-container">
                <PostsStream clickPost={(postData)=>{popWindowChange(postData)}} postlist={postlist}/>
            </div>
        </div>
    </React.Fragment>);
}
export default FindPage;