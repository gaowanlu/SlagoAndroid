package com.example.bigworks.http.UserData;

import com.example.bigworks.SlagoDB.UserData;
import com.example.bigworks.http.APIData;
import com.example.bigworks.http.Utils;
import com.example.bigworks.json.getUserName;
import com.example.bigworks.json.getUserProfile;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_getUserProfile {
    public static String fetch(String userid){
        String back="";
        try{
            OkHttpClient client=new OkHttpClient();
            //排队请求数据
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"getUserProfile?id="+ userid);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            getUserProfile rp=gson.fromJson(nameRp.body().string(),getUserProfile.class);
            //更新数据库
            UserData userData = new UserData();
            userData.setProfile(rp.result);
            userData.updateAll("userid = ?", userid);
            back=rp.result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
