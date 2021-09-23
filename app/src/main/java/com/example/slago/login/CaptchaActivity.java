package com.example.slago.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.slago.R;
import com.example.slago.http.AccountSecurity.Http_sendVerificationCode;
import com.luozm.captcha.Captcha;

import java.util.Hashtable;

public class CaptchaActivity extends AppCompatActivity {
    private Captcha captcha;

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
//                        Intent intent = new Intent(CaptchaActivity.this, RegisteredAccountActivity.class);
//                        startActivity(intent);
                        break;
                    case 2:
                        //Toast.makeText(RegisteredAccountActivity.this,"昵称或电子邮箱已经被存在！",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        captcha = (Captcha) findViewById(R.id.captCha);
        //captcha.setBitmap(url);
        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                Toast.makeText(CaptchaActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CaptchaActivity.this, RegisteredAccountActivity.class);
                setResult(1, intent);
                finish();
                return "验证通过,耗时"+time+"毫秒";
            }

            @Override
            public String onFailed(int failedCount) {
                Toast.makeText(CaptchaActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String userEmail = intent.getStringExtra("nextActivity");
                new Thread(){
                    @Override
                    public void run() {
//                        Message message=new Message();
//                        message.what=1;
//                        handler.sendMessage(message);
//                        Hashtable<String,Object> sendVerificationCode = Http_sendVerificationCode.push(userEmail);
//                        boolean result2=(Boolean) sendVerificationCode.get("result");//是否发送成功
//                        Log.d("!!!", "" + result2);
                    }
                }.start();
                return "验证失败,已失败"+failedCount+"次";
            }

            @Override
            public String onMaxFailed() {
                Toast.makeText(CaptchaActivity.this,"验证超过次数，你的帐号被封锁",Toast.LENGTH_SHORT).show();
                return "验证失败,帐号已封锁";
            }
        });
    }
}