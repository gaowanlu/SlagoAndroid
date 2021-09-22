package com.example.slago.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slago.R;
import com.example.slago.http.AccountSecurity.Http_checkUser;

public class RegisteredAccountActivity extends AppCompatActivity {

    private View back;
    private TextView titlebar_title;
    private EditText userEmail;
    private EditText userName;
    private EditText userPassword;
    private EditText againPassword;
    private Button registeredButton;

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
                Log.d("!!!", "" + Http_checkUser.get("name",userName.getText().toString()));
                if(Http_checkUser.get("name",userName.getText().toString())){
                    Toast.makeText(RegisteredAccountActivity.this, "昵称已被使用！", Toast.LENGTH_SHORT).show();
                }
//                //判断两次密码是否一致
//                if(userPassword.getText().toString().equals(againPassword.getText().toString())){
//                    Toast.makeText(RegisteredAccountActivity.this, "密码确认成功！", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(RegisteredAccountActivity.this, "密码确认失败！", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_account);
        initElement();
        bindActionForElement();
    }
}