package com.example.slago.more;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.slago.R;
import com.example.slago.activityCollector.BaseActivity;
import com.example.slago.login.LoginActivity;

public class MorePageActivity extends BaseActivity {
    private View back;
    private TextView titlebar_title;
    private TextView logout;
    private TextView github_target;
    private TextView tiaokuan;
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
        //退出登录控件
        logout=findViewById(R.id.morepage_logout);
        github_target=findViewById(R.id.github_target);
        tiaokuan=findViewById(R.id.morepage_user_tiaokuan);
    }
    private void httpTest(){
//        new Thread(()->{
//            Log.e("setUserName:", Boolean.toString(Http_setUserName.push("1234")));
//            Log.e("setUserProfile", Boolean.toString(Http_setUserProfile.push("hello world")));
//            Log.e("setUserSex", Boolean.toString(Http_setUserSex.push("男")));
//            Log.e("getUserName:", Http_getUserName.fetch(UserDataUtils.getUserid()));
//            Log.e("getUserProfile", Http_getUserProfile.fetch(UserDataUtils.getUserid()));
//            Log.e("getUserSex", Http_getUserSex.fetch(UserDataUtils.getUserid()));
//        }).start();
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
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;startActivity(intent);
        });
        //跳转到github仓库
        github_target.setOnClickListener(v->{
            Intent intent =new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://github.com/gaowanlu/SlagoAndroid");
            intent.setData(uri);
            startActivity(intent);
        });
        //用户条款
        tiaokuan.setOnClickListener(v->{
            Intent intent=new Intent(this,UserAgreementActivity.class);
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