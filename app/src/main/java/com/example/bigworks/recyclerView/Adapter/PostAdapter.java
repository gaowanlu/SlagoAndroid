package com.example.bigworks.recyclerView.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.bigworks.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> mPostList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        //声明view节点
        ImageView headimg;
        TextView content;
        public ViewHolder( View itemView) {
            super(itemView);
            //获取view节点
            headimg=itemView.findViewById(R.id.component_post_headimg);
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
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Post post=mPostList.get(position);
        //设置view节点的内容 从post中取
        holder.headimg.setImageResource(post.headimg);
        holder.content.setText(post.content);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
