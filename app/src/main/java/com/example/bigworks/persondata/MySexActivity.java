package com.example.bigworks.persondata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bigworks.R;
import com.example.bigworks.http.UserData.Http_setUserName;
import com.example.bigworks.http.UserData.Http_setUserSex;

import java.util.ArrayList;
import java.util.List;

public class MySexActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private PickerView setMySex;
    private Button sexButton;
    private String setSex;

    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
    }

    //初始化滑动选择性别
    private void initScrollSelector() {
        setMySex = (PickerView) findViewById(R.id.set_my_sex);
        List<String> setSex = new ArrayList<String>();
        setSex.add("女");
        setSex.add("男");
        setSex.add("保密");
        setMySex.setData(setSex);
        setMySex.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                String editSex = text;
                new Thread(()->{
                    Http_setUserSex.push(editSex);
                }).start();
            }
        });
    }

    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("性别");
        back.setClickable(true);//设置为textview可点击的

        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });
    }

    private void saveButton(){
        sexButton = (Button) findViewById(R.id.bt_sex);
        sexButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sex);
        initElement();
        initScrollSelector();
        bindActionForElement();
        saveButton();
    }
}