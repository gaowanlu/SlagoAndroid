package site.linkway.slago.postpage;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import site.linkway.slago.R;
import site.linkway.slago.activityCollector.BaseActivity;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.ImageLoad;
import site.linkway.slago.http.Post.Http_deletePost;
import site.linkway.slago.http.Post.Http_likePost;
import site.linkway.slago.http.Post.Http_unlikePost;
import site.linkway.slago.recyclerView.Adapter.ImageAdapter;
import site.linkway.slago.recyclerView.Adapter.Post;

import es.dmoral.toasty.Toasty;
import site.linkway.slago.utils.UserDataUtils;

public class PostActivity extends BaseActivity {
    private View back;
    private Post postdata;
    private RecyclerView imglist;
    private ImageAdapter imageAdapter;
    private ImageView heartButton;
    private ImageView headimg;
    private TextView dateText;
    private TextView likeCollectionText;
    private  TextView userNameText;
    private TextView postContentText;
    private Button commentButton;
    private ImageView postOperatorButton;
    Handler HANDLER=new Handler((Message msg) -> {
        switch (msg.what){
            case 1:
                this.finish();
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
        dateText=findViewById(R.id.post_dateText);
        likeCollectionText=findViewById(R.id.post_likeCollectionText);
        userNameText=findViewById(R.id.post_username);
        imglist=findViewById(R.id.post_recyclerView);
        postContentText=findViewById(R.id.post_contentText);
        commentButton=findViewById(R.id.post_commentButton);
        postOperatorButton=findViewById(R.id.post_operator_button);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };
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
            Intent intent= new Intent(PostActivity.this, UserPeronalActivity.class);
            intent.putExtra("postdata",postdata);
            intent.putExtra("userid",postdata.userid);
            startActivity(intent);
        });
        //评论按钮
        commentButton.setOnClickListener(v->{
            Toasty.success(this, "查看评论", Toast.LENGTH_SHORT, true).show();
        });
        //操作选项
        postOperatorButton.setOnClickListener(v->{
            myPopupMenu(v);
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
        if(postdata.liked){//如果已经点了赞
            heartButton.setImageResource(R.drawable.heart_fill_red);
        }
        //发布日期以及获赞数量及收藏数量
        dateText.setText(postdata.postdate);
        likeCollectionText.setText(postdata.likeNum+" 喜欢 "+postdata.collectionNum+" 收藏 ");
        userNameText.setText(postdata.userid);
        postContentText.setText("  "+postdata.content);
    }

    private void myPopupMenu(View v) {
        //定义PopupMenu对象
        PopupMenu popupMenu = new PopupMenu(this, v);
        //设置PopupMenu对象的布局
        popupMenu.getMenuInflater().inflate(R.menu.post_operator, popupMenu.getMenu());
        //设置PopupMenu的点击事件
        if(postdata.userid.equals(UserDataUtils.getUserid())) {
            popupMenu.setOnMenuItemClickListener(item -> {
                //删除操作
                new Thread(() -> {
                    boolean deleteResult = Http_deletePost.push(postdata.postid);
                    if (true == deleteResult) {
                        Message message = new Message();
                        message.what = 1;
                        HANDLER.sendMessage(message);
                    } else {
                        Toasty.error(this, "删除失败").show();
                    }
                }).start();
                return false;
            });
            //显示菜单
            popupMenu.show();
        }
    }
}