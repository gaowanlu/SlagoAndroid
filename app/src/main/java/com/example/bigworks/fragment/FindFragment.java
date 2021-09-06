package com.example.bigworks.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigworks.R;
import com.example.bigworks.recyclerView.Adapter.Post;
import com.example.bigworks.recyclerView.Adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment {
    private RecyclerView postlist;
    private List<Post> postlistData=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initElement();
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
        String content="find 你好！加油！ 吧hi奥🤦‍♀️";
        for(int i=0;i<10;i++){
            Post post=new Post();
            post.headimg=img;
            post.content=content;
            postlistData.add(post);
        }
    }
    //获得view节点
    private void initElement() {
        postlist=getActivity().findViewById(R.id.find_recyclerview);
    }

}