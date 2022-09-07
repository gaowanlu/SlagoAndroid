(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=`
        <!-- 个人帖子页组件 -->
        <div style="width:640px;background-color: #ffffff;">
            <!-- 导航栏 -->
            <div
                style="width:640px;height:80px;background-color: rgb(255, 255, 255);
                align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">
                <!-- 返回按键 -->
                <div class="hoverPointer" onclick="Slago.PageStack.pop()"
                    style="display: flex;height:100%;align-items: center;margin-left: 10px;">
                    <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">
                    <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">
                        <span style="font-size: 27px;color:#0066cc;">返回</span>
                    </div>
                </div>
                <!--导航栏字体栏-->
                <div style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">
                   帖子
                </div>
            </div>
            <div style="height:80px;"></div>
            <div style="width:640px;display: flex;flex-wrap:wrap;margin-top:10px;">
            </div>
        </div>
    `;
    //滑倒底部异步加载
    namespace.loaddata=function(pagenode,page,num){
        //获取left与right
        let left=pagenode.children[0].children[2];
        //let right=pagenode.children[0].children[2].children[1];
        let factory=SlagoModel.UserPersonal.PersonalPost.PostBlockDOM;
        //发起@AJAX获取用户的帖子
        let AJAX_getUserAllPost=Slago.CancelAjax.before();
        axios.get(Slago.Data.ServerData.IP+
            "getUserAllPost?userid="+Slago.CookieTool.getCookie("id")+
            "&num="+num.toString()+"&page="+page.toString(),{
            cancelToken: AJAX_getUserAllPost.token
        }).then(response=>{
            if(response.data.list.length==0){
                pagenode.toBottomEvent();
            }else {
                //遍历list
                for (let i = 0; i < response.data.list.length; i++) {
                    //if(pagenode.now){
                    left.appendChild(factory.GetDOMNode(response.data.list[i]));
                    //}else{
                    //  right.appendChild(factory.GetDOMNode(response.data.list[i]));
                    //}
                    //pagenode.now=!pagenode.now;
                }
            }
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称用户帖子列表] PersonalPostPage.js");
        });
    }
    namespace.getModel=function(){
        return Slagolib.template.engin(this.Page,{});
    }
    //添加到底事件
    namespace.toBottomEvent=function(pagenode){
        pagenode.isBottom=false;
        pagenode.toBottomEvent=function(){
            if(this.isBottom==false) {
                //获得帖子瀑布父dom
                let left = this.children[0].children[2];
                //提示没有更多帖子
                let alertDom = document.createElement("div");
                alertDom.style.width = "640px";
                alertDom.style.height = "200px";
                alertDom.style.justifyContent = "center";
                alertDom.style.alignItems = "center";
                alertDom.style.display = "flex";
                alertDom.style.color="#707070";
                alertDom.innerText = "没有更多内容";
                this.isBottom = true;
                left.appendChild(alertDom);
            }
        }
    }
    namespace.show=function(){
        //创建页面,推入页面栈
        let pagenode=Slago.CreatePage(this.getModel());
        Slago.CancelAjax.startActivity();//为取消ajax做准备
        //此时的page与num
        pagenode.page=1;
        pagenode.num=12;
        namespace.toBottomEvent(pagenode);
        //pagenode.now=true;//放左还是放右
        //加载第1页数据
        this.loaddata(pagenode,pagenode.page++,pagenode.num);
        //设置底部刷新
        Slago.SlidePageBottomEvent.bottomEvent=function(){
            namespace.loaddata(pagenode,pagenode.page++,pagenode.num);
        }
        //设置回到此页面钩子
        //返回钩子(BACK HOOK)
        pagenode.setBlock=function(){
            namespace.show();//另起页面
            Slago.PageStack.deleteTwoLast();//干掉原来的此页的老页面
        };
        //设置离开此页面钩子
        //销毁钩子(QUIT HOOK)
        pagenode.setNone=function(){
            window.stop();
            Slago.PageStack.stack[Slago.PageStack.stack.length - 1].dom.style.display="none";
        }

    };
    //加入模块
    SlagoModel.UserPersonal.PersonalPost.PersonalPostPage=namespace;
})();
