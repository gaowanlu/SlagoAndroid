package site.linkway.slago.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import site.linkway.slago.R;
import site.linkway.slago.http.Post.Http_getFindPosts;
import site.linkway.slago.http.Post.Http_getPostData;
import site.linkway.slago.json.getPostData;
import site.linkway.slago.recyclerView.Adapter.Post;
import site.linkway.slago.recyclerView.Adapter.PostAdapter;
import site.linkway.slago.search.SearchActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment {
    private View mview;
    private RecyclerView postlist;
    private PostAdapter postAdapter;
    ImageView frafment_find_searchicon;
    private List<Post> postlistData=new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    private RefreshLayout refreshlayouttop;//顶部
    private RefreshLayout refreshlayoutbootom;//底部加载更多
    Handler HANDLER=new Handler((Message msg) -> {
        switch (msg.what){
            case 1:
                postAdapter.notifyDataSetChanged();
                if(refreshlayouttop!=null)
                    refreshlayouttop.finishRefresh(0);//停止刷新动画
                if(refreshlayoutbootom!=null)
                    refreshlayoutbootom.finishLoadMore(0);//停止刷新动画
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
        postlist=view.findViewById(R.id.find_recyclerview);
        refreshLayout=view.findViewById(R.id.find_refreshLayout);
        frafment_find_searchicon=view.findViewById(R.id.frafment_find_searchicon);
        bindEvent();
    }

    private void reloadPost(boolean clear){
        new Thread(()->{
            //获取推荐postids
            List<Post> tempPosts=new ArrayList<>();
            List<String> postids= Http_getFindPosts.fetch();
            for(int i=0;i<postids.size();i++){
                String postid=postids.get(i);
                Log.e("postid",postid);
                if(checkExist(postid)==false) {
                    Post post=loadingPostData(postid);
                    tempPosts.add(post);
                }
            }
            if(clear) {
                postlistData.clear();
            }
            for(Post post:tempPosts){
                postlistData.add(post);
            }
            tempPosts.clear();
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
            reloadPost(true);
        });

        //SmartRefreshLayout控件的加载
        refreshLayout.setOnLoadMoreListener((RefreshLayout refreshlayout) ->{
            this.refreshlayoutbootom=refreshlayout;
            //加载数据并处理重复项
            reloadPost(false);
        });

        //跳转到搜索
        frafment_find_searchicon.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), SearchActivity.class);
            getContext().startActivity(intent);
        });
    }

}