import React, { Component } from 'react';
import axios from 'axios';

class Step2 extends Component {
    state = {
        name:{value:'',status:'昵称不能为空'},
        password:{value:'',status:'密码不能为空'},
        repassword:{value:'',status:'密码不能为空'},
    }

    check=(num)=>{
        let {name,password,repassword}=this.state;
        if(num===3&&name.status===""&&password.status===""&&repassword.status===""){
            this.props.callback({name:this.state.name,password:this.state.password});
        }
        if(num===1){
            this.props.callback(1);
        }
    }

    nameInput=(e)=>{
        let {name}=this.state;
        name.value=e.currentTarget.value;
        this.setState({name});
        //检验name
        name.value=name.value.trim().replace(' ','');
        if(name.value===''){
            name.status="昵称不能为空";
            this.setState({name});
        }else{//发起请求检验
            axios.get("/CheckUser?name="+name.value).then(rp=>{
                if(rp.data.result===false){
                    name.status="";
                }else{
                    name.status="昵称已被使用、请更换";
                }
                this.setState({name});
            });
        }
    }

    passwordInput=(e)=>{
        const {name,value}=e.currentTarget;
        let {password,repassword}=this.state;
        if(name==="1"){//密码
            password.value=value;
        }else{//确认密码
            repassword.value=value;
        }
        if(password.value.replace(' ','').length<8||repassword.value.replace(' ','').length<8){
            password.status=repassword.status="密码长度不能小于8位";
        }else if(password.value!==repassword.value){
            password.status=repassword.status="两次密码不一致";
        }else{
            password.status=repassword.status="";
        }
        this.setState({password,repassword});
    }


    render() { 
        return ( <React.Fragment>
            <div style={{marginTop:'40px'}} className="slago-width-100 slago-flex-jc slago-flex-w">
                <div style={{maxWidth:'320px',minWidth:'200px',fontSize:'1.1em'}} className="slago-grow-1">电子邮箱</div>
                <input readonly="readonly" value={this.props.email} style={{maxWidth:'320px',minWidth:'240px',height:'38px',fontSize:'1.1em'}} className="slago-grow-1"/>
            </div>
            <div style={{marginTop:'8px'}} className="slago-width-100 slago-flex-jc slago-flex-w">
                <div style={{maxWidth:'320px',minWidth:'200px',fontSize:'1.1em'}} className="slago-grow-1">昵称</div>
                <input value={this.state.name.value} onChange={this.nameInput}  style={{maxWidth:'320px',minWidth:'240px',height:'38px',fontSize:'1.1em'}} className="slago-grow-1"/>
                <div className="slago-flex-jc slago-width-100" style={{color:'red'}}>{this.state.name.status}</div>
            </div>
            <div style={{marginTop:'8px'}} className="slago-width-100 slago-flex-jc slago-flex-w">
                <div style={{maxWidth:'320px',minWidth:'200px',fontSize:'1.1em'}} className="slago-grow-1">密码</div>
                <input value={this.state.password.value} name="1" onChange={this.passwordInput}  type="password" style={{maxWidth:'320px',minWidth:'240px',height:'38px',fontSize:'1.1em'}} className="slago-grow-1"/>
                <div className="slago-flex-jc slago-width-100" style={{color:'red'}}>{this.state.password.status}</div>
            </div>
            <div style={{marginTop:'8px'}} className="slago-width-100 slago-flex-jc slago-flex-w">
                <div style={{maxWidth:'320px',minWidth:'200px',fontSize:'1.1em'}} className="slago-grow-1">确认密码</div>
                <input value={this.state.repassword.value} name="2" onChange={this.passwordInput}  type="password" style={{maxWidth:'320px',minWidth:'240px',height:'38px',fontSize:'1.1em'}} className="slago-grow-1"/>
                <div className="slago-flex-jc slago-width-100" style={{color:'red'}}>{this.state.password.status}</div>
            </div>
            <div style={{marginTop:'30px'}} className="slago-ju-se slago-width-100 slago-flex-w">
                <div onClick={()=>this.check(1)} style={{width:'120px',height:'40px',backgroundColor:'rgb(245,99,99)',color:'#fafafa',borderRadius:'20px'}} 
                className="slago-flex-jc-ac slago-hover-p">
                    上一步
                </div>
                <div onClick={()=>this.check(3)}  style={{width:'120px',height:'40px',backgroundColor:'rgb(245,99,99)',color:'#fafafa',borderRadius:'20px'}}
                 className="slago-flex-jc-ac slago-hover-p">
                    邮箱验证
                </div>
            </div>
        </React.Fragment> );
    }
}
 
export default Step2;