(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
        '<!-- 用户主页选项栏 -->',
        '<div style="width:640px;height:500px;background-color: rgb(255, 255, 255);">',
        '    <!-- 个人资料设置 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 30px;display: flex;',
        '    border-bottom:1px solid #dfdfdf ;" onclick="SlagoModel.UserPersonal.UserData.userDataPage.show();">',
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/展示信息设置.png" style="height:50%;margin-left: 30px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            个人信息',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '    <!-- 帖子 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 30px;display: flex;',
        '    border-top: 1px solid #dfdfdf;border-bottom:1px solid #dfdfdf ;" onclick="SlagoModel.UserPersonal.PersonalPost.PersonalPostPage.show()">',//点击显示帖子页面
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/ts-picture.png" style="height:42%;margin-left: 34px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            帖子',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '    <!-- 成就 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 10px;display: flex;',
        '    border-bottom:1px solid #dfdfdf ;" onclick="SlagoModel.UserPersonal.PersonalAchievementPage.show()">',//点击显示个人成就页面
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/ts-planet.png" style="height:40%;margin-left: 30px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            成就',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '    <!-- 更多 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 10px;display: flex;" onclick="SlagoModel.UserPersonal.MorePage.show()">',//点击显示更多页面
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/ts-star-2.png" style="height:45%;margin-left: 33px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            更多',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '</div>',
    ].join("");
    namespace.getModel=function(){
        return Slagolib.template.engin(this.Page,{});
    }
    //加入模块
    SlagoModel.UserPersonal.PersonalPageOptionsComponent=namespace;
})();