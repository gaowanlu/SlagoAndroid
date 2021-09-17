package com.example.slago.recyclerView.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.slago.R;
import com.example.slago.http.APIData;
import com.example.slago.http.ImageLoad;
import com.example.slago.postpage.PostActivity;
import com.example.slago.postpage.VisitorActivity;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> mPostList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        //声明view节点
        ImageView headimg;
        TextView username;
        ImageView showImg;
        TextView content;
        View itemView;
        public ViewHolder( View itemView) {
            super(itemView);
            this.itemView=itemView;
            //获取view节点
            headimg=itemView.findViewById(R.id.component_post_headimg);
            username=itemView.findViewById(R.id.component_post_userid);
            showImg=itemView.findViewById(R.id.component_post_showImg);
            content=itemView.findViewById(R.id.component_post_content);
        }
    }

    //constructor
    public PostAdapter(List<Post> postList){
        mPostList=postList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        //绑定layout
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_post,parent,false);
        ViewHolder holder=new ViewHolder(view);        //为头像与封面添加点击事件
        holder.headimg.setOnClickListener(v -> {
            Intent intent= new Intent(holder.itemView.getContext(), VisitorActivity.class);
            //获取点击的下标
            int position=holder.getAdapterPosition();
            //获取数据
            Post postdata=mPostList.get(position);
            intent.putExtra("postdata",postdata);
            holder.itemView.getContext().startActivity(intent);
        });
        holder.showImg.setOnClickListener(v->{
            Intent intent= new Intent(holder.itemView.getContext(), PostActivity.class);
            //获取点击的下标
            int position=holder.getAdapterPosition();
            //获取数据
            Post postdata=mPostList.get(position);
            intent.putExtra("postdata",postdata);
            holder.itemView.getContext().startActivity(intent);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Post post=mPostList.get(position);
        if(post==null||post.imgs==null){return;}
        //设置头像与用户名
        holder.headimg.setImageResource(post.headimg);
        holder.username.setText(post.userid);


        //显示文字缩略内容
        String content_r=post.content;
        if(post.content.length()>10) {
            content_r=post.content.substring(0,7)+"...";
        }
        holder.content.setText(content_r);


        //圆角
        RoundedCorners roundedCorners= new RoundedCorners(6);
        //通过RequestOptions扩展功能,override采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        //加载头像
        GlideUrl glideUrl= ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ post.userid);
        //更新到视图
        //                .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(holder.itemView.getContext()).load(glideUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.headimg_loading)
                .into(holder.headimg);
        //显示帖子第一张图片
        if(post.imgs.size()>0) {
            GlideUrl postimgUrl = ImageLoad.getGlideURL(APIData.URL_MIPR + "getPostImg" + "?id=" + post.imgs.get(0));
            Glide.with(holder.itemView.getContext()).load(postimgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(options)
                    .placeholder(R.drawable.postimg_loading)
                    .into(holder.showImg);
        }
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
