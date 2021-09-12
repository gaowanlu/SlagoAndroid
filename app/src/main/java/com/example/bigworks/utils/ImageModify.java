package com.example.bigworks.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.bigworks.R;
import com.example.bigworks.http.UserData.Http_setHeadImg;
import com.example.bigworks.persondata.PersonDataPageActivity;
import com.wildma.pictureselector.ImageUtils;
import com.wildma.pictureselector.PictureSelector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.app.ActivityCompat.startIntentSenderForResult;

public class ImageModify {
    private Uri imageUri;
    private Button takePhoto;
    private Button cancel;
    private Button chooseFromAlbum;
    private Context context;
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private static final int CROP_PHOTO = 3;

    public Uri getImageUri(){
        return imageUri;
    }

    //拍照对话框
    public void initImagePhoto(Context context,Dialog dialog){
        this.context = context;
        takePhoto = (Button) dialog.findViewById(R.id.image_photo);
        takePhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission((Activity) context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                } else {
                    openCamera();
                }
            }
        });
    }

    //取消对话框
    public void imageCancel(Dialog dialog){
        cancel = (Button) dialog.findViewById(R.id.image_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //相册对话框
    public void initImageAlbum(Context context, Dialog dialog){
        this.context = context;
        chooseFromAlbum = (Button) dialog.findViewById(R.id.image_album);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                }else{
                    openAlbum();
                }
            }
        });
    }

    private void openCamera(){
        File outputImage = new File(context.getExternalCacheDir(), "output_image.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(context, "com.example.bigworks.fileProvider", outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        ((Activity) this.context).startActivityForResult(intent, TAKE_PHOTO);
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        ((Activity) this.context).startActivityForResult(intent, CHOOSE_PHOTO);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(context, "You denide the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(){
        String imagePath = null;
        if(DocumentsContract.isDocumentUri(this.context, imageUri)){
            String docId = DocumentsContract.getDocumentId(imageUri);
            if("com.android.providers.media.documents".equals(imageUri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if("com.android.providers.downloads.documents".equals(imageUri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(imageUri.getScheme())){
            imagePath = getImagePath(imageUri, null);
        }else if("file".equalsIgnoreCase(imageUri.getScheme())){
            imagePath = imageUri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitkat(){
        String imagePath = getImagePath(imageUri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = this.context.getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void judgeVersion(Intent data){
        imageUri = data.getData();
        if(Build.VERSION.SDK_INT >= 19){
            handleImageOnKitKat();
        }else{
            handleImageBeforeKitkat();
        }
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            imageUri  = ImageUtils.getImageUri(context, imagePath);
        }else{
            Toast.makeText(this.context, "faild to get image", Toast.LENGTH_SHORT).show();
        }
    }

    //把bitmap转成file
    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File("/storage/emulated/0/Pictures" + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public void cropPhoto() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);//问题在这里
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // no face detection
        ((Activity) this.context).startActivityForResult(intent, CROP_PHOTO);
    }
}