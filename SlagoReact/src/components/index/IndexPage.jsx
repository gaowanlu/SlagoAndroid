import React, { Component } from 'react';
import NewPostButton from './NewPostButton';
import '../../css/IndexPage.css';
class IndexPage extends Component {
    state = {
        
    }
    constructor(){
        super();
        this.headerDOM=null;
        this.rootDOM=null;
        this.footerDOM=null;
    }
    componentDidMount(){
        this.headerDOM=(document.getElementsByClassName("HeadNavBar-headerContainer"))[0];
        this.rootDOM=document.getElementById("root");
        this.footerDOM=(document.getElementsByClassName("Footer-container"))[0];
        this.rootDOM.style.backgroundColor='#000000';
        this.headerDOM.style.backgroundColor='#1d1d1f33';
        this.footerDOM.style.backgroundColor='#1d1d1f33';
    }
    componentWillUnmount(){
        this.rootDOM.style.backgroundColor='#ffffff';
        this.headerDOM.style.backgroundColor='#ffffff';
        this.footerDOM.style.backgroundColor='#ffffff';
    }
    render() { 
        return ( <React.Fragment>
            <div style={{width:'100%',minHeight:'100vh',display:'flex',justifyContent:'center',flexWrap:'wrap'}}>
                <div style={{width:'1200px'}}>
                {/* 背景及发帖按钮 */}
                <div style={{width:'100%',position:'relative'}}>
                    <div style={{position:'absolute',zIndex:'3'}}>
                        <img className="animate__animated animate__zoomIn" style={{width:"100%"}} src="/img/brano.jpg" alt="/img/brano-MZ3jsXxEZps-Slago.jpg"/>
                    </div>
                    <div style={{width:'100%',marginTop:'200px',zIndex:'10',position:'absolute',display:'flex',justifyContent:'center'}}>
                        <h1 className="animate__animated animate__fadeIn" style={{color:'#fafafa',fontWeight:'bold'}}>SLAGO SPACE</h1>
                    </div>
                    <NewPostButton style={{marginTop:'380px',zIndex:'11',position:'absolute'}}/>
                </div>
                {/* 开发者 */}
                <div style={{width:'100%',marginTop:'700px',display:'flex',justifyContent:'center'}}>
                    <h1 style={{color:'#fafafa'}}>开发者</h1>
                </div>
                <div style={{width:'100%',display:'flex',justifyContent:'center',padding:'30px'}}>
                    <div style={{width:'100%',borderRadius:'3px',border:'1px #1d1d1f solid',
                    display:'flex',flexWrap:'wrap',justifyContent:'center',padding:'10px'}}>
                        <img style={{maxWidth:'100px',flexGrow:'1',height:'100px',borderRadius:'50px',margin:'8px'}} src="/img/author.jpg" alt="/img/author.jpg"/>
                        <div style={{flexGrow:'1',display:'flex',justifyContent:'center',flexWrap:'wrap'}}>
                            <h4 style={{color:'#fafafa'}}>高万禄</h4>
                            <div style={{color:'#fafafa',width:'100%',textAlign:'center',paddingTop:'10px'}}>一名闲时撸些code、骑会公路车的本科在校生</div>
                        </div>
                    </div>
                </div>
                {/* 绿色风景图 */}
                <div style={{width:'100%',backgroundColor:'#000000',display:'felx',justifyContent:'center',margin:'150px 0px 150px 0px'}}>
                    <img style={{width:"100%"}} src="/img/kristaps.jpg" alt="风景"/>
                </div>
                {/* 加入我们 */}
                <div style={{width:'100%',display:'flex',justifyContent:'center',marginTop:'10px'}}>
                    <h1 style={{color:'#fafafa'}}>加入我们</h1>
                </div>
                <div style={{width:'100%',display:'flex',justifyContent:'center',padding:'30px'}}>
                    <div style={{width:'100%',borderRadius:'3px',border:'1px #1d1d1f solid',
                    display:'flex',flexWrap:'wrap',justifyContent:'center',padding:'10px'}}>
                        <div style={{width:'100%',color:'#fafafa',padding:'8px 0px 8px 0px'}}>
                            在Github提交问题与改进建议、进行Fork pullRequest等</div>
                        <div style={{width:'100%',color:'#fafafa',padding:'8px 0px 8px 0px'}}>
                            开发者邮箱:2209120827@qq.com进行联系</div>
                        <div style={{width:'100%',color:'#fafafa',padding:'8px 0px 8px 0px'}}>
                            平台邮箱:heizuboriyo@gmail.com</div>
                        <div style={{width:'100%',color:'#fafafa',padding:'8px 0px 8px 0px'}}>
                            用户交流QQ群:954078083</div>
                    </div>
                </div>
                </div>
            </div>
        </React.Fragment> );
    }
}
 
export default IndexPage;