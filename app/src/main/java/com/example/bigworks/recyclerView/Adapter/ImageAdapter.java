package com.example.bigworks.recyclerView.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.bigworks.R;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.ImageLoad;
import com.example.bigworks.postpage.PostActivity;
import com.example.bigworks.postpage.VisitorActivity;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private List<String> imgids;

    //constructor
    public ImageAdapter(List<String> imgids){
        this.imgids=imgids;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        //绑定layout
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_image,parent,false);
        ImageAdapter.ViewHolder holder=new ImageAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ImageAdapter.ViewHolder holder, int position) {
        String imgid=imgids.get(position);
        if(imgid==null){return;}
        GlideUrl postimgUrl = ImageLoad.getGlideURL(APIData.URL_MIPR + "getPostImg" + "?id=" + imgid);
        Glide.with(holder.itemView.getContext()).load(postimgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.postimg_loading)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return imgids.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        //声明view节点
        ImageView img;
        View itemView;
        public ViewHolder( View itemView) {
            super(itemView);
            this.itemView=itemView;
            //获取view节点
            img=itemView.findViewById(R.id.component_image_img);
        }
    }


}
