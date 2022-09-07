import React, { Component } from 'react';
import  cookies  from 'react-cookies';
import axios from 'axios';
import UserDataContext from '../../comon/context/UserDataContext';


class SexChange extends Component {
    static contextType=UserDataContext;
    state = {
        listArray:["男","女","保密"],
        currentChoose:""
    }
    componentDidMount(){
        //从上下文获取性别
        this.setState({currentChoose:this.context.getUserData("sex")+""});
    }
    inputOnchange=(e)=>{
        const currentChoose=e.currentTarget.value
        this.setState({currentChoose});
        //发起ajax
        axios.post("/apis/setUserSex?newSex="+currentChoose).then(rp=>{
            this.context.updateUserData("sex",currentChoose+"");//更新上下文性别
        }).catch(er=>{});
    };
    render() { 
        let rowStyle={
            display:'flex',
            alignItems:'center'
        };
        return (<React.Fragment>
            <div className="ViewAll-container   animate__animated animate__fadeIn">
                <form>
                {this.state.listArray.map(v=>{
                    let checked=(v===this.state.currentChoose)?true:false;
                    let input=<input type="radio" name="sex" value={v} onChange={this.inputOnchange}/>;
                    if(checked){
                        input=<input type="radio" name="sex" value={v} onChange={this.inputOnchange} checked="checked"/>;
                    }
                    return(
                        <div key={v} style={rowStyle}>
                            <span className="UserDataPage-fragment-right-text" style={{width:'3em'}}>{v}</span>
                            {input}
                        </div>
                    );
                })}
                </form>
            </div>

        </React.Fragment> );
    }
}
 
export default SexChange;