package com.example.bigworks.postpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bigworks.R;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.ImageLoad;
import com.example.bigworks.http.Post.Http_likePost;
import com.example.bigworks.http.Post.Http_unlikePost;
import com.example.bigworks.recyclerView.Adapter.ImageAdapter;
import com.example.bigworks.recyclerView.Adapter.Post;
import com.example.bigworks.recyclerView.Adapter.PostAdapter;

public class PostActivity extends AppCompatActivity {
    private View back;
    private Post postdata;
    private RecyclerView imglist;
    private ImageAdapter imageAdapter;
    private ImageView heartButton;
    private ImageView headimg;
    Handler HANDLER=new Handler((Message msg) -> {
        switch (msg.what){
            case 1:

                break;
            default:;
        }
        return true;
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postdata=(Post)getIntent().getSerializableExtra("postdata");
        initElement();
        bindEvent();
        dataToView();
    }

    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_postbar).findViewById(R.id.titlebar_post_back);
        headimg=findViewById(R.id.titlebar_postbar).findViewById(R.id.titlebar_post_headimg);
        heartButton=findViewById(R.id.titlebar_postbar).findViewById(R.id.titlebar_post_heart);
        imglist=findViewById(R.id.post_recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        imglist.setLayoutManager(layoutManager);
        imageAdapter=new ImageAdapter(postdata.imgs);
        imglist.setAdapter(imageAdapter);
    }

    private void bindEvent(){
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });
        //heart喜欢事件
        heartButton.setOnClickListener(v->{
            postdata.liked=!postdata.liked;
            checkHeart();
        });
        //头像点击事件
        headimg.setOnClickListener(v->{
            Intent intent= new Intent(PostActivity.this, VisitorActivity.class);
            intent.putExtra("postdata",postdata);
            startActivity(intent);
        });
    }

    private void checkHeart(){
        if(postdata.liked) {
            heartButton.setImageResource(R.drawable.heart_fill_red);
            new Thread(()->{
                Http_likePost.push(postdata.postid);
            }).start();
        }else {
            heartButton.setImageResource(R.drawable.heart_fill_black);
            new Thread(()->{
                Http_unlikePost.push(postdata.postid);
            }).start();
        }
    }

    private void dataToView(){
        //加载头像
        //圆角
        RoundedCorners roundedCorners= new RoundedCorners(6);
        //通过RequestOptions扩展功能,override采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        //加载头像
        GlideUrl glideUrl= ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ postdata.userid);
        //更新到视图
        //                .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(glideUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.headimg_loading)
                .into(headimg);
        if(postdata.liked){
            heartButton.setImageResource(R.drawable.heart_fill_red);
        }
    }
}