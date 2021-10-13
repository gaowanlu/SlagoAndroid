package site.linkway.slago.recyclerView.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import site.linkway.slago.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UploadImgAdapter extends RecyclerView.Adapter<UploadImgAdapter.ViewHolder> {
    private List<Uri> imgids;

    //constructor
    public UploadImgAdapter(List<Uri> imgids){
        this.imgids=imgids;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        //绑定layout
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_image,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UploadImgAdapter.ViewHolder holder, int position) {
        Uri imgid=imgids.get(position);
        if(imgid==null){return;}
        Glide.with(holder.itemView.getContext()).load(imgid)
                .placeholder(R.drawable.postimg_loading)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if(imgids==null||imgids.size()==0){
            return 0;
        }
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
