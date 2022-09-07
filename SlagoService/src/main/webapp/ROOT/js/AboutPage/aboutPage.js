(function(){
    //建立命名空间
    let namespace={};
    //发现页
    namespace.template=[
    '<!--页面容器-->',
    '<div style="width:640px;background-color:#ffffff;">',
    '    <!--页面Header-->',
    '    {{HeaderTemplate}}',
    '    <!--帖子流-->',
    '    {{PostStream}}',
    '</div>',
    ].join("");
    namespace.data={
        HeaderTemplate:SlagoModel.AboutPage.Header.getModel(),//获得导航栏
        PostStream:SlagoModel.FindPage.post_model.getModel(),//获得帖子流
    }
    namespace.getModel=function(){
       return Slagolib.template.engin(this.template,this.data);
    };
    //加入模块
    SlagoModel.AboutPage.aboutPage=namespace;
})();