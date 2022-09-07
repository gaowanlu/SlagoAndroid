(function(){
    //建立命名空间
    let namespace={};
    //发现页
    namespace.template=[
    '<!--页面Header-->',
    '<div style="width:640px;height:150px;position:fixed;background-color:#ffffff;border-bottom:1px rgb(77, 160, 255) solid;">',
    '    <div style="width:640px;height:14px;background-color:#ffffff;"></div>',
    '    <!-- 标题与搜索栏 -->',
    '    <div style="width:640px;height:50%;display: flex;flex-wrap: wrap;">',
    '        <div style="width:140px;background-color:rgb(255, 255, 255);height:100%;',
    '                    font-size:45px;font-weight:bold;display:flex;justify-content: center;',
    '                    align-items: center;color:#11121b;">关注</div>',
    '        <!-- 帖子上传栏 -->',
    '        <div style="width:500px;height:100%;background-color: #ffffff;display: flex;align-items: center;flex-wrap: wrap;">',
    '           <img src="./img/312.png" style="height:40px;margin-left: 415px;" class="hoverPointer" onclick="SlagoModel.PostUpPage.postupPage.show();">',//点击显示帖子上传界面
    '        </div>',
    '    </div>',
    '    <!-- 页面内选择栏 -->',
    '    <div style="width:640px;height:40%;background-color: rgb(255, 255, 255);">',
    '        <!-- 字体栏 -->',
    '        <div style="width:640px;height:80%;background-color: #ffffff;">',
    '            <div style="font-size: 25px;height:100%;color:#0066cc;display: flex;align-items: center;',
    '            margin-left: 23px;">关注</div>',
    '        </div>',
    '        <!-- 滑动条栏 -->',
    '        <div style="width:640px;height:10px;background-color: #ffffff;">',
    '            <div style="width:50px;height:6px;background-color: #0066cc;',
    '            border-radius:3px;margin-left: 24px;"></div>',
    '        </div>',
    '    </div>',
    '</div>',
    '<!--空白-->',
    '<div style="height:150px;width:640px;"></div>'
    ].join("");
    namespace.getModel=function(){
       return Slagolib.template.engin(this.template,{});
    };
    //加入模块
    SlagoModel.AboutPage.Header=namespace;
})();