package site.linkway.slago.postpage;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
        if(null!=postdata){
            Log.e("????????????",postdata.postid);
            for(int i=0;i<postdata.imgs.size();i++){
                Log.e(Integer.toString(i),postdata.imgs.get(i));
            }
        }
        initElement();
        bindEvent();
        dataToView();
    }

    private void initElement(){
        //????????????back=(TextView)
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
                // ????????????????????????
                return false;
            }
        };
        imglist.setLayoutManager(layoutManager);
        imageAdapter=new ImageAdapter(postdata.imgs);
        imglist.setAdapter(imageAdapter);
    }

    private void bindEvent(){
        back.setClickable(true);//?????????textview????????????
        //??????????????????????????????????????????????????????????????????
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//????????????
            }
        });
        //heart????????????
        heartButton.setOnClickListener(v->{
            postdata.liked=!postdata.liked;
            checkHeart();
        });
        //??????????????????
        headimg.setOnClickListener(v->{
            Intent intent= new Intent(PostActivity.this, UserPeronalActivity.class);
            intent.putExtra("postdata",postdata);
            intent.putExtra("userid",postdata.userid);
            startActivity(intent);
        });
        //????????????
        commentButton.setOnClickListener(v->{
            Toasty.success(this, "????????????", Toast.LENGTH_SHORT, true).show();
        });
        //????????????
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
        //????????????
        //??????
        RoundedCorners roundedCorners= new RoundedCorners(6);
        //??????RequestOptions????????????,override?????????,??????ImageView????????????,??????????????????,??????????????????
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        //????????????
        GlideUrl glideUrl= ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ postdata.userid);
        //???????????????
        //                .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(glideUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.headimg_loading)
                .into(headimg);
        if(postdata.liked){//?????????????????????
            heartButton.setImageResource(R.drawable.heart_fill_red);
        }
        //?????????????????????????????????????????????
        dateText.setText(postdata.postdate);
        likeCollectionText.setText(postdata.likeNum+" ?????? "+postdata.collectionNum+" ?????? ");
        userNameText.setText(postdata.userid);
        postContentText.setText("  "+postdata.content);
    }

    private void myPopupMenu(View v) {
        //??????PopupMenu??????
        PopupMenu popupMenu = new PopupMenu(this, v);
        //??????PopupMenu???????????????
        popupMenu.getMenuInflater().inflate(R.menu.post_operator, popupMenu.getMenu());
        //??????PopupMenu???????????????
        if(postdata.userid.equals(UserDataUtils.getUserid())) {
            popupMenu.setOnMenuItemClickListener(item -> {
                //????????????
                new Thread(() -> {
                    boolean deleteResult = Http_deletePost.push(postdata.postid);
                    if (true == deleteResult) {
                        Message message = new Message();
                        message.what = 1;
                        HANDLER.sendMessage(message);
                    } else {
                        Toasty.error(this, "????????????").show();
                    }
                }).start();
                return false;
            });
            //????????????
            popupMenu.show();
        }
    }
}