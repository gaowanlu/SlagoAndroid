package com.example.slago.http.Post;

import com.example.slago.http.APIData;
import com.example.slago.http.Utils;
import com.example.slago.json.getPostData;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_getPostData {
    public static getPostData fetch(String postid){
        getPostData back=new getPostData();
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"/getPostData?postid="+ postid);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            String temp=nameRp.body().string();
            back=gson.fromJson(temp,getPostData.class);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
