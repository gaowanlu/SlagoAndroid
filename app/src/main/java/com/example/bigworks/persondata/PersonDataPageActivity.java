package com.example.bigworks.persondata;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.core.content.FileProvider;

import com.example.bigworks.R;
import com.example.bigworks.achievement.AchievementPageActivity;
import com.example.bigworks.utils.ScreenSizeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PersonDataPageActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;
    Button takePhoto;

    private void initElement(){
        //返回按钮back=(View)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        titlebar_title=findViewById(R.id.titlebar_title);
        TextView name = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_public_text);
        TextView myName = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_personal_text);
        name.setText("昵称");
        myName.setText("小菜鸟");

        TextView number = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_number).findViewById(R.id.rectangles_style_public_text);
        TextView myNumber = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_number).findViewById(R.id.rectangles_style_personal_text);
        number.setText("图享号");
        myNumber.setText("1901420313");

        TextView sex = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_sex).findViewById(R.id.rectangles_style_public_text);
        TextView mySex = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_sex).findViewById(R.id.rectangles_style_personal_text);
        sex.setText("性别");
        mySex.setText("男");

        TextView signature = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_signature).findViewById(R.id.rectangles_style_public_text);
        TextView mySignature = (TextView) findViewById(R.id.rectangles_linearlayout).findViewById(R.id.rectangles_signature).findViewById(R.id.rectangles_style_personal_text);
        signature.setText("个性签名");
        mySignature.setText("Hello world!");
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
        TextView textView = PersonDataPageActivity.this.findViewById(R.id.rectangles_name).findViewById(R.id.rectangles_style_personal_text);
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
        ImageView imageView = findViewById(R.id.set_my_image);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //底部对话框 头像选择方式： 1.拍照 2.相册
                Dialog dialog = new Dialog(PersonDataPageActivity.this, 0);
                View view = View.inflate(PersonDataPageActivity.this, R.layout.activity_image, null);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(true);
                //initPhoto();
                //设置对话框的占比
                view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(PersonDataPageActivity.this).getScreenHeight() * 0.21f));
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = (int) (ScreenSizeUtils.getInstance(PersonDataPageActivity.this).getScreenWidth());
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                dialogWindow.setAttributes(lp);
                dialog.show();
            }
        });
    }

    private void initPhoto(){
        takePhoto = (Button) findViewById(R.id.image_photo);
        //Log.d("chen", "!!!" + takePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(PersonDataPageActivity.this, "com.example.bigworks", outputImage);
                }else{
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture = (ImageView) findViewById(R.id.set_my_image);
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_datapage);
        Log.d("chen", "!!! " + (Button) findViewById(R.id.image_cancel));
        initElement();
        binActionForElement();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
