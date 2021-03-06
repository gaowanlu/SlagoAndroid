package site.linkway.slago;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;

import site.linkway.slago.R;

import site.linkway.slago.activityCollector.BaseActivity;
import site.linkway.slago.fragment.AboutFragment;
import site.linkway.slago.fragment.FindFragment;
import site.linkway.slago.fragment.HomeFragment;
import site.linkway.slago.http.AccountSecurity.Http_authentication;
import site.linkway.slago.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    //身份验证，没有身份信息或者令牌不对则，退出登录
    private void Authentication(){
        new Thread(()->{
            if(false== Http_authentication.fetch()){
                Message message=new Message();
                message.what=0;
                LOGOUTHANDLER.sendMessage(message);
            }
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