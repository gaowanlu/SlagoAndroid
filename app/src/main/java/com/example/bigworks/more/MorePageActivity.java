package com.example.bigworks.more;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bigworks.R;
import com.example.bigworks.login.LoginActivity;

public class MorePageActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private TextView logout;
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
        //退出登录控件
        logout=findViewById(R.id.morepage_logout);
    }
    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("更多");
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });
        //绑定退出登录事件
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入登录界面并，只剩登录一个活动
                Intent intent= new Intent(MorePageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_page);
        initElement();
        bindActionForElement();
    }
}