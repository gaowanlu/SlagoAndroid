import React, { Component, useContext } from 'react';
import '../../css/UserDataPage.css';
import '../../css/ViewAll.css';
import ViewAll from './rightFragment/ViewAll';
import ChooseList from './ChooseList';
import NameChange from './rightFragment/NameChange';
import cookies  from 'react-cookies';
import HeadImgChange from './rightFragment/HeadImgChange';
import SexChange from './rightFragment/SexChange';
import ProfileChange from './rightFragment/ProfileChange';
import UserDataContext from './../comon/context/UserDataContext';
import axios from 'axios';


class UserDataPage extends Component {
    state = { 
        listState:{
            title:"个人信息",
            li:[
                {name:"账号"},
                {name:"昵称"},
                {name:"头像"},
                {name:"性别"},
                {name:"个人简介"},
                {name:"账号安全"},
                {name:"退出登录"},
            ],
            current:"账号"
        },
        //个人信息，提供给个人信息上下文
        userData:{
            sex:'男',
            profile:'vfdsd',
            name:'fdvd',
            id:'21232',
            likeNum:"10",
            aboutNum:"10",
            fansNum:"10",
            headimgVersionCode:0
        },
        userDataContextValue:{
            getUserData:(name)=>this.getUserData(name),
            updateUserData:(name,value)=>this.updateUserData(name,value)
        }
    }
    //上下文通信 更新上下文接口
    updateUserData=(name,value)=>{
        let userData={...this.state.userData};
        userData[name]=value;
        this.setState({userData});
    }
    //获得上下文内容接口
    getUserData=(name)=>{
        return this.state.userData[name];
    }

    listStateChange=(e)=>{
        const listState={...this.state.listState};
        listState.current=e;
        if(listState.current==="退出登录"){
            cookies.remove("id");
            cookies.remove("SlagoSession");
            this.toWhere("replace","/Login");
        }
        //账号安全
        if(listState.current===this.state.listState.li[5].name){
            this.toWhere("push","/AccountSecurityPage");
        }
        this.setState({listState});
    }
    toWhere=(choose,where)=>{
        if("push"===choose){
            this.props.history.push(where);
        }else if("replace"===choose){
            this.props.history.replace(where);
        }
    }
    componentDidMount(){
        
    }
    render() { 
        let rightFragment=<ViewAll toWhere={this.toWhere}></ViewAll>;
        switch(this.state.listState.current){
            case this.state.listState.li[1].name:rightFragment=<NameChange></NameChange>;break;
            case this.state.listState.li[2].name:rightFragment=<HeadImgChange></HeadImgChange>;break;
            case this.state.listState.li[3].name:rightFragment=<SexChange></SexChange>;break;
            case this.state.listState.li[4].name:rightFragment=<ProfileChange></ProfileChange>;break;
            default:break;
        }
        return ( <React.Fragment>
            {/*关于value 当父组件渲染时可能导致consumers组件渲染 因为每次value都被重新赋值为新对象 解决办法 将value的状态提升到父节点的state里面
                尽可能不要value={{property:value,....}}这样写*/}
            <UserDataContext.Provider value={this.state.userDataContextValue}>
                {/* 个人信息上下文 */}
                <div className="UserDataPage-container" style={{padding:'5px'}}>
                    <div className="UserDataPage-left">
                        <ChooseList listState={this.state.listState} listStateChange={this.listStateChange}></ChooseList>
                    </div>
                    <div className="UserDataPage-right">
                        {rightFragment}
                    </div>
                </div>
            </UserDataContext.Provider>
        </React.Fragment> );
    }
}
/*函数组件怎样使用上下文*/
function TempFunctionComponent(props){
    const context=useContext(UserDataContext);//绑定并获取上下文
    return(<React.Fragment>
        context.getUserData("name")
    </React.Fragment>);
}
export default UserDataPage;