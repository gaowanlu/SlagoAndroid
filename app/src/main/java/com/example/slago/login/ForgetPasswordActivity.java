package com.example.slago.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.example.slago.SlagoDB.UserData;
import com.example.slago.http.AccountSecurity.Http_checkUser;
import com.example.slago.http.AccountSecurity.Http_registerNewCount;
import com.example.slago.http.UserData.Http_setHeadImg;
import com.example.slago.persondata.MySexActivity;
import com.example.slago.persondata.PersonDataPageActivity;
import com.example.slago.utils.UserDataUtils;

import java.io.File;
import java.util.Hashtable;

import es.dmoral.toasty.Toasty;

public class ForgetPasswordActivity extends AppCompatActivity {

    private View back;
    private TextView titlebar_title;
    private EditText userEmail;
    private Button verifyButton;
    private ProgressDialog progressDialog;
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
                        Toasty.error(ForgetPasswordActivity.this,"不存在这个邮箱！",Toasty.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        };
    }

    //使用dialog 防止重复请求
    private void nextStepDialog(String email, ProgressDialog progressDialog){
        new Thread(()->{
            try {
                boolean result = Http_checkUser.get("email", email);
                Log.e("RESULT", Boolean.toString(result));
                int sleepTime = 2000;
                if (result) {
                    //progressDialog.setMessage("下一步");
                    Intent intent = new Intent(ForgetPasswordActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("userEmail", userEmail.getText().toString());
                    startActivity(intent);
                    sleepTime = 500;
                } else {
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    //progressDialog.setMessage("不存在这个邮箱");
                }
                try {
                    Thread.sleep(sleepTime);//延时
                } catch (Exception e) {
                }
            }catch (Exception e){}finally {
                progressDialog.dismiss();//去掉dialog
            }
        }).start();
    }

    private void initElement() {
        //返回按钮back=(TextView)
        back = findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title = findViewById(R.id.titlebar_title);
        userEmail = (EditText) findViewById(R.id.verify_user_email);
        verifyButton = (Button) findViewById(R.id.verify_button);
    }

    private void bindActionForElement() {
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

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkEmail = userEmail.getText().toString();
                InputCheck(checkEmail);
                progressDialog=new ProgressDialog(ForgetPasswordActivity.this);
                progressDialog.setTitle("请求");
                progressDialog.setMessage("查找中...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //确实保存在该电子邮箱
                nextStepDialog(checkEmail, progressDialog);
            }
        });
    }

    //检查输入
    private boolean InputCheck(String email){
        if(email==null){
            Toasty.error(ForgetPasswordActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.equals("")) {
            Toasty.error(ForgetPasswordActivity.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initElement();
        bindActionForElement();
        initHandler();
    }
}