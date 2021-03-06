package site.linkway.slago.http.UserData;

import site.linkway.slago.SlagoDB.UserData;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.getUserName;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Http_getUserName {

    public static String fetch(String userid){
        String back="";
        try{
            OkHttpClient client=new OkHttpClient();
            //排队请求数据
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"getUserName?id="+ userid);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            getUserName getUserName_=gson.fromJson(nameRp.body().string(),getUserName.class);
            //更新数据库
            UserData userData = new UserData();
            userData.setName(getUserName_.name);
            userData.updateAll("userid = ?", userid);
            back=getUserName_.name;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
