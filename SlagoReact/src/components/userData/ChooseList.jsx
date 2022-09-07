import React from 'react';
import '../../css/ChooseList.css';
const ChooseList=(props)=>{
    const {listStateChange,listState}={...props};
    return <React.Fragment>
        <div className="ChooseList-container">
            <div className="ChooseList-li ChooseList-bottomline ChooseList-title">{listState.title}</div>
            {
                listState.li.map(c=>{
                    let STYLE="ChooseList-li ChooseList-bottomline";
                    let innerStyle={};
                    if(c.name===listState.current){
                        innerStyle={
                            backgroundColor:"rgb(245, 99, 99)",
                            color:"#fafafa",
                            borderLeft:'2px #0066cc solid'
                        };
                    }
                    /*第一个 添加radius*/
                    //if(c.name===listState.li[0].name) STYLE+=" ChooseList-li-lrtradius";
                    /*最后一个 添加radius*/
                    if(c.name===listState.li[listState.li.length-1].name) STYLE+=" ChooseList-li-lrbradius";
                    return (
                        <div style={innerStyle} key={c.name} onClick={()=>listStateChange(c.name)} className={STYLE}>{c.name}</div>
                    );
                })
            }
        </div>
    </React.Fragment>;
}
 
export default ChooseList;