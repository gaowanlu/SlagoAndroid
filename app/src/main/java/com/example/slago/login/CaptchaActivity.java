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
import com.example.slago.activityCollector.BaseActivity;
import com.example.slago.http.AccountSecurity.Http_sendVerificationCode;
import com.luozm.captcha.Captcha;

import java.util.Hashtable;

public class CaptchaActivity extends BaseActivity {
    private Captcha captcha;
    private static final int success = 1;

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
                setResult(success);
                finish();
                return "验证通过,耗时"+time+"毫秒";
            }

            @Override
            public String onFailed(int failedCount) {
                Toast.makeText(CaptchaActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
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