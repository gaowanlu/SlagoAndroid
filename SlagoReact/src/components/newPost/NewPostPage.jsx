import React, { Component } from 'react';
import axios from 'axios';
import PostUpLoad from './PostUpLoad';
import { Rules } from './Rules';
class NewPostPage extends Component {
    state = { 

    }
    componentDidMount(){
        //发起身份验证
        console.log("发帖页验证");
        axios.get("/SlagoService_Authentication").then(response=>{});
    }
    render() {
        return (<React.Fragment>
            <div className="width-100 slago-flex-jc-ac" style={{marginTop:'20px'}}>
                <div className="slago-flex-jc slago-wrap-w"  style={{width:'1200px'}}>
                    <div className="slago-shrink-1 slago-grow-1 slago-div-bs slago-div-br" style={{maxWidth:'1000px',margin:'5px'}}>
                        <PostUpLoad replace={this.props.history.replace}/>
                        <Rules/>
                    </div>
                </div>
            </div>
        </React.Fragment>);
    }
}
 
export default NewPostPage;