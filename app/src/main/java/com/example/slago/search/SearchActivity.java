package com.example.slago.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.slago.R;

public class SearchActivity extends AppCompatActivity {
    //导航栏
    private View back;
    private TextView titlebar_title;
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
    }
    private void bindEvent(){
        //设置标题栏文字
        titlebar_title.setText("搜索");
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(v -> {
            finish();//结束活动
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initElement();
        bindEvent();
    }
}