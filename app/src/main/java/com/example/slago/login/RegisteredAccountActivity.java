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
import com.example.slago.http.AccountSecurity.Http_checkUser;
import com.example.slago.http.AccountSecurity.Http_registerNewCount;
import com.example.slago.http.AccountSecurity.Http_sendVerificationCode;

import java.util.Hashtable;

public class RegisteredAccountActivity extends AppCompatActivity {

    private View back;
    private TextView titlebar_title;
    private EditText userEmail;
    private EditText userName;
    private EditText userPassword;
    private EditText againPassword;
    private Button registeredButton;
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
                        break;
                    case 2:
                        Toast.makeText(RegisteredAccountActivity.this,"昵称或电子邮箱已经被存在！",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private void initElement() {
        //返回按钮back=(TextView)
        back = findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title = findViewById(R.id.titlebar_title);
        userEmail = (EditText) findViewById(R.id.import_user_email);
        userName = (EditText) findViewById(R.id.import_user_name);
        userPassword = (EditText) findViewById(R.id.import_user_password);
        againPassword = (EditText) findViewById(R.id.again_user_password);
        registeredButton = (Button) findViewById(R.id.registered_button);
    }

    private void judgeNameOccupied(){
        new Thread(){
            @Override
            public void run() {
                if(Http_checkUser.get("name",userName.getText().toString())){
                    System.out.println(Http_checkUser.get("name",userName.getText().toString()));
                }
            }
        }.start();
    }

    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setVisibility(View.INVISIBLE);
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });

        registeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        if(!Http_checkUser.get("name",userName.getText().toString()) && !Http_checkUser.get("email", userEmail.getText().toString())){
                            System.out.println(Http_checkUser.get("name",userName.getText().toString()));
                            //判断两次密码是否一致
                            if(userPassword.getText().toString().equals(againPassword.getText().toString())){
                                Toast.makeText(RegisteredAccountActivity.this, "密码确认成功！", Toast.LENGTH_SHORT).show();
                                Hashtable<String,Object> registerNewCount= Http_registerNewCount.push
                                        (userEmail.getText().toString(),userPassword.getText().toString(),"erfg",userName.getText().toString());
                                boolean result1=(Boolean) registerNewCount.get("result");//是否成功
                                String info1=(String) registerNewCount.get("info");//错误信息
                                System.out.println(result1+" "+info1);
                            }
                            else{
                                Toast.makeText(RegisteredAccountActivity.this, "密码确认失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            //Toast提示:昵称或电子邮箱已经被存在
                            Message message=new Message();
                            message.what=2;
                            handler.sendMessage(message);
                        }
                    }
                }.start();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_account);
        initElement();
        bindActionForElement();
        initHandler();
    }
}