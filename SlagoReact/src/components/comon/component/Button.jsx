import React, { Component } from 'react';
import Hover from '../hoc/Hover.jsx';
/*
按钮接口
bgColor:背景颜色
textColor:没有hover的字体颜色
hoverTextColor:hover字体颜色
hoverBgColor:hover背景颜色
width:按钮宽度
height:按钮高度
*/
class Button extends Component {
    render() {
        let buttonText="";
        if(this.props.ishover===true){
            buttonText="HOVER";
        }
        return (
            <React.Fragment>
                <button>按钮{buttonText}</button>
            </React.Fragment>
        );
    }
}

export default Hover(Button);