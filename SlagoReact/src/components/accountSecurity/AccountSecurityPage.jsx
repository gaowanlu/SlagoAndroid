import React, { Component } from 'react';
import '../../css/AccountSecurityPage.css';
import SignUp from './BottomFragment/SignUp';
import ChooseRow from './ChooseRow';
import FindPassword from './BottomFragment/FindPassword';
class AccountSecurityPage extends Component {
    state = { 
        Chooses:["注册账号","找回密码"],
        current:"注册账号"
    }
    ChooseRowCallback=(current)=>{
        this.setState({current});
    }
    bottomFragment=()=>{
        const index=this.state.Chooses.findIndex(d=>d===this.state.current);
        switch(index){
            case 0:return <SignUp history={this.props.history}/>;
            case 1:return <FindPassword history={this.props.history}/>;
            default:return <SignUp history={this.props.history}/>;
        }
    }
    render() { 
        return ( <React.Fragment>
            <div className="slago-flex-jc slago-width-100">
                <div className="slago-flex-w slago-div-bs slago-div-br" style={{width:'1200px',margin:'20px 10px 0px 10px'}}>
                    <div className="slago-flex-w slago-flex-w" style={{width:'100%'}}>
                        <ChooseRow list={this.state.Chooses} callback={this.ChooseRowCallback}/>
                        {this.bottomFragment()}
                    </div>
                </div>
            </div>
        </React.Fragment> );
    }
}
 
export default AccountSecurityPage;