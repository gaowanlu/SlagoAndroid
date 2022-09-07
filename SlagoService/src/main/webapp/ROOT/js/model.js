
(function(){
    window.SlagoModel={};
    let IMPORT=function(src){
        document.write('<script type="text/javascript" src="'+src+'?random='+Math.random().toString()+'"></script>');
    };
    //操作库
        IMPORT("./js/lib/Slagolib.js");//Slago.js库
        IMPORT("./js/lib/axios.js");//引入axios.js
    //软件界面框架
        IMPORT("./js/Slago.js");//UI基本解决方案
    //主页
    SlagoModel.FindPage={};
        IMPORT("./js/FindPage/Header.js");//Header
        IMPORT("./js/FindPage/post_model.js");//主页帖子流组件
        IMPORT("./js/FindPage/findPage.js");//发现页
    //帖子悬浮页面-帖子悬浮层模块
    SlagoModel.PostSuspensionPage={};
        IMPORT("./js/PostSuspensionPage/postPage.js");//帖子观看层
    //用户个人页模块
    SlagoModel.UserPersonal={};
        IMPORT("./js/UserPersonal/PersonalPageOptionsComponent.js");//选项组件
        IMPORT("./js/UserPersonal/my_page.js");//个人主页
        IMPORT("./js/UserPersonal/PersonalAchievementPage.js");//个人成就页面
        IMPORT("./js/UserPersonal/MorePage.js");//更多详情页面
        SlagoModel.UserPersonal.PersonalPost={};
        IMPORT("./js/UserPersonal/PersonalPost/PostBlockDOM.js");//生成帖子dom node
        IMPORT("./js/UserPersonal/PersonalPost/PersonalPostPage.js");//个人帖子页面
        SlagoModel.UserPersonal.UserData={};//个人信息
            IMPORT("./js/UserPersonal/UserData/userDataPage.js");//个人信息页
            IMPORT("./js/UserPersonal/UserData/nameSetingPage.js");//昵称设置页
            IMPORT("./js/UserPersonal/UserData/sexSetingPage.js");//性别设置页
            IMPORT("./js/UserPersonal/UserData/signature.js");//个性签名设置页
    //关注页面
    SlagoModel.AboutPage={};
        IMPORT("./js/AboutPage/Header.js");//导入导航栏
        IMPORT("./js/AboutPage/aboutPage.js");//关注页
    //帖子上传页面
    SlagoModel.PostUpPage={};
        IMPORT("./js/PostUpPage/postupPage.js");//上传页面
        IMPORT("./js/PostUpPage/PostUpModel.js");//帖子上传处理
    //登录模块
    SlagoModel.LoginModel={};
        IMPORT("./js/LoginModel/loginPage.js");//登录页
})();
