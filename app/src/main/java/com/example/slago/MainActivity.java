package com.example.slago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.example.slago.activityCollector.BaseActivity;
import com.example.slago.fragment.AboutFragment;
import com.example.slago.fragment.FindFragment;
import com.example.slago.fragment.HomeFragment;
import com.example.slago.http.APIData;
import com.example.slago.http.Utils;
import com.example.slago.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private BottomNavigationView bnv_menu;//底部docker
    private Fragment home;//我的页面
    private Fragment about;//关注页面
    private Fragment find;//发现页面
    //退出登录句柄
    Handler LOGOUTHANDLER=new Handler((Message msg) -> {
        Intent intent=new Intent(MainActivity.this, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return true;
    });
    //APIS
    private String AuthenticationURL(){
        return APIData.URL_MIP +"SlagoService_Authentication";
    }
    //身份验证，没有身份信息或者令牌不对则，退出登录
    private void Authentication(){
        new Thread(()->{
            OkHttpClient client=new OkHttpClient();
            Request request= Utils.SessionRequest(AuthenticationURL());
            try {
                Log.e("REQUEST",AuthenticationURL());
                Response response=client.newCall(request).execute();
                String responseData=response.body().string();
                //身份验证失败
                if(!responseData.equals("{\"status\":\"200\",\"result\":\"true\"}")){
                    //退出登录
                    LOGOUTHANDLER.sendMessage(new Message());
                }
            } catch (IOException e) { }
        }).start();
    }
    private void initElement(){
        bnv_menu = (BottomNavigationView) findViewById(R.id.bnv_menu);//底部docker
        //生成碎片
        find=new FindFragment();
        about=new AboutFragment();
        home=new HomeFragment();
    }
    private void bindBottomNavAction(){
        //底部docker栏选择事件
        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                toPage(item.getItemId());
                return true;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Authentication();
        initElement();//获得控件实例
        bindBottomNavAction();//绑定底部docker选项点击后的事件
        //初始化视图:到发现推荐页面
        bnv_menu.setSelectedItemId(R.id.bottom_nav_find);
    }

    private void toPage(int id) {
        switch (id){
            case R.id.bottom_nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.IndexContainer,home).commitNow();return;
            case R.id.bottom_nav_find:
                getSupportFragmentManager().beginTransaction().replace(R.id.IndexContainer,find).commitNow();return;
            case R.id.bottom_nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.IndexContainer,about).commitNow();return;
            default:getSupportFragmentManager().beginTransaction().replace(R.id.IndexContainer,find).commitNow();
        }
    }
}