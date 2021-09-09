package com.example.bigworks.recyclerView.Adapter;

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
import com.bumptech.glide.request.RequestOptions;
import com.example.bigworks.R;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.ImageLoad;
import com.example.bigworks.utils.UserDataUtils;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> mPostList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        //声明view节点
        ImageView headimg;
        TextView content;
        TextView username;
        ImageView showImg;
        TextView likeNum;
        TextView collectionNum;
        TextView commentNum;
        View itemView;
        public ViewHolder( View itemView) {
            super(itemView);
            this.itemView=itemView;
            //获取view节点
            headimg=itemView.findViewById(R.id.component_post_headimg);
            content=itemView.findViewById(R.id.component_post_content);
            username=itemView.findViewById(R.id.component_post_userid);
            showImg=itemView.findViewById(R.id.component_post_showImg);
            likeNum=itemView.findViewById(R.id.component_post_likeNum);
            collectionNum=itemView.findViewById(R.id.component_post_collectionNum);
            commentNum=itemView.findViewById(R.id.component_post_commentNum);
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
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Post post=mPostList.get(position);
        //设置view节点的内容 从post中取
        holder.headimg.setImageResource(post.headimg);
        holder.content.setText(post.content);
        holder.username.setText(post.userid);
        holder.commentNum.setText(Integer.toString(post.commentNum));
        holder.collectionNum.setText(Integer.toString(post.collectionNum));
        holder.likeNum.setText(Integer.toString(post.likeNum));

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
        GlideUrl postimgUrl= ImageLoad.getGlideURL(APIData.URL_MIPR+"getPostImg"+"?id="+ post.postid);
        Glide.with(holder.itemView.getContext()).load(postimgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.postimg_loading)
                .into(holder.showImg);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
