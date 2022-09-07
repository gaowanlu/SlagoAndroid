import React, { Component } from 'react';
import '../../css/Footer.css';
class Footer extends Component {
    state = {  }
    render() { 
        return ( <React.Fragment>
            <div className="Footer-container">
                <div className="Footer-content">
                    <a target="_blank" style={{textDecoration:'none',margin:'10px'}} href="http://www.91kkz.cn/index/app/fenfa?fileurl=16331832805268337">
                        <span>安卓APP</span>
                    </a>
                    <a target="_blank" style={{textDecoration:'none',margin:'10px'}} href="https://github.com/gaowanlu/SlagoReact">
                        <span>Github</span>
                    </a>
                </div>
                <div className="Footer-content">
                    <span>Copyright © 2021  Wanlu</span>
                </div>
            </div>
        </React.Fragment> );
    }
}
 
export default Footer;