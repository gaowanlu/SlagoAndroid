import React, { Component } from 'react';
import '../../css/PageTurner.css';
class PageTurner extends Component {
    state = { 
        data:this.props.data,
    }
    leftClick=()=>{
        let data={...this.state.data};
        if(data.nowPage>1){
            --data.nowPage;
            this.setState({data});
            this.props.callBack('left');
        }
    }
    rightClick=()=>{
        let data={...this.state.data};
        if(data.nowPage<data.countPage){
            ++data.nowPage;
            this.setState({data});
            this.props.callBack('right');
        }
    }
    render() {
        //判断总页数
        if(this.state.data.countPage<=0){return <React.Fragment></React.Fragment>;}
        //确定当前页与下一页位置的内容是什么
        let now=this.state.data.nowPage;
        let next=null;
        if(now<this.state.data.countPage){next=now+1;}
        let nextDisplay={display:'flex'};
        let leftArrowDisplay={...nextDisplay};
        let rightArrowDisplay={...nextDisplay};
        if(next===null){nextDisplay.display='none';}
        //判断左右箭头的显示与否
        if(now===1){//不显示左箭头
            leftArrowDisplay.display='none';
        }
        if(now===this.state.data.countPage){//不显示右箭头
            rightArrowDisplay.display='none';
        }
        return ( <React.Fragment>
            <div className="PageTurner-container">
                <div className="PageTurner-content">
                    {/* 左箭头 */}
                    <div style={leftArrowDisplay} className="PageTurner-block">
                        <img onClick={this.leftClick} src="/img/arrow-left.png" className="PageTurner-arrow" />
                    </div>
                    {/* 当前页数 */}
                    <div className="PageTurner-block PageTurner-color">
                        {now}
                    </div>
                    {/* 下一页数 */}
                    <div onClick={this.rightClick} style={nextDisplay} className="PageTurner-block">
                        {next}
                    </div>
                    {/* 右箭头 */}
                    <div style={rightArrowDisplay} className="PageTurner-block">
                        <img onClick={this.rightClick} src="/img/arrow-right.png" className="PageTurner-arrow" />
                    </div>
                    {/* 总页数 */}
                    <div style={{marginLeft:'5px',maxWidth:'100px'}}>
                        共 {this.state.data.countPage} 页
                    </div>
                </div>
            </div>
        </React.Fragment> );
    }
}
 
export default PageTurner;