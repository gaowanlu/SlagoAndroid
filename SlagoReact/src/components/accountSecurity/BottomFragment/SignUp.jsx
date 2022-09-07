import React, { Component } from 'react';
import Step1 from '../SignUp/Step1';
import Step2 from '../SignUp/Step2';
import Step3 from '../SignUp/Step3';
import axios from 'axios';
class SignUp extends Component {
    state = { 
        email:'',
        name:'',
        password:'',
        nowStep:1
    }
    step1=(email)=>{
        console.log(email);
        this.setState({email,nowStep:2});
    }
    step2=(obj)=>{
        if(obj===1){
            this.setState({nowStep:obj});return;
        }
        let {name,password}=obj;
        name=name.value;password=password.value;
        this.setState({nowStep:3,name,password});
    }
    step3=(checkCode)=>{
        let {email,name,password}=this.state;
        axios.get("/RegisterNewCount?email="+email+"&sex=保密&name="+name+"&password="+password+"&checkCode="+checkCode).then(rp=>{
            if(rp.data.result===true){
                alert("注册成功: id:"+rp.data.id);
                this.props.history.replace("/Login");
            }else{
                alert("注册失败:验证错误,请重试"+rp.data.id);
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
 
export default SignUp;