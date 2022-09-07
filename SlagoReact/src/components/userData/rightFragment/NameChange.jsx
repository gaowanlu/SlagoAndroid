import React, { Component } from 'react';
import  cookies  from 'react-cookies';
import axios from 'axios';
import UserDataContext from './../../comon/context/UserDataContext';
class NameChange extends Component {
    static contextType =UserDataContext;
    state = { 
        userid:cookies.load("id"),
        inputValue:""
    }
    componentDidMount(){
        let {inputValue}=this.state;
        inputValue=this.context.getUserData("name")+"";
        this.setState({inputValue});
    }
    submit=()=>{
        axios.get("/apis/updateUserName?newname="+this.state.inputValue).then(response=>{
            if(response.data.status===200){
                alert("修改成功");
                //更新上下文
                this.context.updateUserData("name",this.state.inputValue+"");//利用上下文提供的方法
            }
        });
    }
    inputChange=(e)=>{
        let {inputValue}=this.state;
        inputValue=e.currentTarget.value;
        this.setState({inputValue});
    }
    render() {
        let buttonStyle={
            backgroundColor:'rgb(245, 99, 99)',
            color:'#fafafa',
            border:'0px',
            height:'2.2em',
            width:'5em',
            borderRadius:'0.2em',
        };
        let inputStyle={
            width:'100%',
            height:buttonStyle.height,
            boder:buttonStyle.border,
            maxWidth:'30em'
        };
        return (<React.Fragment>
            <div className="ViewAll-container   animate__animated animate__fadeIn">
                <div style={{width:'100%'}}>
                    <input className="UserDataPage-fragment-right-text" style={inputStyle} value={this.state.inputValue} onChange={this.inputChange} type="text"/>
                    <div style={{display:'flex',width:'100%',justifyContent:'flex-start',marginTop:'0.5em',maxWidth:'30em'}}>
                        <button style={buttonStyle} onClick={this.submit}>更改</button>                        
                    </div>
                </div>
            </div>
        </React.Fragment>);
    }
}
 
export default NameChange;