//=>SlagoModel.UserPersonal.UserData.sexSetingPage=>start
(function () {
    //建立命名空间
    let namespace = {};
    //渲染页
    namespace.Page = [
        '    <!-- 性别设置界面 -->',
        '    <div style="width:640px;background-color: rgb(255, 255, 255);">',
        '        <!-- 单个资料信息设置页 -->',
        '        <div style="width:640px;background-color: #ffffff;">',
        '            <!-- 导航栏 -->',
        '            <div',
        '                style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
        '                <!-- 返回按键 -->',
        '                <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
        '                    style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
        '                    <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
        '                    <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
        '                        <span style="font-size: 27px;color:#0066cc;">返回</span>',
        '                    </div>',
        '                </div>',
        '                <!--导航栏字体栏-->',
        '                <div',
        '                    style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
        '                    性别',
        '                </div>',
        '            </div>',
        '            <div style="height:80px;width:100%;"></div>',
        '            <!-- 性别设置栏 -->',
        '',
        '',
        '            <!-- 男 -->',
        '            <div id="SlagoModel.UserPersonal.UserData.sexSetingPage.man" ',
        'onclick = "SlagoModel.UserPersonal.UserData.sexSetingPage.choose(this)" class= "hoverPointer"',
        '                style="width:640px;height:100px;background-color: {{men}};display: flex;">',
        '                <!-- 字体提示栏 -->',
        '                <div style="width:150px;height:100px;',
        '                            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
        '                    男',
        '                </div>',
        '                <!-- 右箭头 -->',
        '                <div',
        '                    style="margin-left:420px;height: 100%;width:40px;display: flex;justify-content: center;align-items: center;">',
        '                    <img src="./img/箭头_右.png" style="width:40px;">',
        '                </div>',
        '            </div>',
        '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
        '            <!-- 女 -->',
        '            <div  class="hoverPointer" id="SlagoModel.UserPersonal.UserData.sexSetingPage.woman" ',
        '   onclick = "SlagoModel.UserPersonal.UserData.sexSetingPage.choose(this)"          ',
        '   style="width:640px;height:100px;background-color: {{women}};display: flex;">',
        '                <!-- 字体提示栏 -->',
        '                <div style="width:150px;height:100px;',
        '                            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
        '                    女',
        '                </div>',
        '                <!-- 右箭头 -->',
        '                <div',
        '                    style="margin-left:420px;height: 100%;width:40px;display: flex;justify-content: center;align-items: center;">',
        '                    <img src="./img/箭头_右.png" style="width:40px;">',
        '                </div>',
        '            </div>',
        '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
        '            <!-- 其他 -->',
        '            <div  class="hoverPointer"',
        ' id="SlagoModel.UserPersonal.UserData.sexSetingPage.other" onclick = "SlagoModel.UserPersonal.UserData.sexSetingPage.choose(this)" ',
        '                style="width:640px;height:100px;background-color: {{other}};display: flex;">',
        '                <!-- 字体提示栏 -->',
        '                <div style="width:150px;height:100px;',
        '                            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
        '                    保密',
        '                </div>',
        '                <!-- 右箭头 -->',
        '                <div',
        '                    style="margin-left:420px;height: 100%;width:40px;display: flex;justify-content: center;align-items: center;">',
        '                    <img src="./img/箭头_右.png" style="width:40px;">',
        '                </div>',
        '            </div>',
        '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
        '            <!-- 保存按钮栏 -->',
        '            <div style="width:640px;height:100px;background-color: #ffffff;margin-top: 40px;">',
        '                <div onclick="SlagoModel.UserPersonal.UserData.sexSetingPage.submit(this);" class="hoverPointer" style="width:120px;height:60px;border-radius:30px;',
        '                                    background-color: #0066cc;color: #ffffff;display: flex;',
        '                                    justify-content: center;align-items: center;font-size: 24px;margin-left: 505px;">',
        '                    保存',
        '                </div>',
        '            </div>',
        '        </div>',
        '    </div>',
    ].join("");
    namespace.getModel = function () {
        return Slagolib.template.engin(this.Page, {});
    }
    /*AJAX请求性别信息后显示性别*/
    namespace.setSex=function(sex){
        let nodes = [
            document.getElementById("SlagoModel.UserPersonal.UserData.sexSetingPage.man"),
            document.getElementById("SlagoModel.UserPersonal.UserData.sexSetingPage.woman"),
            document.getElementById("SlagoModel.UserPersonal.UserData.sexSetingPage.other")
        ];
        switch (sex){
            case "男":
                nodes[0].style.backgroundColor="rgb(180, 218, 253)";
                break;
            case "女":
                nodes[1].style.backgroundColor="rgb(180, 218, 253)";
                break;
            case "保密":
                nodes[2].style.backgroundColor="rgb(180, 218, 253)";
                break;
        }
    }
    namespace.show = function () {
        //数据
        let DOMData={
            men:"#ffffff",
            women:"#ffffff",
            other:"#ffffff"
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //@AJAX 发起get请求获取用户性别
        axios.get(Slago.Data.ServerData.IP+
            "getUserSex?id="+Slago.CookieTool.getCookie("id")).then(response=>{
                this.setSex(response.data.result);
        }).catch(error=>{
            console.log("Slago::ERROR[请求性别信息失败] userDataPage.js");
        });

    }
    //点击保存按钮：请求更改性别
    namespace.submit=function(button){
        //判断现在的性别
        let newSex="";
        let ids = [
            "SlagoModel.UserPersonal.UserData.sexSetingPage.man",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.woman",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.other"
        ];
        if(document.getElementById(ids[0]).style.backgroundColor=="rgb(180, 218, 253)"){
            newSex="男";
        }else if(document.getElementById(ids[1]).style.backgroundColor=="rgb(180, 218, 253)"){
            newSex="女";
        }else if(document.getElementById(ids[2]).style.backgroundColor=="rgb(180, 218, 253)"){
            newSex="保密";
        }
        Slago.LoadPage.hover();
        //@AJAX 更改性别请求
        axios.get(Slago.Data.ServerData.IP+
            "setUserSex?newSex="+newSex).then(response=>{
            Slago.LoadPage.trans();
            setTimeout("Slago.LoadPage.move();",500);
        }).catch(error=>{
            Slago.LoadPage.move();
            console.log("Slago::ERROR[请求修改性别失败] sexSetingPage.js");
        });
    }


    //选择按钮:选择按钮 切换节点颜色
    namespace.choose = function (dom) {
        //获得选择按钮的三个节点
        let list = [];
        let ids = [
            "SlagoModel.UserPersonal.UserData.sexSetingPage.man",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.woman",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.other"
        ];
        for (let i = 0; i < ids.length; i++) {
            let obj = {};
            obj.id = ids[i];
            obj.node = document.getElementById(ids[i]);
            list.push(obj);
        }
        for (let i = 0; i < list.length; i++) {
            if (dom.id != list[i].id) {
                list[i].node.style.backgroundColor = "#ffffff";
            } else {
                list[i].node.style.backgroundColor = "rgb(180, 218, 253)";
            }
        }
    }

    //加入模块
    SlagoModel.UserPersonal.UserData.sexSetingPage = namespace;
})();
//=>SlagoModel.UserPersonal.UserData.sexSetingPage=>end