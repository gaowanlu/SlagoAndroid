import React, { Component } from 'react';
import axios from 'axios';
import UserRow from './UserRow';
import StateBar from './StateBar';
import PageTurner from './PageTurner';
class About extends Component {
    state = { 
        aboutlist:[],
        PageTurnerState:{
            countPage:1,
            nowPage:1
        }
    }
    PageTurnerCallBack=(message)=>{
        let PageTurnerState={...this.state.PageTurnerState};
        if(message==='left'){
            --PageTurnerState.nowPage;
        }else if(message==='right'){
            ++PageTurnerState.nowPage;
        }
        this.setState({PageTurnerState});
    }
    componentDidMount(){
        axios.get("/apis/aboutlist?page=1&pagesize=10").then(response=>{
            let {aboutlist}=this.state;
            for(let i=0;i<response.data.list.length;i++){
                aboutlist.push({
                    id:response.data.list[i]
                });
                this.setState({aboutlist});
            }
        });
    }
    //取消关注回调事件
    cancelAttention = (userid) => {
      //发起取消关注请求；请求成功后将id为userid的UserRow从列表删除
      //删除这一行
        let { aboutlist } = this.state;
        let filtered = [];
        filtered = aboutlist.filter((obj) => {
            return obj.id !== userid;
        });
        this.setState({ aboutlist:filtered });
        axios.get("/apis/unfollowing?otherId=" + userid).then((rp) => {
            if (rp.data.result!==true) {
                //撤回操作
                this.setState({ aboutlist });
            }
         });
    }
    // 点击用户列表项则进行路由跳转:访问这个用户
    userRowClick = (userid) => {
        console.log("访问这个用户", userid);
        //进行路由跳转
        this.props.toWhere("push", "/User/" + userid);
    }
    render() { 
        return ( 
            <div className="UserPage-content-container  animate__animated animate__fadeIn" >
            {/* 状态栏 */}
            <StateBar placeholder="账号/昵称" buttonText="搜索" nowState="我的关注"/>
            {
                this.state.aboutlist.map(d=>{
                    return (
                      <UserRow
                        key={d.id}
                        id={d.id}
                        userRowClick={this.userRowClick}
                        rightbuttonText="取消关注"
                        cancelAttention={this.cancelAttention}
                      ></UserRow>
                    );
                })
            }
            <PageTurner callBack={this.PageTurnerCallBack} data={this.state.PageTurnerState}/>
        </div>
        );
    }

}
 
export default About;
