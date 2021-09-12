package com.example.bigworks.persondata;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.bigworks.R;
import com.example.bigworks.SlagoDB.UserData;
import com.example.bigworks.achievement.AchievementPageActivity;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.ImageLoad;
import com.example.bigworks.http.UserData.Http_getUserName;
import com.example.bigworks.http.UserData.Http_getUserProfile;
import com.example.bigworks.http.UserData.Http_getUserSex;
import com.example.bigworks.http.UserData.Http_setHeadImg;
import com.example.bigworks.http.UserData.Http_setUserName;
import com.example.bigworks.uploadpost.UploadPostActivity;
import com.example.bigworks.utils.ImageModify;
import com.example.bigworks.utils.ScreenSizeUtils;
import com.example.bigworks.utils.UserDataUtils;
import com.wildma.pictureselector.ImageUtils;
import com.wildma.pictureselector.PictureSelectUtils;
import com.wildma.pictureselector.PictureSelector;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.litepal.LitePalApplication.getContext;

public class PersonDataPageActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int CROP_PHOTO = 3;
    private int Last_Choose = 0;
    private Dialog dialog;
    private TextView textView;

    private TextView name;
    private TextView myName;
    private TextView number;
    private TextView myNumber;
    private TextView sex;
    private TextView mySex;
    private TextView signature;
    private TextView mySignature;
    private ImageView imageView;
    private ImageModify img = new ImageModify();;


    Handler HANDLER=new Handler((Message msg) -> {
        UserData userData=UserDataUtils.getAllUserData().get(0);//获取用户信息
        if(null==userData){ return false;}
        switch (msg.what){
            case 1:
                myName.setText(userData.getName());
                break;
            case 2:
                myNumber.setText(userData.getUserid());
                break;
            case 3:
                mySex.setText(userData.getSex());
                break;
            case 4:
                mySignature.setText(userData.getProfile());
                break;
            default:;
        }
        return true;
    });

    private void initElement(){
        //返回按钮back=(View)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        titlebar_title=findViewById(R.id.titlebar_title);

        UserData userData=UserDataUtils.getAllUserData().get(0);//获取用户信息
        imageView = findViewById(R.id.set_my_image);
        //加载头像
        GlideUrl glideUrl= ImageLoad.getGlideURL(APIData.URL_MIPR+"getUserHeadImg"+"?id="+ UserDataUtils.getUserid());
        //更新到视图
        Glide.with(getContext()).load(glideUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.headimg_loading)
                .into(imageView);

        name = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_public_text);
        myName = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_personal_text);
        name.setText("昵称");
        myName.setText(userData.getName());
        //更新昵称
        new Thread(()->{
            Http_getUserName.fetch(UserDataUtils.getUserid());
            Message message=new Message();
            message.what=1;
            HANDLER.sendMessage(message);
        }).start();

        number = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_number).findViewById(R.id.rectangles_style_public_text);
        myNumber = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_number).findViewById(R.id.rectangles_style_personal_text);
        number.setText("图享号");
        myNumber.setText(userData.getUserid());
        //更新账号
        new Thread(()->{
            Http_getUserName.fetch(UserDataUtils.getUserid());
            Message message=new Message();
            message.what=2;
            HANDLER.sendMessage(message);
        }).start();

        sex = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_sex).findViewById(R.id.rectangles_style_public_text);
        mySex = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_sex).findViewById(R.id.rectangles_style_personal_text);
        sex.setText("性别");
        mySex.setText(userData.getSex());
        //获取性别
        new Thread(()->{
            Http_getUserSex.fetch(UserDataUtils.getUserid());
            Message message=new Message();
            message.what=3;
            HANDLER.sendMessage(message);
        }).start();

        signature = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_signature).findViewById(R.id.rectangles_style_public_text);
        mySignature = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_signature).findViewById(R.id.rectangles_style_personal_text);
        signature.setText("个性签名");
        mySignature.setText(userData.getProfile());
        //更新个性签名
        new Thread(()->{
            Http_getUserProfile.fetch(UserDataUtils.getUserid());
            Message message=new Message();
            message.what=4;
            HANDLER.sendMessage(message);
        }).start();
    }

    private void binActionForElement(){
        //设置标题栏文字
        titlebar_title.setText("个人信息");
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });

        //昵称按钮跳转
        textView = PersonDataPageActivity.this.findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_personal_text);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(PersonDataPageActivity.this, MyName.class);
                startActivity(intent);
            }
        });

        //性别按钮跳转
        textView = PersonDataPageActivity.this.findViewById(R.id.rectangles_sex).findViewById(R.id.rectangles_style_personal_text);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(PersonDataPageActivity.this, MySexActivity.class);
                startActivity(intent);
            }
        });

        //个性签名按钮跳转
        textView = PersonDataPageActivity.this.findViewById(R.id.rectangles_signature).findViewById(R.id.rectangles_style_personal_text);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(PersonDataPageActivity.this, PersonalSignatureActivity.class);
                startActivity(intent);
            }
        });

        //头像按钮跳转
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //底部对话框 头像选择方式： 1.拍照 2.相册
                dialog = new Dialog(PersonDataPageActivity.this, 0);
                View view = View.inflate(PersonDataPageActivity.this, R.layout.activity_image, null);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(true);
                //设置对话框的占比
                view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(PersonDataPageActivity.this).getScreenHeight() * 0.21f));
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = (int) (ScreenSizeUtils.getInstance(PersonDataPageActivity.this).getScreenWidth());
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                dialogWindow.setAttributes(lp);
                dialog.show();
                img.initImagePhoto(PersonDataPageActivity.this, dialog);
                img.initImageAlbum(PersonDataPageActivity.this, dialog);
                img.imageCancel(dialog);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Last_Choose = TAKE_PHOTO;
                    img.cropPhoto();
                }
                dialog.dismiss();
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    Last_Choose = CHOOSE_PHOTO;
                    img.judgeVersion(data);
                    img.cropPhoto();
                }
                dialog.dismiss();
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = null;
                    File imgfile = null;

                    //格式转化
                    if(Last_Choose == TAKE_PHOTO){
                        try {
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(img.getImageUri()));
                            imgfile = img.getFile(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(Last_Choose == CHOOSE_PHOTO){
                        try {
                            Uri uri = (Uri) data.getData();
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                            imgfile = img.getFile(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
//                        String s = ImageUtils.getImagePath(PersonDataPageActivity.this, uri);
//                        imgfile = new File(s);
                    }

                    File file = imgfile;
                    new Thread(()->{
                        boolean result=Http_setHeadImg.push(file);
                        Log.e("RESULT", Boolean.toString(result));
                    }).start();
                    this.onResume();
                }
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新获取数据的
        setContentView(R.layout.activity_person_datapage);
        initElement();
        binActionForElement();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_datapage);
        initElement();
        binActionForElement();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
