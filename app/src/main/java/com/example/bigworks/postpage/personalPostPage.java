package com.example.bigworks.postpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.bigworks.R;
import com.example.bigworks.SlagoDB.UserData;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.ImageLoad;
import com.example.bigworks.http.UserData.Http_getLikeAboutFans;
import com.example.bigworks.http.UserData.Http_getUserName;
import com.example.bigworks.http.UserData.Http_getUserProfile;
import com.example.bigworks.utils.UserDataUtils;

import static org.litepal.LitePalApplication.getContext;

public class personalPostPage extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private ImageView headimg;
    private TextView username;
    private TextView profile;
    private TextView aboutnum;
    private TextView fansnum;
    //退出登录句柄
    Handler HANDLER=new Handler((Message msg) -> {
        UserData userData= UserDataUtils.getAllUserData().get(0);//获取用户信息
        if(null==userData){ return false;}
        switch (msg.what){
            case 1:
                username.setText(userData.getName());break;
            case 2:
                aboutnum.setText(userData.getAboutNum());
                fansnum.setText(userData.getFansNum());
                break;
            case 3:
                profile.setText("简介:"+userData.getProfile());
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
        headimg=findViewById(R.id.personal_post_page_headimg);
        username=findViewById(R.id.personal_post_page_name);
        profile=findViewById(R.id.personal_post_page_profile);
        aboutnum=findViewById(R.id.personal_post_page_aboutnum);
        fansnum=findViewById(R.id.personal_post_page_fansnum);
    }
    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("帖子");
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
        exeHandler(1);
        exeHandler(2);
        exeHandler(3);
        //加载头像
        GlideUrl glideUrl= ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ UserDataUtils.getUserid());
        //更新到视图
        Glide.with(getContext()).load(glideUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.headimg_loading)
                .into(headimg);
        //加载username
        new Thread(()->{
            Http_getUserName.fetch(UserDataUtils.getUserid());
            exeHandler(1);
        }).start();
        //获取喜欢 关注 粉丝数量
        new Thread(()->{
            Http_getLikeAboutFans.fetch(UserDataUtils.getUserid());
            //渲染like_about_fans
            exeHandler(2);
        }).start();
        //profile
        new Thread(()->{
            Http_getUserProfile.fetch(UserDataUtils.getUserid());
            exeHandler(3);
        }).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_post_page);
        initElement();
        bindActionForElement();
        dataToView();
    }
}