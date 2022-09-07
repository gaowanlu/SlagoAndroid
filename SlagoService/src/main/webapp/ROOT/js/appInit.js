//使用Hbuilder打包app，解决返回按键问题
document.addEventListener('plusready', function() {
    var webview = plus.webview.currentWebview();
    var first = null;
    plus.key.addEventListener('backbutton', function() {
        webview.canBack(function(e) {
            if (e.canBack) {
                webview.back();
            } else {
                //webview.close(); //hide,quit
                //plus.runtime.quit();
                //首页返回键处理
                //处理逻辑：1秒内，连续两次按返回键，则退出应用；
                //首次按键，提示‘再按一次退出应用’
                if (!first) {
                    first = new Date().getTime();
                    setTimeout(function() {
                        first = null;
                    }, 1000);
                } else {
                    if (new Date().getTime() - first < 1400) {
                        plus.runtime.quit();
                    }
                }
            }
        })
    }, false);
    /*沉浸式延伸的问题:状态栏的高度被忽略*/
    plus.webview.currentWebview().setStyle({
        statusbar:{background:'#ff0000'},top:0,bottom: 0
    });
});
//开始加载模块
document.write('<script type="text/javascript" src="'+"./js/model.js?"+'?random='+Math.random().toString()+'"></script>');
//document.write('<script type="text/javascript" src="'+"./build/app.js?"+'?random='+Math.random().toString()+'"></script>');