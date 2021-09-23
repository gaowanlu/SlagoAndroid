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

import com.example.slago.R;
import com.example.slago.http.AccountSecurity.Http_changePwd;
import com.example.slago.http.AccountSecurity.Http_sendVerificationCode;

import java.util.Hashtable;

public class ChangePasswordActivity extends AppCompatActivity {

    private String userEmail;
    private View back;
    private TextView titlebar_title;
    private TextView changUserEmail;
    private EditText newPassword;
    private EditText againNewPassword;
    private Button emailVerifyButton;

    private void initElement() {
        //返回按钮back=(TextView)
        back = findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title = findViewById(R.id.titlebar_title);
        changUserEmail = (TextView) findViewById(R.id.chang_user_email);
        newPassword = (EditText) findViewById(R.id.new_password);
        againNewPassword = (EditText) findViewById(R.id.again_new_password);
        emailVerifyButton = (Button) findViewById(R.id.email_verify_button);
    }

    private void bindActionForElement(){
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");
        //设置标题栏文字
        titlebar_title.setText("找回密码");
        //设置上一步输入的电子邮箱
        changUserEmail.setText(userEmail);
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });

        emailVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
                //changePassword();
            }
        });
    }

    private void sendEmail(){
        new Thread(){
            @Override
            public void run() {
                Hashtable<String,Object> sendVerificationCode = Http_sendVerificationCode.push(userEmail);
                boolean result2=(Boolean) sendVerificationCode.get("result");//是否发送成功
            }
        }.start();
    }


    private void changePassword(){
        String newPwd = newPassword.getText().toString();
        String againPwd = againNewPassword.getText().toString();
        Toast.makeText(ChangePasswordActivity.this, "1: " + newPwd + " 2: " + againPwd, Toast.LENGTH_SHORT).show();
        Hashtable<String,Object> changePwd= Http_changePwd.push(userEmail,newPwd,againPwd);
        boolean result=(Boolean) changePwd.get("result");
        String info=(String) changePwd.get("info");//获得失败情况
        System.out.println(result+" "+info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initElement();
        bindActionForElement();
    }
}