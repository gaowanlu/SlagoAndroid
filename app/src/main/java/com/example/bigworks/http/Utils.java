package com.example.bigworks.http;

import com.example.bigworks.SlagoDB.UserData;

import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Utils {
    private static String APICookie(){
        //检测UserData表中的信息
        String id="";
        String SlagoSession="";
        List<UserData> users= DataSupport.findAll(UserData.class);
        for( UserData user:users){
            id=user.getUserid();
            SlagoSession=user.getSlagoSession();
            break;
        }
        //返回Cookie
        //id=123456; SlagoSession=7MhAxGPYTMNOBDqUzwDVNZKdt6Grz9OgijWgHubhmJY5J8vYQrpSBPzLa5JckHa0AiBQNAV52mxBUGt0bq4NshZuaMMvJODvCzBH1xHWpOpwq38wz8vmNSKIhlJJ4
        return "id="+id+"; "+"SlagoSession="+SlagoSession;
    }
    public static Request SessionRequest(String URL){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(URL).header("Cookie",APICookie())
                .build();
        return request;
    }
    public static Request SessionRequest(String URL,RequestBody requestBody){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(URL).header("Cookie",APICookie())
                .post(requestBody).build();
        return request;
    }
}
