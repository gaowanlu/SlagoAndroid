package com.example.slago.persondata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.slago.R;
import com.example.slago.SlagoDB.UserData;
import com.example.slago.activityCollector.BaseActivity;
import com.example.slago.http.UserData.Http_setUserSex;
import com.example.slago.utils.UserDataUtils;

import java.util.ArrayList;
import java.util.List;

public class MySexActivity extends BaseActivity {
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
        UserData userData = UserDataUtils.getAllUserData().get(0);//获取用户信息
        List<String> setSex = new ArrayList<String>();
        String[] sexArry = new String[]{"男", "女", "保密"};

        for(int i = 0; i < 3; i++){
            if(sexArry[i].equals(userData.getSex())){
                String temp = sexArry[1];
                sexArry[i] = temp;
                sexArry[1] = userData.getSex();
                break;
            }
        }

        for(int i = 0; i < 3; i++){
            setSex.add(sexArry[i]);
        }
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