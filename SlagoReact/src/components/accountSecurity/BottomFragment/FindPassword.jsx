import React, { Component } from 'react';
import axios from 'axios';

import Step1 from '../Find/Step1';
import Step2 from '../Find/Step2';
import Step3 from '../SignUp/Step3';
class FindPassword extends Component {
    state = { 
        email:'',
        password:'',
        nowStep:1
    }
    step1=(email)=>{
        this.setState({email,nowStep:2});
    }
    step2=(obj)=>{
        if(obj===1){
            this.setState({nowStep:obj});return;
        }
        this.setState({password:obj,nowStep:3});
    }
    step3=(checkCode)=>{
        let {email,password}=this.state;
        axios.get("/ChangePwd?email="+email+"&new="+password+"&check="+checkCode).then(rp=>{
            if(rp.data.result==="true"){
                alert("密码修改成功");
                this.props.history.replace("/Login");
            }else{
                alert("修改失败:验证错误请重试 "+rp.data.id);
                this.setState({nowStep:2});
            }
        });
    }
    nowStep=()=>{
        switch(this.state.nowStep){
            case 1:return <Step1 email={this.state.email} callback={this.step1}/>;
            case 2:return <Step2 email={this.state.email} callback={this.step2}/>;
            case 3:return <Step3 email={this.state.email} callback={this.step3}/>;
            default:return <Step1 email={this.state.email} callback={this.step1}/>;
        }
    }
    render() { 
        return ( <React.Fragment>
            <div className="slago-flex-jc-ac" style={{width:'100%',margin:'5px',alignContent:'flex-start'}}>
                <div className="slago-width-100 slago-flex-jc-ac slago-flex-w" style={{minHeight:'500px',alignContent:'flex-start',padding:'5px'}}>
                    {this.nowStep()}
                </div>
            </div>
        </React.Fragment> );
    }
}
 
export default FindPassword;