package com.example.bigworks.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bigworks.R;
import com.example.bigworks.postpage.personalPostPage;
import com.example.bigworks.recyclerView.Adapter.Post;
import com.example.bigworks.recyclerView.Adapter.PostAdapter;
import com.example.bigworks.uploadpost.UploadPostActivity;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private ImageView uploadpostbutton;
    private RecyclerView postlist;
    private List<Post> postlistData=new ArrayList<>();

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
        initElement();
        //为内部组件绑定事件
        binActionForElement();
        initList();
    }

    //初始化recylerview
    private void initList() {
        //初始化列表数据
        initListData();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        postlist.setLayoutManager(layoutManager);
        PostAdapter postAdapter=new PostAdapter(postlistData);
        postlist.setAdapter(postAdapter);

    }

    //获取list数据
    private void initListData() {
        int img=R.drawable.tempheadimg;
        String content="about 你好！加油！ 吧hi奥🤦‍♀️";
        for(int i=0;i<10;i++){
            Post post=new Post();
            post.headimg=img;
            post.content=content;
            postlistData.add(post);
        }
    }

    //获得view节点
    private void initElement() {
        postlist=getActivity().findViewById(R.id.about_recyclerview);
    }

}