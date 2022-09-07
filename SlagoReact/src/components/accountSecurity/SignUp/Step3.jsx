import React, { Component } from 'react';
import axios from 'axios';
//发送验证码确认邮箱身份
class Step3 extends Component {
    state = {
        time:0,
        checkCode:''
    }

    componentDidMount(){
        //发起请求发送验证码
        this.sendCode();
    }

    componentWillUnmount(){
        clearInterval(this.timeCount);
    }

    timeCountFun=()=>{
        let {time}=this.state;
        if(time<=0){
            time=0;clearInterval(this.timeCount);
        }else{
            time--;
        }
        this.setState({time});
    }

    sendCode=()=>{
        if(this.state.time>0){
        }else{
            axios.get("/SendVerificationCode?email="+this.props.email).then(rp=>{});
            //刷新计时
            this.setState({time:35});
            //建定时器
            this.timeCount=setInterval(this.timeCountFun,1000);
        }
    }


    getRetryText=()=>{
        const {time}=this.state;
        if(time>0){
            return time.toString();
        }else{
            return '';
        }
    }

    check=()=>{
        this.props.callback(this.state.checkCode);
    }

    Input=(e)=>{
        this.setState({checkCode:e.currentTarget.value});
    }

    render() { 
        return (<React.Fragment>
            <div className="slago-width-100 slago-flex-jc-ac">
                <h1 style={{color:'#1d1d1f'}}>邮箱验证</h1>
            </div>
            <div className="slago-width-100 slago-flex-jc-ac">
                <input value={this.state.checkCode} onChange={this.Input} type="text" maxLength="4"
                 style={{width:'200px',height:'80px',fontSize:'40px',textAlign:'center',color:'#707070'}}/>
            </div>
            <div className="slago-flex slago-ju-se slago-width-100" style={{marginTop:'30px'}}>
            <div onClick={this.sendCode} style={{width:'120px',height:'40px',backgroundColor:'rgb(245,99,99)',color:'#fafafa',borderRadius:'20px'}} 
                className="slago-flex-jc-ac slago-hover-p">
                    {"重新发送 "+this.getRetryText()}
                </div>
                <div onClick={this.check} style={{width:'100px',height:'40px',backgroundColor:'rgb(245,99,99)',color:'#fafafa',borderRadius:'20px'}}
                 className="slago-flex-jc-ac slago-hover-p">
                    确定
                </div>
            </div>
        </React.Fragment>);
    }
}
 
export default Step3;