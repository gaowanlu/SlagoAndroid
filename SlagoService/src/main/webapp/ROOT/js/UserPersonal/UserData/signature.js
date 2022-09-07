//=>SlagoModel.UserPersonal.UserData.signature=>start
//个性签名设置
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
        '    <!-- 单个资料信息设置页 -->',
        '    <div style="width:640px;height:600px;background-color: #ffffff;">',
        '        <!-- 导航栏 -->',
        '        <div',
        '            style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
        '            <!-- 返回按键 -->',
        '            <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
        '                style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
        '                <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
        '                <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
        '                    <span style="font-size: 27px;color:#0066cc;">返回</span>',
        '                </div>',
        '            </div>',
        '            <!--导航栏字体栏-->',
        '            <div',
        '                style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
        '                个性签名',
        '            </div>',
        '        </div>',
        '        <div style="height:80px;width:100%;"></div>',
        '         <div style="width:640px;height:400px;">',
        '            <textarea style="width: 600px;height:200px;outline: none;resize: none;',
        '                             color:rgb(36, 36, 36);padding: 10px;background-color: #ffffff;',
        '                             font-size: 26px;margin-left: 10px;">{{profile}}</textarea>',
        '            <!-- 保存按钮栏 -->',
        '            <div style="width:640px;height:100px;background-color: #ffffff;margin-top: 40px;">',
        '                <div onclick="SlagoModel.UserPersonal.UserData.signature.submit(this);" class="hoverPointer" style="width:120px;height:60px;border-radius:30px;',
        '                                                                            background-color: #0066cc;',
        '                                                                            color: #ffffff;display: flex;',
        '                                                                            justify-content: center;align-items: center;',
        '                                                                            font-size: 24px;margin-left: 505px;">',
        '                保存',
        '                </div>',
        '            </div>',
        '        </div>',
        '    </div>'
    ].join("");
    namespace.getModel = function (DOMData) {
        return Slagolib.template.engin(this.Page, DOMData);
    }
    namespace.show = function () {
        //数据
        let DOMData={
            profile:"",
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //@AJAX 发起GET请求获取用户个性签名
        axios.get(Slago.Data.ServerData.IP+
            "getUserProfile?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.profile=response.data.result;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求个性签名信息失败] signature.js");
        });
    }
    //提交个性签名
    namespace.submit=function(buttonNode){
        //获得testarea dom node
        let textarea=buttonNode.parentNode.parentNode.children[0];
        //@AJAX更新个性签名
        Slago.LoadPage.hover();
        axios.get(Slago.Data.ServerData.IP+
            "/setUserProfile?"+
            "newProfile="+textarea.value).then(function(response){
            setTimeout("Slago.LoadPage.trans();",300);
        }).catch(error=>{
            Slago.LoadPage.move();//失败则去掉加载页
        })
    }
    //加入模块
    SlagoModel.UserPersonal.UserData.signature=namespace;
})();
//=>SlagoModel.UserPersonal.UserData.signature=>end