import React, { Component } from 'react';
import Joi from 'joi';
import axios from 'axios';

//邮箱验证
let email_schema=Joi.object({
    value:Joi.string().trim().min(4).max(100).required().email({ tlds: { allow: false }}).error(new Error("邮箱格式不符合要求")),
    status:Joi.string()
});

const RightEmailStatus="请点击下一步";//可以通行的status


//第一步邮箱输入：动态验证邮箱是否正确与账号已存在并提醒
class Step1 extends Component{
    state={
        email:{value:this.props.email,status:''}
    }
    callback=()=>{
        if(this.state.email.status===RightEmailStatus){
            this.props.callback(this.state.email.value);
        }
    }
    inputChange=(e)=>{
        let {email}=this.state;
        email.value=e.currentTarget.value;
        this.setState({email});
        try{
            const value=email_schema.validate(email);
            let message=value.error&&value.error.message?value.error.message.toString():"邮箱格式合法";
            email.status=message;
            if(message==="邮箱格式合法"){
                axios.get("/CheckUser?email="+email.value).then(rp=>{
                    if(rp.data.result===true){
                        email.status=RightEmailStatus;
                    }else{
                        email.status="邮箱未被注册";
                    }
                    this.setState({email});
                });
            }else{
                this.setState({email});                
            }
            //如果符合邮箱格式、发起ajax请求 RightEmailStatus
            //判断此邮箱是否可以注册新账号：获得请求后的消息更新status为可以注册,不可注册则提示此邮箱已注册
        }catch(e){console.log(e);}
    }
    render(){
        let emailStatusBackgroundColor='rgb(245, 99, 99)';//主题色不通行
        if(this.state.email.status===RightEmailStatus){
            emailStatusBackgroundColor='rgb(99,175,99)';//绿色通行
        }
        return(
            <React.Fragment>
                <img style={{width:'150px',height:'150px'}} src="/img/qiutian.png" alt=""/>
                <div className="slago-width-100 slago-flex-jc">
                    <h2 style={{color:'#707070',fontWeight:'bold'}}>找回密码</h2>
                </div>
                <div className="slago-width-100 slago-flex-jc slago-flex-w" style={{marginTop:'70px'}}>
                    <div className="slago-flex-jc-ac slago-width-100" style={{height:'40px',fontSize:'1.1em'}}>
                        <div style={{maxWidth:'300px',paddingLeft:'5px'}} className="slago-flex-js slago-width-100 slago-over-h">
                            电子邮箱
                        </div>
                    </div>
                    <div className="slago-flex-jc slago-width-100">
                        <input value={this.state.email.value} onChange={this.inputChange} 
                        style={{width:'100%',maxWidth:'300px',height:'40px',textAlign:'center'}} type="email"/>
                    </div>
                    {/*动态提示email的状态*/}
                    {this.state.email.status!==""&&
                        <div style={{backgroundColor:emailStatusBackgroundColor,marginTop:'5px',maxWidth:'300px',height:'30px',color:'#fafafa'}} 
                            className="slago-flex-jc-ac slago-width-100 slago-div-br">
                            {this.state.email.status}
                        </div>
                    }
                    <div className="slago-width-100 slago-flex-jc-ac" style={{marginTop:'50px'}}>
                        <div className="slago-flex-jc-ac slago-hover-p" onClick={this.callback} style={{width:'150px',height:'40px'
                        ,borderRadius:'20px',color:'#fafafa',backgroundColor:'rgb(245, 99, 99)'}}>下一步</div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default Step1;