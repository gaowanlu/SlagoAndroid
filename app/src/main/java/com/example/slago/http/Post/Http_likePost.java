package com.example.slago.http.Post;

import com.example.slago.http.APIData;
import com.example.slago.http.Utils;
import com.example.slago.json.StatusResult;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_likePost {
    public static boolean push(String postid){
        boolean back=false;
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"/likePost?postId="+ postid);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            String temp=nameRp.body().string();
            StatusResult result =gson.fromJson(temp,StatusResult.class);
            back=result.result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
