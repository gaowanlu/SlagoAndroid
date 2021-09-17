package com.example.slago.http.UserData;

import android.util.Log;

import com.example.slago.http.APIData;
import com.example.slago.http.Utils;
import com.example.slago.json.Status_sResult_s;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Http_setHeadImg {
    public static boolean push(File file){
        Log.e("头像",file.getPath());
        boolean back=false;
        try{
            OkHttpClient client=new OkHttpClient();
            MediaType mediaType = MediaType.parse("image/jpeg");
            // 把文件封装进请求体
            RequestBody fileBody = RequestBody.create(mediaType, file);
            // MultipartBody 上传文件专用的请求体
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM) // 表单类型(必填)
                    .addFormDataPart("headImg", file.getName(), fileBody)
                    .build();
            Request request=new Request.Builder()
                    .url(APIData.URL_MIPR+"setHeadImg").header("Cookie",Utils.APICookie())
                    .post(body)
                    .build();
            Gson gson=new Gson();
            Response nameRp = client.newCall(request).execute();
            Status_sResult_s rp=gson.fromJson(nameRp.body().string(), Status_sResult_s.class);
            if(rp.result.equals("true")){
                back=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
