package site.linkway.slago.http.UserData;

import site.linkway.slago.SlagoDB.UserData;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.StatusResult;
import site.linkway.slago.utils.UserDataUtils;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_setUserName {
    public static boolean push(String newName){
        boolean back=false;
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"updateUserName?newname="+ newName);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            StatusResult rp=gson.fromJson(nameRp.body().string(), StatusResult.class);
            //更新数据库
            back=rp.result;
            if(back) {
                UserData userData = new UserData();
                userData.setName(newName);
                userData.updateAll("userid = ?", UserDataUtils.getUserid());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
