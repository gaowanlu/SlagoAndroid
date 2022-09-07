/*
高阶组件:hover
*/
import React from 'react';
export default function Hover(MyComponent){
    return class Hover extends React.Component{
        state={
            hover:false//触点是否正在上面
        }

        changeHoverState=(hover)=>{
            this.setState({hover});
        }

        render(){
            return (<div style={{display:'inline'}} onMouseOver={()=>this.changeHoverState(true)} onMouseOut={()=>this.changeHoverState(false)}>
                <MyComponent {...this.props} ishover={this.state.hover}></MyComponent>
            </div>);
        }
    }
}
