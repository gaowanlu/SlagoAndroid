package com.example.bigworks.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigworks.R;
import com.example.bigworks.SlagoDB.UserData;
import com.example.bigworks.utils.UserDataUtils;

public class ForgetPasswordActivity extends AppCompatActivity {

    private View back;
    private TextView titlebar_title;
    private EditText userEmail;
    private Button verifyButton;

    private void initElement() {
        //返回按钮back=(TextView)
        back = findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title = findViewById(R.id.titlebar_title);
        userEmail = (EditText) findViewById(R.id.verify_user_email);
        verifyButton = (Button) findViewById(R.id.verify_button);
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

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForgetPasswordActivity.this, userEmail.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initElement();
        bindActionForElement();
    }
}