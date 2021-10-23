package site.linkway.slago.http.About;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.LikeList;
import site.linkway.slago.json.StatusResult;

public class Http_about {
    public static boolean push(String userid){
        boolean back=false;
        try {
            OkHttpClient client = new OkHttpClient();
            Request nameRq = Utils.SessionRequest(APIData.URL_MIPR + "/following?otherId=" + userid);
            Gson gson = new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            StatusResult json = gson.fromJson(nameRp.body().string(), StatusResult.class);
            if(true==json.result){//取消关注成功
                back=true;
            }
        }catch (Exception e){}
        finally {
            return back;
        }
    }
}
