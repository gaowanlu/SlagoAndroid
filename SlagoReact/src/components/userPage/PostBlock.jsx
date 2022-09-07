import React, { Component } from 'react';
import axios from 'axios';
// 获取帖子信息
// 请求地址/apis/getPostData
// 请求格式:?postid=$
// 返回格式:
// 成功: {"status":200,"userid":"1901420313","posttext":"🤬🦊","postdate":"2021-07-19 20:49:55","imgs":["4"]}
// 失败: {"status":300}
class PostBlock extends Component {
    state = { 
        postid:this.props.postid,
        postjson:{
            userid:'',
            posttext:'',
            postdate:'',
            imgs:["/img/loading.png"]
        }
    }
    componentDidMount(){
        axios.get("/apis/getPostData?postid="+this.state.postid).then(rp=>{
            let {postjson}=this.state;
            postjson.userid=rp.data.userid;
            postjson.posttext=rp.data.posttext;
            postjson.postdate=rp.data.postdate;
            postjson.imgs=[];
            for(let i=0;i<rp.data.imgs.length;i++){
                postjson.imgs.push(rp.data.imgs[i]);
            }
            this.setState({postjson});
        });
    }
    render() { 
        let imgURL="/apis/getPostImg?id="+this.state.postjson.imgs[0];
        if(this.state.postjson.imgs[0]==="/img/loading.png"){
            imgURL="/img/loading.png";
        }
        return (
            <React.Fragment>
                <div style={{width:'250px',height:'210px',display:'flex',flexWrap:'wrap',backgroundColor:'#fafafa',
                flexGrow:'1',flexShrink:'1',margin:'0.5em',borderRadius:'3px',padding:'10px',justifyContent:'center',
                boxShadow:'0px 0px 1px 1px #4646461f'}}>
                    <div style={{width:'100%',justifyContent:'center',display:'flex'}}>
                        <img src={imgURL} 
                        style={{width:'150px',height:'150px',objectFit:'cover',borderRadius:'3px',cursor:'pointer'}} alt=""/>
                    </div>
                    <div style={{width:'97%',maxWidth:'180px',height:'20px',overflow:'hidden'}}>
                        <span style={{color:'#1d1d1f',fontSize:'0.7em'}}>{this.state.postjson.posttext}</span>
                    </div>
                    <div style={{width:'97%',maxWidth:'180px',height:'20px',overflow:'hidden',color:'#707070',fontSize:'0.6em',textAlign:'right',marginTop:'5px'}}>
                    {this.state.postjson.postdate}
                    </div>
                </div>
            </React.Fragment>
        );
    }
}
 
export default PostBlock;