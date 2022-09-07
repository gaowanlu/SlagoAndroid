(function(){
    let namespace={};
    namespace.template=`
    <!--PostBlock-->
    <div style="width: 310px;margin-left:5px;background-color: #fcfcfc;border-radius: 10px;">
        <!--封面-->
        <div style="width:300px;margin-left:5px;border-radius:5px;height:300px;overflow: hidden;display: flex;justify-content: center;">
            <img style="width: 300px;height:300px;object-fit: cover;" src="./img/loadpostimg.gif">
        </div>
        <!--文字简介-->
        <div style="width:300px;margin-left:5px;height:30px;margin-top:20px;color:#707070;">
            
        </div>
        <!--日期-->
        <div style="width:300px;margin-left:5px;height:20px;margin-top: 10px;color: #707070;">
        
        </div>
    </div>
    `;
    //提供postid生成组件dom node，内部进行ajax请求，请求后自将数据更新到视图
    namespace.GetDOMNode=function(postid){
        return this.getnode(postid);
    };
    namespace.getnode=function(postid){
        let node=document.createElement("div");
        node.style.width="320px";
        node.style.height="420px";
        node.style.marginTop="10px";
        node.innerHTML=Slagolib.template.engin(this.template,{});
        //获取img content time DOMNode
        let img=node.children[0].children[0].children[0];
        let content=node.children[0].children[1];
        let time=node.children[0].children[2];
        ////http://127.0.0.1:12344/apis/getPostData?postid=10
        //发起@AJAX请求根据postid获取帖子的详细内容
        //发起@AJAX获取用户的帖子
        let AJAX_getPostData=Slago.CancelAjax.before();
        axios.get(Slago.Data.ServerData.IP+
            "getPostData?postid="+postid,{
            cancelToken: AJAX_getPostData.token
        }).then(response=>{
            //更新视图资源
            content.innerHTML=response.data.posttext;
            time.innerHTML=response.data.postdate;
            img.src=Slago.Data.ServerData.IP+"getPostImg?id="+response.data.imgs[0];
            //为img绑定onclick事件
            img.onclick=()=>{
                //{"status":200,"userid":"1901420313","posttext":"","postdate":"2021-08-13 19:29:48","imgs":["42"]}
                //显示一个帖子页面
                window.stop();
                SlagoModel.PostSuspensionPage.postPage.show(response.data);
            };
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称用户帖子列表] PersonalPostPage.js");
        });
        return node;
    }
    SlagoModel.UserPersonal.PersonalPost.PostBlockDOM=namespace;
})();