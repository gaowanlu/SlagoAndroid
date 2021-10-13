package site.linkway.slago.persondata;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import site.linkway.slago.R;
import site.linkway.slago.SlagoDB.UserData;
import site.linkway.slago.activityCollector.BaseActivity;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.ImageLoad;
import site.linkway.slago.http.UserData.Http_getUserName;
import site.linkway.slago.http.UserData.Http_getUserProfile;
import site.linkway.slago.http.UserData.Http_getUserSex;
import site.linkway.slago.http.UserData.Http_setHeadImg;
import site.linkway.slago.utils.ImageModify;
import site.linkway.slago.utils.ScreenSizeUtils;
import site.linkway.slago.utils.UserDataUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileNotFoundException;

import static org.litepal.LitePalApplication.getContext;

public class PersonDataPageActivity extends BaseActivity {
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
    private ImageModify img = new ImageModify();
    private ProgressDialog progressDialog;


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

    private void useUCrop(Uri uri){
        Uri destinationUri = Uri.fromFile(new File(getExternalCacheDir(), "uCrop.jpg"));
        //使用ucrop进行图片裁剪
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setToolbarTitle("裁剪头像");
        UCrop.of(img.getImageUri(), destinationUri)
                .withAspectRatio(1, 1)
                .withOptions(options)
                .start(PersonDataPageActivity.this);
    }

    private void uploadHeadImg(File imgfile,ProgressDialog progressDialog){
        new Thread(()->{
            try {
                boolean result = Http_setHeadImg.push(imgfile);
                Log.e("RESULT", Boolean.toString(result));
                int sleepTime = 2000;
                if (result) {
                    progressDialog.setMessage("设置成功");
                    sleepTime = 1000;
                } else {
                    progressDialog.setMessage("设置失败");
                }
                try {
                    Thread.sleep(sleepTime);//延时
                } catch (Exception e) {
                }
            }catch (Exception e){}finally {
                progressDialog.dismiss();//去掉dialog
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri uri = UCrop.getOutput(data);
            Log.e("PATH",uri.getPath());
            File imgfile=new File(uri.getPath());
            progressDialog=new ProgressDialog(PersonDataPageActivity.this);
            progressDialog.setTitle("头像修改");
            progressDialog.setMessage("玩命进行中...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            uploadHeadImg(imgfile,progressDialog);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Log.e("选中图片","");
                    Last_Choose = TAKE_PHOTO;
                    dialog.dismiss();
                    //img.cropPhoto();
                    useUCrop(img.getImageUri());
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    Last_Choose = CHOOSE_PHOTO;
                    img.judgeVersion(data);
                    dialog.dismiss();
                    //img.cropPhoto();
                    useUCrop(img.getImageUri());
                }
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
                            Log.e("URI",uri.getPath().toString());
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(img.getImageUri()));
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
