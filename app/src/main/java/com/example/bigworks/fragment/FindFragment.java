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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment {
    private View mview;
    private RecyclerView postlist;
    private List<Post> postlistData=new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    private int page;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mview==null) {
            mview= inflater.inflate(R.layout.fragment_find, container, false);
            initElement(mview);
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
        String content="find 你好！加油！ 吧hi奥🤦‍♀️";
        for(int i=0;i<10;i++){
            Post post=new Post();
            post.headimg=img;
            post.content=content+Integer.toString(i);
            postlistData.add(post);
        }
    }
    //获得view节点
    private void initElement(View view) {
        postlist=view.findViewById(R.id.find_recyclerview);
        refreshLayout=view.findViewById(R.id.find_refreshLayout);
        bindEvent();
    }

    private void bindEvent() {
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

}