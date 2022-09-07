package site.linkway.slago.postpage;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import site.linkway.slago.R;
import site.linkway.slago.SlagoDB.UserData;
import site.linkway.slago.activityCollector.BaseActivity;
import site.linkway.slago.fragment.LikePostListFragment;
import site.linkway.slago.fragment.MyPostListFragment;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.About.Http_about;
import site.linkway.slago.http.About.Http_checkAbouted;
import site.linkway.slago.http.About.Http_unabout;
import site.linkway.slago.http.ImageLoad;
import site.linkway.slago.http.UserData.Http_getLikeAboutFans;
import site.linkway.slago.http.UserData.Http_getUserName;
import site.linkway.slago.http.UserData.Http_getUserProfile;
import site.linkway.slago.recyclerView.Adapter.Post;
import site.linkway.slago.utils.UserDataUtils;

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
    private TextView fansText;
    private TextView aboutText;
    private Button aboutButton;//关注按钮
    private String Userid;
    private boolean abouted=false;//关注标志符，初始化没有关注



    //我的帖子和我喜欢的帖子进行切换
    private List<Fragment> fragmentList;
    private ViewPager2 myViewPager;
    private TabFragmentPagerAdapter viewPagerAdapter;
    private Fragment mypostfragment;
    private Fragment likepostfragment;
    TabLayout pagerTableLayout;
    private UserData userData;
    //退出登录句柄
    Handler HANDLER=new Handler((Message msg) -> {
        if(null==userData){ return false;}
        switch (msg.what){
            case 1:
                String name=userData.getName();
                if(name.length()>8){name=name.substring(0,7);}
                username.setText(name);break;
            case 2:
                aboutnum.setText(userData.getAboutNum());
                fansnum.setText(userData.getFansNum());
                break;
            case 3:
                profile.setText("简介:"+userData.getProfile());
                break;
            case 4:
                if(!abouted){//已经关注
                    aboutButton.setText("未关注");
                    aboutButton.setBackground(getResources().getDrawable(R.drawable.about_no_btn));
                }else{//未关注
                    aboutButton.setText("已关注");
                    aboutButton.setBackground(getResources().getDrawable(R.drawable.about_yes_btn));
                }
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
        aboutText=findViewById(R.id.personal_post_page_about);
        fansText=findViewById(R.id.personal_post_page_fans);
        aboutButton=findViewById(R.id.about_button);
        //获得viewPager
        myViewPager=findViewById(R.id.personal_post_page_viewPager);
        //将需要切换的fragment放进list中
        fragmentList=new ArrayList<>();
        mypostfragment=new MyPostListFragment();
        likepostfragment=new LikePostListFragment();
        //需要将userid传到碎片
        Bundle bundle = new Bundle();
        bundle.putString("userid",Userid);
        mypostfragment.setArguments(bundle);
        likepostfragment.setArguments(bundle);
        //-----------------------------------------
        fragmentList.add(mypostfragment);
        fragmentList.add(likepostfragment);
        viewPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        myViewPager.setAdapter(viewPagerAdapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面,即我的帖子
        pagerTableLayout=findViewById(R.id.personal_post_page_tablayout);
    }
    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("主页");
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });
        //是否显示关注按钮、如果显示则不是用户自己的主页
        if(false==Userid.equals(UserDataUtils.getUserid())){
            //访客模式:先标为为关注
            exeHandler(4);
            //发起请求检测是否已经关注:关注了则更改ui，以及关注的状态
            new Thread(()->{
                abouted= Http_checkAbouted.fetch(Userid);
                //更新刷新UI
                exeHandler(4);
            }).start();
            //为关注按钮绑定事件
            aboutButton.setOnClickListener(v->{
                if(abouted){//取消关注
                    new Thread(()->{
                        boolean result= Http_unabout.push(Userid);
                        if(result){
                            abouted=!abouted;
                            exeHandler(4);
                        }
                    }).start();
                }else{//关注此用户
                    new Thread(()->{
                        boolean result= Http_about.push(Userid);
                        if(result){
                            abouted=!abouted;
                            exeHandler(4);
                        }
                    }).start();
                }
            });
        }else{
            //用户自己访问自己的主页
            aboutButton.setVisibility(View.GONE);
            //为关注与粉丝绑定点击事件，以便可以跳转到相应页面查看内容
            View.OnClickListener fansClick=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(), UserListActivity.class);
                    intent.putExtra("MODEL","FANS");
                    startActivity(intent);
                }
            };
            View.OnClickListener aboutClick=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(), UserListActivity.class);
                    intent.putExtra("MODEL","ABOUT");
                    startActivity(intent);
                }
            };
            aboutnum.setOnClickListener(aboutClick);
            fansnum.setOnClickListener(fansClick);
            aboutText.setOnClickListener(aboutClick);
            fansText.setOnClickListener(fansClick);

        }
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
        //加载头像
        GlideUrl glideUrl= ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ Userid);
        //更新到视图
        Glide.with(getContext()).load(glideUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.headimg_loading)
                .into(headimg);
        //判断用户的内容是否可以从数据库中读取
        if(true==Userid.equals(UserDataUtils.getUserid())){
            userData=UserDataUtils.getAllUserData().get(0);
            if(userData!=null) {
                exeHandler(1);
                exeHandler(2);
                exeHandler(3);
                return;
            }
        }
        userData=new UserData();
        //否则将要网络请求获取
        //加载username
        new Thread(()->{
            String val=Http_getUserName.fetch(Userid);
            if(val!=null) {
                userData.setName(val);
                exeHandler(1);
            }
        }).start();
        //获取喜欢 关注 粉丝数量
        new Thread(()->{
            int[] backs=Http_getLikeAboutFans.fetch(Userid);
            userData.setLikeNum(Integer.toString(backs[0]));
            userData.setAboutNum(Integer.toString(backs[1]));
            userData.setFansNum(Integer.toString(backs[2]));
            //渲染like_about_fans
            exeHandler(2);
        }).start();
        //profile
        new Thread(()->{
            String val=Http_getUserProfile.fetch(Userid);
            if(val!=null) {
                userData.setProfile(val);
                exeHandler(3);
            }
        }).start();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_post_page);
        //从上下文获得需要显示的用户的id
        Userid=(String)getIntent().getStringExtra("userid");
        if(Userid==null){
            Post post=(Post)getIntent().getSerializableExtra("postdata");
            Userid=post.userid;
        }
        initElement();
        bindActionForElement();
        dataToView();
    }
}