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
    '                昵称',
    '            </div>',
    '        </div>',
    '        <div style="height:80px;width:100%;"></div>',
    '        <!-- 昵称设置栏 -->',
    '                <!-- 昵称栏 -->',
    '                <div onclick="SlagoModel.UserPersonal.UserData.nameSetingPage.inputClick(this)" class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;display: flex;">',
    '                    <!-- 字体提示栏 -->',
    '                    <div style="width:150px;height:100px;background-color: rgb(255, 255, 255);',
    '                    display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
    '                        昵称',
    '                    </div>',
    '                    <!-- 主题内容 -->',
    '                    <div style="width:420px;height:100%;background-color: rgb(255, 255, 255);">',
    '                        <div style="background-color:rgb(255, 255, 255);height:100%;width:400px;margin-left: 15px;display: flex;align-items: center;justify-content: flex-end;font-size: 20px;color:#707070;overflow: hidden;">',
    '                            <!-- 输入框 -->',
    '                            <input id="SlagoModel.UserPersonal.UserData.nameSetingPage.inputnode" type="text" style="width:100%;height:80%;',
    '                            outline: none;text-align: right;font-size: 25px;color:#1f1f1f;',
    '                            caret-color: #0066cc;" value="{{name}}">',
    '                        </div>',
    '                    </div>',
    '                    <!-- 右箭头 -->',
    '                    <div style="height: 100%;width:40px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
    '                        <img src="./img/箭头_右.png" style="width:40px;">',
    '                    </div>',
    '                </div>',
    '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
    '            <!-- 保存按钮栏 -->',
    '            <div style="width:640px;height:100px;background-color: #ffffff;margin-top: 40px;">',
    '               <div class="hoverPointer" onclick="SlagoModel.UserPersonal.UserData.nameSetingPage.submit(this)" style="width:120px;height:60px;border-radius:30px;',
    '                            background-color: #0066cc;color: #ffffff;display: flex;',
    '                            justify-content: center;align-items: center;font-size: 24px;margin-left: 505px;">',
    '               保存',
    '               </div>',
    '            </div>',
    '    </div>',
    ].join("");
    /*点击输入框水平位置出发点击输入框，增加覆盖范围 */
    namespace.inputClick=function(dom){
        let input=dom.children[1].children[0].children[0];
        input.click();
    }
    namespace.getModel=function(obj){
        return Slagolib.template.engin(this.Page,obj);
    }
    namespace.show=function(){
        //数据
        let DOMData={
            name:"",
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //@AJAX 发起GET请求获取用户昵称
        axios.get(Slago.Data.ServerData.IP+
            "getUserName?id="+Slago.CookieTool.getCookie("id")).then(response=>{
                DOMData.name=response.data.name;
                //重新渲染页面
                PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] nameSetingPage.js");
        });
    }
    //点击保存按钮
    namespace.submit=function(dom){
        //获取输入框node
        let input=document.getElementById("SlagoModel.UserPersonal.UserData.nameSetingPage.inputnode");
        Slago.LoadPage.hover();
        axios.get(Slago.Data.ServerData.IP+
            "updateUserName?"+
            "newname="+input.value).then(function(response){
            setTimeout("Slago.LoadPage.trans();",300);
        }).catch(error=>{
            Slago.LoadPage.move();//失败则去掉加载页
        })
    }
    //加入模块
    SlagoModel.UserPersonal.UserData.nameSetingPage=namespace;
})();