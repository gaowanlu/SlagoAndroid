import axios from 'axios';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../../css/LoginForm.css';
class LoginForm extends Component {
    state = { 
        formdata:{
            id:"",
            password:""
        }
    }
    inputOnchange=(e)=>{
        const dom=e.currentTarget;
        const name=dom.name;
        const data={...this.state.formdata};
        data[name]=dom.value;
        this.setState({formdata:data});
    }
    login=(e)=>{
        e.preventDefault();//阻止默认事件
        const requestUrl=`/SlagoService_Login?id=${this.state.formdata.id}&password=${this.state.formdata.password}`;
        //{status: "200", result: "true"}
        axios.get(requestUrl).then(response=>{
            if(response.data.result==="true"){
                this.props.toWhere("replace","/");
            }else{
                alert("账号或密码错误");
            }
        }).catch(e=>{
            console.log(e);
        });
    }
    render() { 
        return ( <React.Fragment>
            <div className="LoginForm-content">
                <form>
                <div className="LoginForm-title">Hi Slago!</div>
                <div className="LoginForm-c">
                    <span className="LoginForm-label">账号</span>
                    <input value={this.state.formdata.id} onChange={this.inputOnchange} className="LoginForm-input" type="text" name="id"></input>
                </div>
                <div className="LoginForm-c">
                    <span className="LoginForm-label">密码</span>
                    <input  value={this.state.formdata.password}  onChange={this.inputOnchange} className="LoginForm-input" type="password" name="password"></input>
                </div>
                <div className="LoginForm-c">
                    <button onClick={this.login} className="LoginForm-loginbutton">登录</button>
                </div>
                <div className="LoginForm-c">
                    <span style={{marginLeft:'2em'}}>
                        <Link className="LoginForm-register" to="/AccountSecurityPage"><span >注册账号或找回密码</span></Link>                        
                    </span>
                </div>
                </form>
            </div>
        </React.Fragment> );
    }
}
 
export default LoginForm;