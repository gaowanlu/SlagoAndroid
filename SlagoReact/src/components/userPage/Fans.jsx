import React, { Component } from 'react';
import axios from 'axios';
import UserRow from './UserRow';
import StateBar from './StateBar';
import PageTurner from './PageTurner';
class Fans extends Component {
    state = { 
        fanslist:[],
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
        axios.get("/apis/fanslist?page=1&pagesize=10").then(response=>{
            let {fanslist}=this.state;
            for(let i=0;i<response.data.list.length;i++){
                fanslist.push({
                    id:response.data.list[i]
                });
                this.setState({fanslist});
            }
        });
    }
    render() { 
        return ( 
            <React.Fragment>
                <div className="UserPage-content-container  animate__animated animate__fadeIn" >
                <StateBar placeholder="账号/昵称" buttonText="搜索" nowState="我的粉丝"/>
                    {
                        this.state.fanslist.map(d=>{
                            return (
                                <UserRow key={d.id} id={d.id}></UserRow>
                            );
                        })
                    }
                <PageTurner callBack={this.PageTurnerCallBack} data={this.state.PageTurnerState}/>
                </div>
            </React.Fragment>
        );
    }
}
 
export default Fans;