package site.linkway.slago.http.AccountSecurity;

import android.util.Log;

import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_authentication {
    //APIS
    private static String AuthenticationURL(){
        return APIData.URL_MIP +"SlagoService_Authentication";
    }
    public static boolean fetch(){
        OkHttpClient client=new OkHttpClient();
        Request request= Utils.SessionRequest(AuthenticationURL());
        boolean back=false;
        try {
            Log.e("REQUEST",AuthenticationURL());
            Response response=client.newCall(request).execute();
            String responseData=response.body().string();
            //身份验证失败
            if(responseData.equals("{\"status\":\"200\",\"result\":\"true\"}")){
               back=true;
            }
        } catch (IOException e) { e.printStackTrace();}finally {
            return back;
        }
    }
}
