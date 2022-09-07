(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=`
    <!-- 个人成就页组件 -->
    <div style="width:640px;">
        <!-- 导航栏 -->
        <div style="z-index:1000;width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">
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
                成就
            </div>
        </div>
        <div style="height:80px;"></div>
        <div style="position: relative;width: 440px;height:400px;float: left;">
            <img src="./img/成就/新星系探索.png" style="
            width: 440px;margin-top: 20px;position: absolute;">  
            <img src="./img/成就/星球019.png" style="width: 180px;
            position: absolute;margin-top: 190px;margin-left: 40px;"> 
            <div style="position: absolute;height:100px;width:100%;
            margin-top: 40px;color: #ffffff;display: flex;
            justify-content: center;align-items: center;font-size: 40px;font-weight: bolder;">
            {{fans}}  &nbsp&nbsp粉丝
            </div>                
        </div>
        <div style="position: relative;width: 190px;height:400px;margin-left: 440px;">
            <img src="./img/成就/火箭01.png" style="width: 100%;position: absolute;margin-top: 200px;">    
        </div>
        <div style="position: relative;width: 500px;margin-left:80px;height:400px;margin-top: 70px;">
            <img src="./img/成就/太空探索与航天飞机.png" style="width: 100%;position: absolute;"> 
            <div style="position: absolute;height:200px;width:100%;
            margin-top: 80px;color: #ffffff;display: flex;
            justify-content: center;align-items: center;font-size: 60px;font-weight: bolder;">
            {{likes}} &nbsp获赞
            </div>   
        </div>
        <div style="position: relative;width: 640px;height:500px;">
            <img src="./img/成就/星系02.png" style="position: absolute;
            width: 320px;">    
            <img src="./img/成就/流星04.png" style="position: absolute;
            height:500px;">   
            <img src="./img/成就/星球011.png" style="position: absolute;
            width: 320px;margin-left: 320px;margin-top: 100px;">   
        </div>
    </div>
    `;
    namespace.getModel=function(DOMData){
        return Slagolib.template.engin(this.Page,DOMData);
    }
    namespace.show=function(){
        Slago.backgroundColor.seting("#160a52");
        //数据
        let DOMData={
            fans:0,
            likes:0
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        PAGENODE.setNone=function(){
            Slago.backgroundColor.default();
            Slago.PageStack.deleteDOMNode(this);
        }
        //@AJAX 发起请求获得用户 关注 喜欢 收藏数量
        axios.get(Slago.Data.ServerData.IP+
            "getLikeAboutFans?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.likes=response.data.likeNum;
            DOMData.fans=response.data.fansNum;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] nameSetingPage.js");
        });
    }
    //加入模块
    SlagoModel.UserPersonal.PersonalAchievementPage=namespace;
})();