import React, { Component } from 'react';
import  cookies  from 'react-cookies';
import axios from 'axios';
import UserDataContext from './../../comon/context/UserDataContext';
class ProfileChange extends Component {
    state = { 
        inputValue:"",
    }
    inputOnChange=(e)=>{
        this.setState({inputValue:e.currentTarget.value});
    };
    componentDidMount(){
        //从上下文获得profile
        console.log("读取上下文profile",this.context.getUserData("profile"));
        this.setState({inputValue:this.context.getUserData("profile")+""});
    }
    buttonEvent=()=>{
        axios.post("/apis/setUserProfile?newProfile="+this.state.inputValue).then(response=>{
            if(response.data.status===200){
                alert("个人简介修改成功");
                //更新上下文
                this.context.updateUserData("profile",this.state.inputValue+"");
            }
        }).catch(error=>{});
    };
    render() { 
        let buttonStyle={
            backgroundColor:'rgb(245, 99, 99)',
            color:'#fafafa',
            border:'0px',
            height:'2.2em',
            width:'5em',
            borderRadius:'0.2em',
        };
        let textareaStyle={
            width:'100%',
            minHeight:'10em'
        };
        return (<React.Fragment>
            <div className="ViewAll-container   animate__animated animate__fadeIn">
                <div>
                    <textarea style={textareaStyle} value={this.state.inputValue} onChange={this.inputOnChange}></textarea>                    
                </div>
                <div style={{display:'flex',justifyContent:'flex-end'}}>
                    <button style={buttonStyle} onClick={this.buttonEvent}>更改</button>
                </div>
            </div>
        </React.Fragment> );
    }
}
ProfileChange.contextType=UserDataContext;//使用Class.contextType挂载
export default ProfileChange;