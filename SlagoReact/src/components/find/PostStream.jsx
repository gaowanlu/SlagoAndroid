import React from "react";
import PostCard from './PostCard';
/*右侧四列帖子瀑布流组件*/
//接口
/*
props
props.postlist=["1","2","3"];//数组内的值为帖子的id
*/
export default function PostsStream(props){
    PostsStream.now=0;
    let col=[[],[],[],[]];//四列,如果经过响应式四列成为了一列，则只会讲内容显示到最后一列
    //遍历postlist
    for(let i=0;i<props.postlist.length;i++){
        //需要决定将帖子放到哪一列
        if(window.screen.availWidth<450)
            col[0].push(props.postlist[i]);
        else
            col[PostsStream.now].push(props.postlist[i]);
            PostsStream.now++;
        if(PostsStream.now===4){
            PostsStream.now=0;
        }
    }
    return(<React.Fragment>
        <div className="FindPage-poststream-container">
            <div className="FindPage-poststream-col">
                {col[0].map(
                    (currentValue,index,arr)=>{
                        return (
                            <PostCard imgClick={props.clickPost} postid={currentValue} key={index}/>
                        )
                    }
                )
                }
            </div>
            <div className="FindPage-poststream-col">
            {col[1].map(
                    (currentValue,index,arr)=>{
                        return (
                            <PostCard  imgClick={props.clickPost}  postid={currentValue} key={index}/>
                        )
                    }
                )
                }
            </div>
            <div className="FindPage-poststream-col">
            {col[2].map(
                    (currentValue,index,arr)=>{
                        return (
                            <PostCard  imgClick={props.clickPost}  postid={currentValue} key={index}/>
                        )
                    }
                )
                }
            </div>
            <div className="FindPage-poststream-col">
            {col[3].map(
                    (currentValue,index,arr)=>{
                        return (
                            <PostCard  imgClick={props.clickPost}  postid={currentValue} key={index}/>
                        )
                    }
                )
                }
            </div>
        </div>
    </React.Fragment>);
}