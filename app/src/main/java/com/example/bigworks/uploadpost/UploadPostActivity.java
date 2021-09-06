package com.example.bigworks.uploadpost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bigworks.R;

public class UploadPostActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_combar_back);
        back.setOnClickListener(v->{
            finish();
        });
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
        titlebar_title.setText("发帖");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);
        initElement();
    }
}