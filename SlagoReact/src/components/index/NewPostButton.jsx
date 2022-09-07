import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import 'animate.css';
class NewPostButton extends Component {
    state = { 
        animate:"slago-flex-jc-ac IndexPage-newpostbutton slago-hover-p animate__animated animate__heartBeat"
    }
    onClick=()=>{
        let animate="slago-flex-jc-ac IndexPage-newpostbutton slago-hover-p animate__animated animate__bounceOut";
        this.setState({animate});
    }
    render() {
        let container={
            width:'100%',
            height:'100px'
        };
        let containerStyle={...this.props.style,...container};
        return ( <React.Fragment>
            <Link to="/NewPostPage" push>
            <div className="slago-flex-jc-ac" style={containerStyle}>
                <div onClick={this.onClick} className={this.state.animate}>
                    <span style={{color:'#fafafa',fontSize:'1.5em'}}>分享内容</span>
                </div>
            </div>
            </Link>
        </React.Fragment>);
    }
}
 
export default NewPostButton;