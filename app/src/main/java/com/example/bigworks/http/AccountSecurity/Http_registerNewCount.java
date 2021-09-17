package com.example.bigworks.http.AccountSecurity;

import com.example.bigworks.http.APIData;
import com.example.bigworks.http.Utils;
import com.example.bigworks.json.ChangePwd;
import com.example.bigworks.json.RegisterNewCount;
import com.google.gson.Gson;

import java.util.Hashtable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_registerNewCount {
    public static Hashtable<String,Object> push(String email, String password, String checkCode,String name){
        //?email=&sex=&name=&password=&checkCode=$
        //{"status":200,"result":true,"id":"注册的账号"}
        Hashtable<String,Object> back=new Hashtable<>();
        boolean result=false;
        String info="出现错误、请检查应用版本";
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionNoCookie(APIData.URL_MIP+"RegisterNewCount?email="+email+"&password="+password+"&checkCode="+checkCode+"&name="+name);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            RegisterNewCount rp=gson.fromJson(nameRp.body().string(), RegisterNewCount.class);
            result=rp.result;
            info=rp.id;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            back.put("info",info);
            back.put("result",result);
            return back;
        }
    }
}
