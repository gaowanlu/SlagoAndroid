package com.example.slago.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.slago.R;
import com.example.slago.http.Like.Http_likelist;
import com.example.slago.http.Post.Http_getPostData;
import com.example.slago.http.Post.Http_getUserAllPost;
import com.example.slago.json.getPostData;
import com.example.slago.json.getUserPosts;
import com.example.slago.recyclerView.Adapter.MyPostAdapter;
import com.example.slago.recyclerView.Adapter.Post;
import com.example.slago.recyclerView.Adapter.PostAdapter;
import com.example.slago.utils.UserDataUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class LikePostListFragment extends Fragment {
    private View mview;
    private RecyclerView postlist;
    private PostAdapter postAdapter;
    private List<Post> postlistData=new ArrayList<>();//适配器每个item的数据
    private SmartRefreshLayout refreshLayout;
    private RefreshLayout refreshlayouttop;//顶部
    private RefreshLayout refreshlayoutbootom;//底部加载更多

    /*动态分页读取用户的帖子信息*/
    private int nowPage=0;
    private static final int PAGESIZE=10;

    Handler HANDLER=new Handler((Message msg) -> {
        switch (msg.what){
            case 1:
                postAdapter.notifyDataSetChanged();
                if(refreshlayouttop!=null)
                    refreshlayouttop.finishRefresh(0);
                if(refreshlayoutbootom!=null)
                    refreshlayoutbootom.finishLoadMore(0);
                break;
            default:;
        }
        return true;
    });


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mview==null) {
            mview= LayoutInflater.from(getContext()).inflate(R.layout.fragment_likepostlist,container,false);
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
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        StaggeredGridLayoutManager layoutManager1=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        postlist.setLayoutManager(layoutManager1);
        postAdapter=new PostAdapter(postlistData);
        postlist.setAdapter(postAdapter);
        //初始化列表数据
        refreshLayout.autoRefresh();
    }

    //获得view节点
    private void initElement(View view) {
        postlist=view.findViewById(R.id.likepostlist_recyclerview);
        refreshLayout=view.findViewById(R.id.likepostlist_refreshLayout);
        bindEvent();
    }

    private void loadPost(boolean clear){
        new Thread(()->{
            //获取推荐postids
            List<Post> tempPosts=new ArrayList<>();
            List<String> ids= Http_likelist.fetch(nowPage,PAGESIZE);
            if(ids!=null&&ids.size()>0){//获取数据成功
                nowPage++;//页数递增
                //打印获取数据测试
                for(int i=0;i<ids.size();i++){
                    System.out.println("Fetch User Like Posts  "+ids.get(i));
                    //获取帖子数据
                    if(checkExist(ids.get(i))==false){//列表中不存在,则重新fetch此帖子信息
                        Post post=loadingPostData(ids.get(i));
                        if(post!=null){
                            tempPosts.add(post);
                        }
                    }
                }
            }
            //一次性将tempPosts中的内容添加到列表中去
            for(Post post:tempPosts){
                postlistData.add(post);
            }
            Message message=new Message();
            message.what=1;
            HANDLER.sendMessage(message);
        }).start();
    }

    //检查帖子是否已经存在
    private boolean checkExist(String postid){
        if(postlistData==null||postlistData.size()==0){return false;}
        boolean flag=false;
        for(int i=0;i<postlistData.size();i++){
            if(postid.equals(postlistData.get(i).postid)){
                flag=true;
            }
        }
        return flag;
    }

    private Post loadingPostData(String postid){
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
        return  post;
    }

    private void addPost(){

    }

    private void bindEvent() {
        refreshLayout.setOnRefreshListener((RefreshLayout refreshlayout)-> {
            this.refreshlayouttop=refreshlayout;
            //重新加载数据
            loadPost(true);
        });

        //SmartRefreshLayout控件的加载
        refreshLayout.setOnLoadMoreListener((RefreshLayout refreshlayout) ->{
            this.refreshlayoutbootom=refreshlayout;
            //加载数据并处理重复项
            loadPost(false);
        });
    }
}
