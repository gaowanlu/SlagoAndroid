package site.linkway.slago.more;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import site.linkway.slago.R;

public class UserAgreementActivity extends AppCompatActivity {
    //导航栏
    private View back;
    private TextView titlebar_title;
    //webview
    private WebView xieyi;
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
        xieyi=findViewById(R.id.webview_xieyi);
        xieyi.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
        xieyi.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        xieyi.loadUrl("file:///android_asset/www/index.html");
    }
    private void bindEvent(){
        //设置标题栏文字
        titlebar_title.setText("协议条款");
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(v -> {
            finish();//结束活动
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        initElement();
        bindEvent();
    }
}