package com.example.bigworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bigworks.fragment.homepage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnv_menu;//底部docker
    private Fragment home;//我的页面
    private Fragment about;//关注页面
    private Fragment find;//发现页面
    private void initElement(){
        bnv_menu = (BottomNavigationView) findViewById(R.id.bnv_menu);//底部docker
        find=new HomeFragment("发现");
        about=new HomeFragment("关注");
        home=new HomeFragment("我的");
    }
    private void bindBottomNavAction(){
        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.IndexContainer,home).commitNow();
                        return true;
                    case R.id.bottom_nav_find:
                        getSupportFragmentManager().beginTransaction().replace(R.id.IndexContainer,find).commitNow();
                        return true;
                    case R.id.bottom_nav_about:
                        getSupportFragmentManager().beginTransaction().replace(R.id.IndexContainer,about).commitNow();
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElement();//获得控件实例
        bindBottomNavAction();//绑定底部docker选项点击后的事件
    }
}