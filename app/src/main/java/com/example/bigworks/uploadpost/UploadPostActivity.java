package com.example.bigworks.uploadpost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.BoringLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigworks.R;
import com.example.bigworks.SlagoDB.UserData;
import com.example.bigworks.http.Post.Http_uploadPost;
import com.example.bigworks.http.UserData.Http_setHeadImg;
import com.example.bigworks.persondata.PersonDataPageActivity;
import com.example.bigworks.recyclerView.Adapter.ImageAdapter;
import com.example.bigworks.recyclerView.Adapter.Post;
import com.example.bigworks.recyclerView.Adapter.UploadImgAdapter;
import com.example.bigworks.utils.GlideEngine;
import com.example.bigworks.utils.UserDataUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import es.dmoral.toasty.Toasty;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UploadPostActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private Button shareButton;
    private List<Photo> photoList=new ArrayList<>();
    private List<Uri> uris=new ArrayList<>();
    private UploadImgAdapter uploadImgAdapter;
    private RecyclerView imglist;
    private EditText contentEdit;
    private File[] imgsfiles=new File[6];
    private ProgressDialog progressDialog;

    Handler HANDLER=new Handler((Message msg) -> {
        UserData userData= UserDataUtils.getAllUserData().get(0);//获取用户信息
        if(null==userData){ return false;}
        switch (msg.what){
            case 1:
                Toasty.error(this, "内容上传失败", Toast.LENGTH_SHORT, true).show();
                break;
            default:;
        }
        return true;
    });


    private void initElement(){
        //制空imgsfiles
        for(int i=0;i<6;i++){
            imgsfiles[i]=null;
        }
        imglist=findViewById(R.id.upload_post_imgs);
        shareButton=findViewById(R.id.upload_post_button);
        contentEdit=findViewById(R.id.upload_post_content);
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_uploadpost_combar_back);
        back.setOnClickListener(v->{
            finish();
        });
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_uploadpost_title);
        titlebar_title.setText("发帖");
        shareButton.setOnClickListener(v->{
            progressDialog=new ProgressDialog(UploadPostActivity.this);
            progressDialog.setTitle("头像修改");
            progressDialog.setMessage("玩命进行中...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            for(int i=0;i<photoList.size();i++) {
                minilizeImg(i);//压缩图片
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        imglist.setLayoutManager(layoutManager);
        uploadImgAdapter=new UploadImgAdapter(uris);
        imglist.setAdapter(uploadImgAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);
        initElement();
        //去选择图片
        choosePhotos();
    }

    private void uploadPost(){
        //获取文字
        String content=contentEdit.getText().toString();
        //压缩图片
        List<String> types=new ArrayList<>();
        for(Photo photo:photoList){
            types.add(photo.type);//image/jpeg
        }
        new Thread(()->{
            try {
                boolean result = Http_uploadPost.push(content, types, imgsfiles);
                if (result) {
                    progressDialog.setMessage("上传成功");
                    Thread.sleep(1000);
                    finish();//上传成功,退出发帖活动
                } else {
                    Message message=new Message();
                    message.what=1;
                    HANDLER.sendMessage(message);
                }
            }catch (Exception e){}
            finally {
                progressDialog.dismiss();
            }
        }).start();

    }

    private void notificationPhotos(){
        uploadImgAdapter.notifyDataSetChanged();
    }

    private void choosePhotos(){
        EasyPhotos.createAlbum(this, true, false, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.example.bigworks.fileProvider")
                    .setCount(6)
                    .start(new SelectCallback() {
            @Override
            public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                //选中了某些图片
                photoList.clear();
                uris.clear();
                photoList.addAll(photos);
                for(Photo photo:photoList){
                    Log.e("TYPE",photo.path.toString());
                    uris.add(photo.uri);
                }
                //图片改变事件
                notificationPhotos();
            }
            @Override
            public void onCancel() {
                //没有选中任何图片回调
                UploadPostActivity.this.finish();
            }
            });
    }


    //使用luban压缩图片
    private void minilizeImg(int i){
        //压缩过得到file,同时要将图片的类型与file进行配对，而图片类型要从uri进行获得
        Luban.with(this)
                .setTargetDir(getExternalCacheDir().getAbsolutePath())
                .load(photoList.get(i).path)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        Log.e("开始压缩","开始压缩");
                    }
                    @Override
                    public void onSuccess(File file) {
                        Log.e("绝对地址re",file.getAbsolutePath());
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        Log.e("压缩成功"+i,"压缩成功"+file.getPath());
                        //将其图片类型及file,存储起来，按i为下标记录
                        imgsfiles[i]=file;
                        //通知并检查是否压缩完了
                        if(checkMinOver()){
                            //可以上传帖子了
                            uploadPost();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        Log.e("压缩异常","压缩异常");
                    }
                }).launch();
        Log.e("执行任务","执行任务");
    }

    private boolean checkMinOver(){
        boolean back=true;
        for(int i=0;i<uris.size();i++){
            if(imgsfiles[i]==null){
                back=false;
                break;
            }
        }
        return back;
    }



}