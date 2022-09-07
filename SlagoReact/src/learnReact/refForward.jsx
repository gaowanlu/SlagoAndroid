/*Ref转发
什么意思 也就是将组件
*/
import React from "react";
import { Component } from 'react';
const FancyButton = React.forwardRef((props, ref) => (
  <button ref={ref} className="FancyButton">
    {props.children}
  </button>
));

// 你可以直接获取 DOM button 的 ref： 将ref对象向下传递 由子组件进行ref与dom的绑定
const ref = React.createRef();
<FancyButton ref={ref}>Click me!</FancyButton>;
//此时ref即得到了FancyButton内button的dom节点
//这样对于一些可复用组件库比较有用 为用户提供获取内部dom节点的可能性



//高阶组件中的Refs转发
function logProps(WrappedComponent) {
  class LogProps extends React.Component {
    componentDidUpdate(prevProps) {
      console.log("old props:", prevProps);
      console.log("new props:", this.props);
    }

    render() {
        const {forwardRef,...rest} =this.props;
      return <div>
          <WrappedComponent ref={forwardRef} {...this.props} />
          </div>;
    }
  }

  return React.forwardRef((props,ref)=>{
      return <LogProps {...props} forwardRef={ref}/>
  });
  
}


function Button(props){
    return<div>
        <button>点击</button>
    </div>
}

let DivPro=logProps(Button);

/*如果在此使用ref转发*/
let buttonRef=React.createRef();
let component=<DivPro ref={buttonRef}/>
/*那么ref到底给了谁 
refs 将不会透传下去。这是因为 ref 不是 prop 属性。
就像 key 一样，其被 React 进行了特殊处理。
如果你对 HOC 添加 ref，该 ref 将引用最外层的容器组件，而不是被包裹的组件
则ref给到了DivPro组件 
//这样我们就可以使用ref转发 将ref转发到所包裹的组件内
*/
