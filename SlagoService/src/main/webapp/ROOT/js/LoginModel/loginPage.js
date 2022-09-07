//=>SlagoModel.LoginModel.loginPage=>start
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    // namespace.Page=[
    //
    // ].join("");
    namespace.Page= `
    <div style="width:640px;background-color: #0066cc;display: flex;align-items: center;">
        <div style="width:640px;height:640px;margin-top: 100px;
            display: flex;justify-content: center;
            align-items:center;flex-wrap: wrap;">
            <!--logo区域-->
            <div style="width:400px;
                        height:200px;
                        display: flex;justify-content: center;align-items: center;
                        font-size: 56px;font-weight: bold;color: #fafafa;">
                       Hi! Slago
            </div>
            <!--账号密码区域-->
            <div style="width:640px;height:360px;">
                <!--账号-->
                <input type="text" placeholder="账号" 
                                style="
                                width:520px;margin-left: 60px;
                                height:80px;border-radius: 40px;
                                outline: none;text-align: center;
                                font-size: 28px;">
                <!--密码-->
                <input type="password" placeholder="密码" 
                                style="
                                width:520px;margin-left: 60px;margin-top: 30px;
                                height:80px;border-radius: 40px;
                                outline: none;text-align: center;
                                font-size: 28px;">
                <!--登录按钮-->                  
                 <div onclick="SlagoModel.LoginModel.loginPage.login(this);" class="hoverPointer" style="width:120px;height:60px;border: 2px solid gold;
                                                  border-radius:30px;margin-top: 40px;
                                                  background-color: #0066cc;font-weight: bold;
                                                  color: #ffffff;display: flex;
                                                  justify-content: center;align-items: center;
                                                  font-size: 24px;margin-left: 460px;">
                   登录
                 </div>
                 <!--账号安全中心-->
                 <div class="hoverPointer" style="width:250px;margin-left: 50px;
                            height:50px;display: flex;justify-content: center;
                            align-items: center;font-size: 23px;color: aliceblue;">
                    注册账号或忘记密码
                 </div>
            </div>
            
        </div>

    </div>
    `;
    namespace.login=function(button){
        /*首先进行AJAX请求，进行身份验证，如果验证成功，则直接离开登录界面进行信息刷新与请求
        * 否则留下提示请输入账号与密码,在让用户进行请求登录，获取个人信息
        */
        //AJAX身份验证:验证成功则更新身份信息，否则停止不跳转，让用户登录
        //获取账号与密码input node
        let input_id=button.parentNode.children[0];
        let input_password=button.parentNode.children[1];
        //@AJAX 发起GET请求登录
        Slago.LoadPage.hover();
        //登录api不用进行session判断
        axios.get(Slago.Data.ServerData.IP.replace("apis/","")+
            "SlagoService_Login?id="+input_id.value.trim()+
            "&password="+input_password.value.trim()).then(response=>{
            console.log(response.data);
            if(response.data.result=="true"){//验证成功
                Slago.LoadPage.move();
                this.loginSuccess();//登录成功
            }else{
                Slago.LoadPage.move();
            }
        }).catch(error=>{
            Slago.LoadPage.move();
            console.log("Slago::ERROR[登录失败] nameSetingPage.js");
        });
    }
    namespace.getModel = function (DOMData) {
        return Slagolib.template.engin(this.Page, DOMData);
    }
    namespace.show = function () {
        Slago.backgroundColor.seting("#0066cc");
        //数据
        let DOMData={

        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //返回钩子(BACK HOOK)
        PAGENODE.setBlock=function(){
            //-----FINAL
            namespace.show();//另起页面
            Slago.PageStack.deleteTwoLast();//干掉原来的此页的老页面
            //-----FINAL
        };
        PAGENODE.setNone=function(){
            Slago.backgroundColor.default();//恢复背景颜色
            Slago.PageStack.deleteDOMNode(this);
        }
        /*@AJAX身份验证
        * 身份验证api不用进行session判断，不拦截
        * */
        axios.get(Slago.Data.ServerData.IP.replace("apis/","")+
            "SlagoService_Authentication").then(response=>{
                if(response.data.result=="true"){
                    this.loginSuccess();
                }
        }).catch(error=>{
            console.log("SlagoError::身份校验请求异常");
        });
    }
    namespace.loginSuccess=function(){
        //成功TODO:
        //初始化三个主界面
        //渲染瀑布流
        document.getElementById("Slago.FindPage").innerHTML = SlagoModel.FindPage.findPage.getModel();
        //渲染瀑布流
        document.getElementById("Slago.AboutPage").innerHTML = SlagoModel.AboutPage.aboutPage.getModel();
        //渲染个人主页
        SlagoModel.UserPersonal.my_page.show();
        //退出登录页面
        Slago.PageStack.pop();
        //恢复背景颜色
        Slago.backgroundColor.default();
    }
    //加入模块
    SlagoModel.LoginModel.loginPage=namespace;
})();
//=>SlagoModel.LoginModel.loginPage=>end