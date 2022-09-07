package site.linkway.slago.http.Post;

import android.util.Log;

import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.StatusResult;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//用户提交新的帖子
//        请求地址/apis/uploadpost
//        请求格式:
//        enctype:multipart/form-data
//        "img1":图片
//        "img2":图片
//        "img3":图片
//        "img4":图片
//        "img5":图片
//        "img6":图片
//        "content":文字
//        返回格式:
//        成功:{"status":200,"result":true}
//        失败:{"status":300,"result":false}
public class Http_uploadPost {

    private static RequestBody getMultipartBody(String content,List<RequestBody> requestBodies){
        okhttp3.MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型(必填)
                .addFormDataPart("img1", "img1", requestBodies.get(0));
        for(int i=1;i<requestBodies.size();i++){
            builder.addFormDataPart("img"+Integer.toString(i+1),"img"+Integer.toString(i+1),requestBodies.get(i));
        }
        builder.addFormDataPart("content",content);
        return builder.build();
    }

    public static boolean push(String content, List<String> types, File[]files){
        boolean back=false;
        for(int i=0;i<types.size();i++){
            Log.e("绝对地址",files[i].getAbsolutePath());
        }
        try{
            OkHttpClient client=new OkHttpClient();
            //创建RequestBody
            MediaType[] mediaTypes=new MediaType[types.size()];
            List<RequestBody> requestBodies=new ArrayList<>();
            for(int i=0;i<types.size();i++){
                mediaTypes[i]=MediaType.parse(types.get(i));
                requestBodies.add(RequestBody.create(mediaTypes[i], files[i]));
            }

            // MultipartBody 上传文件专用的请求体
            RequestBody body = getMultipartBody(content,requestBodies);
            Request request=new Request.Builder()
                    .url(APIData.URL_MIPR+"uploadpost").header("Cookie", Utils.APICookie())
                    .post(body)
                    .build();
            Gson gson=new Gson();
            Response nameRp = client.newCall(request).execute();
            StatusResult rp=gson.fromJson(nameRp.body().string(), StatusResult.class);
            back= rp.result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
