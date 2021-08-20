package com.example.bigworks.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.bumptech.glide.load.model.GlideUrl;
import com.example.bigworks.R;
import com.example.bigworks.achievement.AchievementPageActivity;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.ImageLoad;
import com.example.bigworks.more.MorePageActivity;
import com.example.bigworks.persondata.PersonDataPageActivity;
import com.example.bigworks.postpage.personalPostPage;
import com.example.bigworks.utils.UserData;

public class HomeFragment extends Fragment {
    private ImageView headimg;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage,container,false);
    }
    private void binActionForElement(){
        Activity mainActivity=getActivity();
        //头像
        headimg=mainActivity.findViewById(R.id.homepage_headimg);
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
        GlideUrl glideUrl=ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ UserData.getUserid());
        //更新到视图
        Glide.with(getContext()).load(glideUrl).into(headimg);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //为内部组件绑定事件
        binActionForElement();
        //加载数据并上视图
        DataToView();
    }
}
