package site.linkway.slago.http.AccountSecurity;

import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.SendVerificationCode;
import com.google.gson.Gson;

import java.util.Hashtable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_sendVerificationCode {
    public static Hashtable<String,Object> push(String email){
        Hashtable<String,Object> back=new Hashtable<>();
        boolean result=false;
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionNoCookie(APIData.URL_MIP+"SendVerificationCode?email="+email);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            SendVerificationCode rp=gson.fromJson(nameRp.body().string(), SendVerificationCode.class);
            result=rp.result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            back.put("result",result);
            return back;
        }
    }
}
