package com.example.slago.achievement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.slago.R;
import com.example.slago.SlagoDB.UserData;
import com.example.slago.http.UserData.Http_getLikeAboutFans;
import com.example.slago.utils.UserDataUtils;

public class AchievementPageActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private TextView fansnum;
    private TextView likenum;
    Handler HANDLER=new Handler((Message msg) -> {
        UserData userData= UserDataUtils.getAllUserData().get(0);//获取用户信息
        if(null==userData){ return false;}
        switch (msg.what){
            case 1:
                fansnum.setText(userData.getFansNum()+" 粉丝");
                likenum.setText(userData.getLikeNum()+" 获赞");
                break;
            default:;
        }
        return true;
    });
    private void exeHandler(int i){
        Message message=new Message();
        message.what=i;
        HANDLER.sendMessage(message);
    }
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
        fansnum=findViewById(R.id.achievement_page_fansnum);
        likenum=findViewById(R.id.achievement_page_likenum);
    }
    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("成就");
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });
    }

    private void dataToView(){
        Http_getLikeAboutFans.fetch(UserDataUtils.getUserid());
        //渲染like_about_fans
        exeHandler(1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_page);
        initElement();
        bindActionForElement();
        dataToView();
    }
}