package com.example.bigworks.http.AccountSecurity;

import com.example.bigworks.http.APIData;
import com.example.bigworks.http.Utils;
import com.example.bigworks.json.ChangePwd;
import com.example.bigworks.json.StatusResult;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_checkUser {
    public static boolean get(String key,String value){
        if(key.equals("email")||key.equals("name")||key.equals("id")){
        }else{ return false; }
        boolean back=false;
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionNoCookie(APIData.URL_MIP+"CheckUser?"+key+"="+value);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            StatusResult rp=gson.fromJson(nameRp.body().string(), StatusResult.class);
            back=rp.result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
