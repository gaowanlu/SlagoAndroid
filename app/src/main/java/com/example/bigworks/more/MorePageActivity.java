package com.example.bigworks.more;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigworks.R;
import com.example.bigworks.login.LoginActivity;
import com.example.bigworks.utils.GlideEngine;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

public class MorePageActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private TextView logout;
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_combar_back);
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
        back.setOnClickListener(v -> {
                finish();//结束活动
        });
        //绑定退出登录事件
        logout.setOnClickListener(v->{
                //进入登录界面并，只剩登录一个活动
                Intent intent= new Intent(MorePageActivity.this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                startActivity(intent);
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