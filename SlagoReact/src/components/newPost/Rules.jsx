import React, { useState } from 'react';
import $ from 'jquery';
function Rules(){
    let [yinsi,setyinsi]=useState(0,'');
    let [tiaojian,settiaojian]=useState(1,'');
    let [xieyi,setxieyi]=useState(2,'');
    let ROWstyle={
        color:'#1d1d1f',
        fontWeight:'light',
        fontSize:'0.5em',
        margin:'5px 0px 5px 0px'
    };
    $.get("/textdir/隐私政策.txt",(rp)=>{
        setyinsi(rp);
    });
    $.get("/textdir/条款与条件.txt",(rp)=>{
        settiaojian(rp);
    });
    $.get("/textdir/协议.txt",(rp)=>{
        setxieyi(rp);
    });
    return(
        <React.Fragment>
            <div className="slago-width-100" style={{padding:'20px'}}>
                <div style={{color:'#1d1d1f',fontWeight:'bold',fontSize:'1em'}}>协议</div>
                <div style={ROWstyle} dangerouslySetInnerHTML={{__html:xieyi }}></div>
                <div style={{color:'#1d1d1f',fontWeight:'bold',fontSize:'1em'}}>隐私政策</div>
                <div style={ROWstyle} dangerouslySetInnerHTML={{__html:yinsi }}></div>
                <div style={{color:'#1d1d1f',fontWeight:'bold',fontSize:'1em'}}>条款与条件</div>
                <div style={ROWstyle}  dangerouslySetInnerHTML={{__html:tiaojian}}></div>
            </div>
        </React.Fragment>
    );
}
export {Rules};