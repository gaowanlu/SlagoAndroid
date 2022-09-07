import React, { Component } from 'react';
class StateBar extends Component {
    state = {  }
    render() { 
        return ( 
            <div style={{width:'100%',minHeight:'40px',borderBottom:'1px #70707011 solid',display:'flex',flexWrap:'wrap',justifyContent:'space-between'}}>
                <span style={{height:'38px',display:'flex',alignItems:'center',paddingLeft:'1em',color:'#707070',fontSize:'0.8em'}}>{this.props.nowState}</span>
                <span style={{height:'38px',display:'flex',alignItems:'center'}}>
                    <input style={{width:'140px',height:'26px',borderRadius:'6px',fontSize:'0.7em',
                    outlineColor:'#ffffff00',border:'0px',color:'#1d1d1f',textAlign:'center'}} type="text" placeholder={this.props.placeholder}></input>
                    <div style={{width:'50px',color:'#ffffff',backgroundColor:'#ee5757',
                    marginLeft:'4px',height:'26px',borderRadius:'6px',fontSize:'0.7em',display:'flex',
                    justifyContent:'center',alignItems:'center',cursor:'pointer',marginRight:'5px'}}>
                        {this.props.buttonText}
                    </div>
                </span>
            </div>
        );
    }
}
 
export default StateBar;