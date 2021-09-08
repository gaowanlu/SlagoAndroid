package com.example.bigworks.uploadpost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bigworks.R;
import com.example.bigworks.utils.GlideEngine;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.ArrayList;

public class UploadPostActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private Button shareButton;
    ArrayList<Photo> selectedPhotoList=new ArrayList<>();
    private void initElement(){
        shareButton=findViewById(R.id.upload_post_button);
        //返回按钮back=(TextView)
        back=findViewById(R.id.titlebar_combar_back);
        back.setOnClickListener(v->{
            finish();
        });
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
        titlebar_title.setText("发帖");
        shareButton.setOnClickListener(v->{
                        EasyPhotos.createAlbum(this, true, false, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.example.bigworks.fileprovider")
                    .setCount(6)
                    .start(new SelectCallback() {
            @Override
            public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                //选中了某些图片
                    selectedPhotoList.clear();
                    selectedPhotoList.addAll(photos);
                        if(selectedPhotoList.size()>=1) {
                            Log.e("URI",selectedPhotoList.get(0).uri.toString());
                            Uri destinationUri = Uri.fromFile(new File(getExternalCacheDir(), "uCrop.jpg"));
                            //使用ucrop进行图片裁剪
                            UCrop.Options options = new UCrop.Options();
                            //设置裁剪图片可操作的手势
                            options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
                            options.setToolbarTitle("裁剪图像");
                            UCrop.of(selectedPhotoList.get(0).uri, destinationUri)
                                .withAspectRatio(1, 1)
                                .withMaxResultSize(2000, 2000)
                                .withOptions(options)
                                .start(UploadPostActivity.this);
                        }
                    }
            @Override
            public void onCancel() {
                //没有选中任何图片回调
                selectedPhotoList.clear();
            }
        });
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);
        initElement();
    }

    //Ucrop编辑图片后的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

}