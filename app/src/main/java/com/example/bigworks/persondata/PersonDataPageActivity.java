package com.example.bigworks.persondata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigworks.R;

public class PersonDataPageActivity extends AppCompatActivity {
    private TextView back;
    private View view;
    private void initElement(){
        //返回按钮back=(TextView)
        TextView name = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.include_text).findViewById(R.id.rectangles_style_text_name);
        name.setText("test");
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
    }
    private void binActionForElement(){
        back.setClickable(true);//设置为textview可点击的
        //绑定字体，作为返回上级按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
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
}
