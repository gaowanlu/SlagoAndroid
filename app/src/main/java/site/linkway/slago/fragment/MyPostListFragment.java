package site.linkway.slago.fragment;


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

import site.linkway.slago.R;
import site.linkway.slago.http.Post.Http_getPostData;
import site.linkway.slago.http.Post.Http_getUserAllPost;
import site.linkway.slago.json.getPostData;
import site.linkway.slago.json.getUserPosts;
import site.linkway.slago.recyclerView.Adapter.MyPostAdapter;
import site.linkway.slago.recyclerView.Adapter.Post;
import site.linkway.slago.utils.UserDataUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MyPostListFragment extends Fragment {
    private View mview;
    private RecyclerView postlist;
    private MyPostAdapter postAdapter;
    private List<Post> postlistData=new ArrayList<>();//适配器每个item的数据
    private SmartRefreshLayout refreshLayout;
    private RefreshLayout refreshlayouttop;//顶部
    private RefreshLayout refreshlayoutbootom;//底部加载更多
    private String userid;

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
            mview= LayoutInflater.from(getContext()).inflate(R.layout.fragment_mypostlist,container,false);
            //读取userid
            Bundle bundle = getArguments();
            if(bundle!=null){
                userid = bundle.getString("userid");
            }else{
                userid="";
            }
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
        postAdapter=new MyPostAdapter(postlistData);
        postlist.setAdapter(postAdapter);
        //初始化列表数据
        refreshLayout.autoRefresh();
    }

    //获得view节点
    private void initElement(View view) {
        postlist=view.findViewById(R.id.find_recyclerview);
        refreshLayout=view.findViewById(R.id.find_refreshLayout);
        bindEvent();
    }

    private void loadPost(boolean clear){
        new Thread(()->{
            //获取推荐postids
            List<Post> tempPosts=new ArrayList<>();
            getUserPosts fetchUserPosts= Http_getUserAllPost.fetch(nowPage,PAGESIZE, userid);
            if(fetchUserPosts!=null&&fetchUserPosts.list!=null){//获取数据成功
                nowPage++;//页数递增
                //打印获取数据测试
                for(int i=0;i<fetchUserPosts.list.size();i++){
                    System.out.println("Fetch User Posts  "+fetchUserPosts.list.get(i));
                    //获取帖子数据
                    if(checkExist(fetchUserPosts.list.get(i))==false){//列表中不存在,则重新fetch此帖子信息
                        Post post=loadingPostData(fetchUserPosts.list.get(i));
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
