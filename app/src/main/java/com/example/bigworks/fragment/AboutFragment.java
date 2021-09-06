package com.example.bigworks.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bigworks.R;
import com.example.bigworks.recyclerView.Adapter.Post;
import com.example.bigworks.recyclerView.Adapter.PostAdapter;
import com.example.bigworks.uploadpost.UploadPostActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private View mview;
    private SmartRefreshLayout refreshLayout;
    private ImageView uploadpostbutton;
    private RecyclerView postlist;
    private List<Post> postlistData=new ArrayList<>();
    private int page;

    private void binActionForElement(View view){
        uploadpostbutton=view.findViewById(R.id.fragment_about_upload);
        uploadpostbutton.setOnClickListener(v->{
            /*跳转到发帖子页面*/
            Intent intent= new Intent(getContext(), UploadPostActivity.class);
            startActivity(intent);
        });
        refreshLayout.setOnRefreshListener((RefreshLayout refreshlayout)-> {
            page = 1;
            //重新加载数据
            //initList();
            //3秒以后关闭刷新的视图
            refreshlayout.finishRefresh(1000);
        });

        //SmartRefreshLayout控件的加载
        refreshLayout.setOnLoadMoreListener((RefreshLayout refreshlayout) ->{
            page++;
            //重新加载数据
            //initList();
            //3秒以后关闭加载的视图
            refreshlayout.finishLoadMore(1000);
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int flag=0;
        if(mview==null) {
            mview = inflater.inflate(R.layout.fragment_about, container, false);
            initElement(mview);
            //为内部组件绑定事件
            binActionForElement(mview);
            initList();
        }
        return mview;
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            post.content=content+Integer.toString(i);
            postlistData.add(post);
        }
    }

    //获得view节点
    private void initElement(View view) {
        postlist=view.findViewById(R.id.about_recyclerview);
        refreshLayout=view.findViewById(R.id.about_refreshLayout);
    }

}