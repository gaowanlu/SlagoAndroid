package com.example.slago.http.AccountSecurity;

import com.example.slago.http.APIData;
import com.example.slago.http.Utils;
import com.example.slago.json.ChangePwd;
import com.google.gson.Gson;

import java.util.Hashtable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_changePwd {
    public static Hashtable<String,Object> push(String email, String newPassword, String checkCode){
        Hashtable<String,Object> back=new Hashtable<>();
        boolean result=false;
        String info="出现错误、请检查应用版本及网络";
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionNoCookie(APIData.URL_MIP+"ChangePwd?email="+email+"&new="+newPassword+"&check="+checkCode);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            ChangePwd rp=gson.fromJson(nameRp.body().string(), ChangePwd.class);
            System.out.println(nameRp.body().string());
            if(rp.result.equals("true")){
                result=true;
            }else{
                info=rp.result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            back.put("info",info);
            back.put("result",result);
            return back;
        }
    }
}
