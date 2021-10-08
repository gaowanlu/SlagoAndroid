package com.example.slago.postpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.slago.R;
import com.example.slago.SlagoDB.UserData;
import com.example.slago.activityCollector.BaseActivity;
import com.example.slago.fragment.LikePostListFragment;
import com.example.slago.fragment.MyPostListFragment;
import com.example.slago.http.APIData;
import com.example.slago.http.ImageLoad;
import com.example.slago.http.UserData.Http_getLikeAboutFans;
import com.example.slago.http.UserData.Http_getUserName;
import com.example.slago.http.UserData.Http_getUserProfile;
import com.example.slago.utils.UserDataUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class UserPeronalActivity extends BaseActivity {
    private View back;
    private TextView titlebar_title;
    private ImageView headimg;
    private TextView username;
    private TextView profile;
    private TextView aboutnum;
    private TextView fansnum;



    //我的帖子和我喜欢的帖子进行切换
    private List<Fragment> fragmentList;
    private ViewPager2 myViewPager;
    private TabFragmentPagerAdapter viewPagerAdapter;
    private Fragment mypostfragment;
    private Fragment likepostfragment;
    TabLayout pagerTableLayout;

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
        //获得viewPager
        myViewPager=findViewById(R.id.personal_post_page_viewPager);
        //将需要切换的fragment放进list中
        fragmentList=new ArrayList<>();
        mypostfragment=new MyPostListFragment();
        likepostfragment=new LikePostListFragment();
        fragmentList.add(mypostfragment);
        fragmentList.add(likepostfragment);
        viewPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        myViewPager.setAdapter(viewPagerAdapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面,即我的帖子
        pagerTableLayout=findViewById(R.id.personal_post_page_tablayout);
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
        myViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                pagerTableLayout.setScrollPosition(position,positionOffset,true);
            }
        });
        pagerTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewPager.setCurrentItem(tab.getPosition());
                Log.e("Position",Integer.toString(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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