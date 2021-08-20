package com.example.bigworks.http;

import com.example.bigworks.utils.UserData;

import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Utils {
    public static String APICookie(){
        //检测UserData表中的信息
        String id= UserData.getUserid();
        String SlagoSession=UserData.getSlagoSession();
        //返回Cookie
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
