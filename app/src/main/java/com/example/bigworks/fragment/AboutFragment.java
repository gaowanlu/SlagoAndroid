package com.example.bigworks.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bigworks.R;
import com.example.bigworks.postpage.personalPostPage;
import com.example.bigworks.uploadpost.UploadPostActivity;

public class AboutFragment extends Fragment {
    public AboutFragment() {

    }
    private ImageView uploadpostbutton;
    private void binActionForElement(){
        Activity mainActivity=getActivity();
        uploadpostbutton=mainActivity.findViewById(R.id.fragment_about_upload);
        uploadpostbutton.setOnClickListener(v->{
            /*跳转到发帖子页面*/
            Intent intent= new Intent(getContext(), UploadPostActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //为内部组件绑定事件
        binActionForElement();
    }

}