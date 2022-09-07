import React, { Component } from 'react';
class ChooseRow extends Component {
    state = { 
        list:this.props.list,
        current:this.props.list[0]
    }
    clickEvent=(current)=>{
        this.setState({current});
        this.props.callback(current);
    }
    render() { 
        return ( <React.Fragment>
            <div className="slago-flex-w slago-ju-sa" style={{width:'100%',backgroundColor:'#fafafa',borderBottom:'1px #70707033 solid',paddingBottom:'8px'}}>
                {
                    this.state.list.map(d=>{
                        let borderbottom='0px grey solid';
                        let style="slago-hover-p slago-flex-jc-ac ";
                        if(d===this.state.current){
                            borderbottom='2px rgb(245, 99, 99) dashed';
                            style+=" slago-font-b animate__animated animate__flipInX";
                        }
                        return(
                            <div key={d} onClick={()=>this.clickEvent(d)} className={style}
                             style={{flexGrow:'1',height:'50px',borderBottom:borderbottom,maxWidth:'140px',
                             margin:'3px',color:'#1d1d1f',fontSize:'0.9em'}}>
                                {d}
                            </div>
                        )
                    })
                }
            </div>
        </React.Fragment> );
    }
}
 
export default ChooseRow;