import React, { Component } from 'react';
import StateBar from './StateBar';
class Collection extends Component {
    state = {  }
    render() { 
        return ( <React.Fragment>
            <div className="UserPage-content-container  animate__animated animate__fadeIn" >
                <StateBar placeholder="内容/作者" buttonText="搜索" nowState="我的收藏"/>
            </div>
        </React.Fragment> );
    }
}
 
export default Collection;