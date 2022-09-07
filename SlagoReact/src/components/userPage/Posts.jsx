import React, { Component } from 'react';
import axios from 'axios';
import cookies from 'react-cookies';
import PostBlock from './PostBlock';
import StateBar from './StateBar';
import PageTurner from './PageTurner';
class Posts extends Component {
    state = {
        postslist:[

        ],
        PageTurnerState:{
            countPage:7,
            nowPage:1
        }
    }
    componentDidMount(){
        this.updatePostsList();
    }
    //更新显示内容列表
    updatePostsList=()=>{
        axios.get(`/apis/getUserAllPost?num=8&page=${this.state.PageTurnerState.nowPage}&userid=`+this.props.id).then(rp=>{
            let postslist=[];
            let {PageTurnerState}=this.state;
            for(let i=0;i<rp.data.list.length;i++){
                postslist.push(rp.data.list[i]);
            }
            let countPage=Math.ceil(rp.data.countPostNum/8);//每页有8个
            PageTurnerState.countPage = countPage;
            this.setState({postslist,PageTurnerState});
        });
    }
    componentDidUpdate(prevProps, prevState){
        //console.log([prevProps, prevState]);
        if(prevState.PageTurnerState.nowPage!==this.state.PageTurnerState.nowPage){
            this.updatePostsList();
        }
    }
    PageTurnerCallBack=(message)=>{
        let PageTurnerState={...this.state.PageTurnerState};
        if(message==='left'){
            --PageTurnerState.nowPage;
        }else if(message==='right'){
            ++PageTurnerState.nowPage;
        }
        window.stop();
        this.setState({PageTurnerState});
    }
    render() { 
        return ( <React.Fragment>
            <div className="UserPage-content-container animate__animated animate__fadeIn">
                <StateBar placeholder="内容/日期" buttonText="搜索" nowState="博客"/>
                <div style={{width:'100%',padding:'1px',display:'flex',flexWrap:'wrap',justifyContent:'space-around'}}>
            {
                this.state.postslist.map(v=>{
                    return <PostBlock key={v} postid={v}/>
                })
            } 
                </div>
                <PageTurner callBack={this.PageTurnerCallBack} data={this.state.PageTurnerState}/>
            </div>

        </React.Fragment> );
    }
}
 
export default Posts;