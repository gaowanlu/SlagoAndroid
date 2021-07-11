package com.example.bigworks.persondata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigworks.R;

public class PersonDataPageActivity extends AppCompatActivity {
    private TextView back;
    private void initElement(){
        //返回按钮back=(TextView)
        findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
    }
    private void binActionForElement(){
//        back.setClickable(true);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    finish();
//            }
//        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_datapage);
        initElement();
        binActionForElement();
    }
}
