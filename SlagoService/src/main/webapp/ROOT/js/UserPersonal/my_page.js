(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=`
    <!-- 用户个人页面 -->
    <div style="width:640px;background-color: rgb(255, 255, 255);">
        <!-- 用户信息块\昵称\关注\粉丝\头像等元素 -->
        <div style="width:620px;
                    height:200px;margin-left: 10px;
                    display: flex;flex-wrap: wrap;justify-content: space-around;
                    align-items: center;margin-top: 30px;
                    ">
            <!-- 头像 -->
            <div style="width:140px;height:140px;
                        background-color:rgb(255, 255, 255);
                        border-radius:80px;">
                        <!--点击头像进入个人信息页面-->
                <img onclick="SlagoModel.UserPersonal.UserData.userDataPage.show();" src="{{userHeadImgURL}}"
                    style="width:100%;height:100%;border-radius:50%;">
            </div>
            <!-- 个人成就 -->
            <div style="width:440px;height:140px;">
                <!-- 用户名 -->
                <div style="width:100%;
                            height:70px;
                            background-color:rgb(255, 255, 255);
                            display:flex;
                            justify-content:center;
                            align-items:center;
                            font-size: 27px;
                            color:rgb(68,68,68);">
                    <p>{{UserName}}</p>
                </div>
                <!-- 成就栏 -->
                <div style="width:100%;
                            height:70px;
                            background-color:rgb(255, 255, 255);
                            display: flex;
                            justify-content:center;
                            align-items: center;
                            font-size: 24px;
                            color:rgb(68,68,68);">
                    <p>{{AboutNum}} 关注 {{FansNum}} 粉丝 {{LikeNum}} 喜欢</p>
                </div>
            </div>
        </div>
    {{PersonalPageOptionsComponent}}
    </div>`;

    namespace.getModel=function(DOMData){
        return Slagolib.template.engin(this.Page,DOMData);
    }
    namespace.show=function(){
        let PAGENODE=document.getElementById("Slago.UserPage");
        let DOMData={
            //获取选择模块
            PersonalPageOptionsComponent:SlagoModel.UserPersonal.PersonalPageOptionsComponent.getModel(),
            //头像地址
            userHeadImgURL:Slago.Data.ServerData.IP+"getUserHeadImg?id="+
                Slago.CookieTool.getCookie("id")+"&random="+Math.random().toString(),
            UserName:"loading...",//昵称
            //喜欢数量
            LikeNum:"_",
            //粉丝数量
            FansNum:"_",
            //关注数量
            AboutNum:"_"
        };
        /*为个人页设置返回钩子，以便刷新最新个人信息
        * */
        PAGENODE.setBlock=function(){
            PAGENODE.style.display="block";
            namespace.show();
            // console.log("重新刷新个人页内容");
        }
        PAGENODE.innerHTML=this.getModel(DOMData);
        //@AJAX 发起GET请求获取用户昵称
        axios.get(Slago.Data.ServerData.IP+
            "getUserName?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.UserName=response.data.name;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] nameSetingPage.js");
        });
        //@AJAX 发起请求获得用户 关注 喜欢 收藏数量
        axios.get(Slago.Data.ServerData.IP+
            "getLikeAboutFans?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.LikeNum=response.data.likeNum;
            DOMData.AboutNum=response.data.aboutNum;
            DOMData.FansNum=response.data.fansNum;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] nameSetingPage.js");
        });
    }
    //加入模块
    SlagoModel.UserPersonal.my_page=namespace;
})();