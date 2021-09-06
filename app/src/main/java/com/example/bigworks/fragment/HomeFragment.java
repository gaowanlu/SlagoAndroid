package com.example.bigworks.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.bigworks.R;
import com.example.bigworks.SlagoDB.UserData;
import com.example.bigworks.achievement.AchievementPageActivity;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.ImageLoad;
import com.example.bigworks.http.UserData.Http_getLikeAboutFans;
import com.example.bigworks.http.UserData.Http_getUserName;
import com.example.bigworks.http.Utils;
import com.example.bigworks.json.getLikeAboutFans;
import com.example.bigworks.json.getUserName;
import com.example.bigworks.more.MorePageActivity;
import com.example.bigworks.persondata.PersonDataPageActivity;
import com.example.bigworks.postpage.personalPostPage;
import com.example.bigworks.utils.UserDataUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    private ImageView headimg;//头像
    private TextView username;//用户昵称
    private TextView aboutfanslike;

    Handler HANDLER=new Handler((Message msg) -> {
        UserData userData=UserDataUtils.getAllUserData().get(0);//获取用户信息
        if(null==userData){ return false;}
        switch (msg.what){
            case 1:
                username.setText(userData.getName());break;
            case 2:
                aboutfanslike.setText(userData.getAboutNum()+" 关注 "+userData.getFansNum()+" 粉丝 "+userData.getLikeNum()+" 喜欢");
                break;
            default:;
        }
        return true;
    });
    private  void refreshData(){
        //获取昵称
        new Thread(()->{
            Http_getUserName.fetch(UserDataUtils.getUserid());
            Message message=new Message();
            message.what=1;
            HANDLER.sendMessage(message);
        }).start();
        //获取喜欢 关注 粉丝数量
        new Thread(()->{
            Http_getLikeAboutFans.fetch(UserDataUtils.getUserid());
            //渲染like_about_fans
            Message message=new Message();
            message.what=2;
            HANDLER.sendMessage(message);
        }).start();
    }
    @Override
    public void onAttach(@NonNull  Context context) {
        super.onAttach(context);
    }

    //为碎片创建视图(加载布局)时调用
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage,container,false);
    }
    private void binActionForElement(){
        Activity mainActivity=getActivity();
        //头像
        headimg=mainActivity.findViewById(R.id.homepage_headimg);
        //昵称
        username=mainActivity.findViewById(R.id.homepage_name);
        aboutfanslike=mainActivity.findViewById(R.id.homepage_aboutfanslike);
        //个人信息按钮
        TextView textView=mainActivity.findViewById(R.id.homepage_persondata);
        textView.setClickable(true);
        textView.setOnClickListener(v->{
                Intent intent= new Intent(getContext(), PersonDataPageActivity.class);
                startActivity(intent);
        });
        //成就按钮跳转
        textView=mainActivity.findViewById(R.id.homepage_achive);
        textView.setClickable(true);
        textView.setOnClickListener(v->{
                Intent intent= new Intent(getContext(), AchievementPageActivity.class);
                startActivity(intent);
        });
        //更多按钮跳转
        textView=mainActivity.findViewById(R.id.homepage_more);
        textView.setClickable(true);
        textView.setOnClickListener(v->{
                Intent intent= new Intent(getContext(), MorePageActivity.class);
                startActivity(intent);
        });
        //帖子页面跳转
        textView=mainActivity.findViewById(R.id.homepage_post);
        textView.setClickable(true);
        textView.setOnClickListener(v->{
                Intent intent= new Intent(getContext(), personalPostPage.class);
                startActivity(intent);
        });
    }

    //加载数据到视图
    private void DataToView(){
        //加载头像
        GlideUrl glideUrl=ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ UserDataUtils.getUserid());
        //更新到视图
        Glide.with(getContext()).load(glideUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.headimg_loading)
                .into(headimg);
        UserData userData=UserDataUtils.getAllUserData().get(0);
        if(null!=userData){
            //显示昵称
            username.setText(userData.getName());
            //显示  关注 粉丝 喜欢
            aboutfanslike.setText(userData.getAboutNum()+" 关注 "+userData.getFansNum()+" 粉丝 "+userData.getLikeNum()+" 喜欢");
        }
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //为内部组件绑定事件
        binActionForElement();
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //加载数据并上视图
        DataToView();
        //刷新所需数据重新请求
        new Thread(()->{
            refreshData();
        }).start();
    }
}
