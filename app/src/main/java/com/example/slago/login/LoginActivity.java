package com.example.slago.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slago.MainActivity;
import com.example.slago.R;
import com.example.slago.SlagoDB.UserData;
import com.example.slago.http.APIData;
import com.example.slago.json.SlagoService_Login;
import com.google.gson.Gson;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText inputid;
    private EditText inputpwd;
    private TextView forgetPwd;
    private TextView registerAccount;
    //Hander
    private Handler handler;
    //初始化handler
    @SuppressLint("HandlerLeak")
    private void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }
    //APIS
    private String loginURL(){
        return APIData.URL_MIP + "SlagoService_Login";
    }

    //获得组件
    private void initElement(){
        loginButton=(Button)findViewById(R.id.login_button);
        inputid=(EditText)findViewById(R.id.login_inputid);
        inputpwd=(EditText)findViewById(R.id.login_inputpwd);
        forgetPwd=(TextView)findViewById(R.id.forget_password);
        registerAccount=(TextView)findViewById(R.id.registered_account);
    }
    //检查输入
    private boolean InputCheck(String id,String pwd){
        if(id==null||pwd==null){
            Toast.makeText(LoginActivity.this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (id.equals("") || pwd.equals("")) {
            Toast.makeText(LoginActivity.this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //为组件绑定事件
    private void bindEvent(){
        loginButton.setOnClickListener(v -> {
            String id=inputid.getText().toString();
            String pwd=inputpwd.getText().toString();
            //校验输入
            if(!InputCheck(id,pwd)){return;}
            //发起网络请求
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody=new FormBody.Builder()
                    .add("id",id)
                    .add("password",pwd).build();
            Request request=new Request.Builder()
                    .url(loginURL())
                    .post(requestBody).build();
            new Thread(()->{
                try {
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.e("BODY",responseData);
                    Gson gson=new Gson();
                    SlagoService_Login slagoService_login=gson.fromJson(responseData,SlagoService_Login.class);
                    if(slagoService_login.status.equals("200")&&slagoService_login.result.equals("true")){
                        //获取cookie SlagoSession
                        Headers headers=response.headers();
                        List<String> cookies = headers.values("Set-Cookie");
                        String session = cookies.get(1);
                        String sessionID = session.substring(session.indexOf("=")+1, session.indexOf(";"));
                        Log.e("SlagoSession",sessionID);
                        //更新数据库
                        UserData userData=new UserData();
                        userData.setUserid(id);
                        userData.setSlagoSession(sessionID);
                        userData.save();
                        //跳转到MainActivity
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);
                    }else{
                        //Toast提示:账号或密码错误
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) { }
            }).start();
        });

        //找回密码
        forgetPwd.setClickable(true);
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"忘记密码了！",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        //注册账号
        registerAccount.setClickable(true);
        registerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"我想要个账号！",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(LoginActivity.this, RegisteredAccountActivity.class);
                startActivity(intent);
            }
        });
    }
    //数据上视图
    private void initView(){
        //查看UserData表中是否有账号，有账号则取一个
        List<UserData> users=DataSupport.findAll(UserData.class);
        for( UserData user:users){
            inputid.setText(user.getUserid());
            break;
        }
        //清除旧的UserData
        Integer changlines=DataSupport.deleteAll(UserData.class);
        Log.e("changeLines",changlines.toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LitePal.getDatabase();
        initElement();
        initHandler();
        bindEvent();
        //初始化内容
        initView();
    }
}