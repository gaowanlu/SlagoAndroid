package com.example.bigworks.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bigworks.R;

import com.example.bigworks.SlagoDB.UserData;
import com.example.bigworks.http.Post.Http_getFindPosts;
import com.example.bigworks.http.Post.Http_getPostData;
import com.example.bigworks.json.getPostData;
import com.example.bigworks.recyclerView.Adapter.Post;
import com.example.bigworks.recyclerView.Adapter.PostAdapter;
import com.example.bigworks.uploadpost.UploadPostActivity;
import com.example.bigworks.utils.UserDataUtils;
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
    private PostAdapter postAdapter;
    private RefreshLayout refreshlayout;

    Handler HANDLER=new Handler((Message msg) -> {
        UserData userData= UserDataUtils.getAllUserData().get(0);//获取用户信息
        if(null==userData){ return false;}
        switch (msg.what){
            case 1:
                postAdapter.notifyDataSetChanged();
                if(refreshlayout!=null)
                    refreshlayout.finishRefresh(0);
                break;
            default:;
        }
        return true;
    });

    private void binActionForElement(View view){
        uploadpostbutton=view.findViewById(R.id.fragment_about_upload);
        //发帖子按钮点击事件
        uploadpostbutton.setOnClickListener(v->{
            Intent intent= new Intent(getActivity(), UploadPostActivity.class);
            startActivity(intent);
        });
        refreshLayout.setOnRefreshListener((RefreshLayout refreshlayout)-> {
            //重新加载数据
            //initList();
            //3秒以后关闭刷新的视图
            this.refreshlayout=refreshlayout;
            reloadPost();
        });

        //SmartRefreshLayout控件的加载
        refreshLayout.setOnLoadMoreListener((RefreshLayout refreshlayout) ->{
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
            //mGlide= Glide.with(getContext());
        }
        return mview;
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //初始化recylerview
    private void initList() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        postlist.setLayoutManager(layoutManager);
        postAdapter=new PostAdapter(postlistData);
        postlist.setAdapter(postAdapter);
        //初始化列表数据
        reloadPost();
    }

    private void reloadPost(){
        new Thread(()->{
            //获取推荐postids
            List<String> postids= Http_getFindPosts.fetch();
            postlistData.clear();
            for(int i=0;i<postids.size();i++){
                String postid=postids.get(i);
                new Thread(()->{
                    Log.e("postid",postid);
                    loadingPostData(postid);
                }).start();
            }
        }).start();
    }

    private void loadingPostData(String postid){
        getPostData data= Http_getPostData.fetch(postid);
        Post post=new Post();
        post.content=data.posttext;
        post.headimg=R.drawable.tempheadimg;
        post.collectioned=data.collectioned;
        post.liked=data.liked;
        post.collectionNum=data.collectionNum;
        post.likeNum=data.likeNum;
        post.userid=data.userid;
        post.imgs=data.imgs;
        post.postdate=data.postdate;
        post.commentNum=data.commentNum;
        post.postid=postid;
        postlistData.add(post);
        Message message=new Message();
        message.what=1;
        HANDLER.sendMessage(message);
    }


    //获得view节点
    private void initElement(View view) {
        postlist=view.findViewById(R.id.about_recyclerview);
        refreshLayout=view.findViewById(R.id.about_refreshLayout);
    }

}

