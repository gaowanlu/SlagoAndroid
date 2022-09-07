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
    '                    align-items: center;color:#11121b;">发现</div>',
    '        <!-- 搜索栏 -->',
    '        <div style="width:500px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;flex-wrap: wrap;">',
    '            <input type="text" style="width:350px;height:50px;margin-left: 50px;outline:none;text-align: center;',
    '            border-radius: 25px;background-color: rgb(193, 227, 255);font-size: 27px;">',
    '            <img src="./img/搜索.png" class="hoverPointer" style="height:50px;border-top-right-radius: 25px;border-bottom-right-radius:25px ;margin-left: 10px;">',
    '        </div>',
    '    </div>',
    '    <!-- 页面内选择栏 -->',
    '    <div style="width:640px;height:40%;background-color: rgb(255, 255, 255);">',
    '        <!-- 字体栏 -->',
    '        <div style="width:640px;height:80%;background-color: #ffffff;">',
    '            <div style="font-size: 25px;height:100%;color:#0066cc;display: flex;align-items: center;',
    '            margin-left: 23px;">精选</div>',
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
    SlagoModel.FindPage.Header=namespace;
})();