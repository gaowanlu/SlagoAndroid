package com.example.bigworks.persondata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigworks.R;


public class MyName extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);

        TextView name = (TextView) findViewById(R.id.rectangles_my_number).findViewById(R.id.rectangles_public_edit_text);
        TextView myName = (TextView) findViewById(R.id.rectangles_my_number).findViewById(R.id.rectangles_personal_edit_text);
        name.setText("昵称");
        myName.setText("小菜鸟");
    }
    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("昵称");
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_name);
        Log.d("is: ", "in");
        initElement();
        bindActionForElement();
    }
}