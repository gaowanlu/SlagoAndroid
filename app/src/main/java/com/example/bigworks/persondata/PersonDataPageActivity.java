package com.example.bigworks.persondata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigworks.R;

public class PersonDataPageActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private void initElement(){
        //返回按钮back=(View)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        titlebar_title=findViewById(R.id.titlebar_title);
        TextView name = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_public_text);
        TextView myName = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_personal_text);
        name.setText("昵称");
        myName.setText("小菜鸟");

        TextView number = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_number).findViewById(R.id.rectangles_style_public_text);
        TextView myNumber = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_number).findViewById(R.id.rectangles_style_personal_text);
        number.setText("图享号");
        myNumber.setText("1901420313");

        TextView sex = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_sex).findViewById(R.id.rectangles_style_public_text);
        TextView mySex = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_sex).findViewById(R.id.rectangles_style_personal_text);
        number.setText("性别");
        mySex.setText("男");

        TextView signature = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_signature).findViewById(R.id.rectangles_style_public_text);
        TextView mySignature = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_signature).findViewById(R.id.rectangles_style_personal_text);
        number.setText("个性签名");
        mySignature.setText("Slago Inc");
    }
    private void binActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("个人信息");
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
        setContentView(R.layout.activity_person_datapage);
        initElement();
        binActionForElement();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
