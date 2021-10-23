package site.linkway.slago.recyclerView.Adapter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import site.linkway.slago.R;
import site.linkway.slago.SlagoDB.UserData;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.ImageLoad;
import site.linkway.slago.http.UserData.Http_getUserName;
import site.linkway.slago.postpage.UserPeronalActivity;

public class LeftHeadimgRightNameAdapter extends RecyclerView.Adapter<LeftHeadimgRightNameAdapter.ViewHolder>{
    private List<UserData> users;
    static class ViewHolder extends RecyclerView.ViewHolder{
        //声明view节点
        ImageView img;
        TextView rightText;
        View itemView;
        public ViewHolder( View itemView) {
            super(itemView);
            this.itemView=itemView;
            //获取view节点
            img=itemView.findViewById(R.id.component_leftimg_rightname_img);
            rightText=itemView.findViewById(R.id.component_leftimg_rightname_name);
        }
    }

    public LeftHeadimgRightNameAdapter(List<UserData> userids){
        this.users=userids;
    }

    @NonNull
    @NotNull
    @Override
    public LeftHeadimgRightNameAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //绑定layout
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_leftimg_rightname,parent,false);
        LeftHeadimgRightNameAdapter.ViewHolder holder=new LeftHeadimgRightNameAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LeftHeadimgRightNameAdapter.ViewHolder holder, int position) {
        UserData id=users.get(position);
        if(id==null){return;}
        GlideUrl imgUrl = ImageLoad.getGlideURL(APIData.URL_MIPR + "getUserHeadImg" + "?id=" + id.getUserid());
        Glide.with(holder.itemView.getContext()).load(imgUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.postimg_loading)
                .into(holder.img);
        //获取昵称
        holder.rightText.setText(id.getName());
        holder.itemView.setOnClickListener(v->{
            Intent intent= new Intent(holder.itemView.getContext(), UserPeronalActivity.class);
            intent.putExtra("userid",id.getUserid());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(users==null||users.size()==0){
            return 0;
        }
        return users.size();
    }
}
