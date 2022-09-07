//=>UserPersonal.UserData.MorePage=>start
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=`
    <div style="width:640px;background-color: #a5c2f5;">
        <!-- 导航栏 -->
        <div style="width:640px;height:80px;background-color: rgb(255, 255, 255);
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
            <div style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;
            align-items:center;justify-content: center;font-size:27px;">
                更多
            </div>
        </div>
        <div style="height:80px;"></div>
        <div style="height:160px;width:640px;display: flex;
  justify-content: center;
  align-items: center;color: #ffffff;font-size: 70px;font-weight: bold;">
            Slago
        </div>
        <div style="height:50px;width:640px;display: flex;justify-content: center;
                  font-size: 30px;color: #ffffff;font-weight: bold;">
            分享乐趣，快乐生活
        </div>
        <div style="height:50px;width: 440px;margin-left: 200px;margin-top: 50px;
  font-size: 35px;color: #ffffff;font-weight: bold;">
            <a style="text-decoration: none;color:#ffffff;" href="https://github.com/gaowanlu/Slago-Web">Github开源</a>
         </div>
        <div style="height:60px;width: 150px;margin-left: 450px;
  font-size: 30px;color: #ffffff;font-weight: bold;">V1.0.1</div>
        <div style="height:60px;width: 450px;margin-left: 50px;
              font-size: 30px;color: #ffffff;font-weight: bold;">Design By Wanlu</div>
                      <div style="height:60px;width: 450px;margin-left: 150px;
              font-size: 30px;color: #ffffff;font-weight: bold;">heizuboriyo@gmail.com</div>
        <img src="./img/海豚小女孩.png" style="width: 640px;margin-top: 200px;">
    </div>

       `;
    namespace.getModel=function(DOMData){
        return Slagolib.template.engin(this.Page,DOMData);
    }
    namespace.show=function(){
        Slago.backgroundColor.seting("#a5c2f5");
        //数据
        let DOMData={

        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        PAGENODE.setNone=function(){
            Slago.backgroundColor.default();
            Slago.PageStack.deleteDOMNode(this);
        }
    }
    //加入模块
    SlagoModel.UserPersonal.MorePage=namespace;
})();
//=>UserPersonal.UserData.MorePage=>end