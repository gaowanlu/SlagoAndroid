package com.example.slago.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.slago.R;
import com.luozm.captcha.Captcha;

public class CaptchaActivity extends AppCompatActivity {
    private Captcha captcha;
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