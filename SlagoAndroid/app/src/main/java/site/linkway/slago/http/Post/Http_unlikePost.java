package site.linkway.slago.http.Post;

import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.StatusResult;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_unlikePost {
    public static boolean push(String postid){
        boolean back=false;
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"/unlikePost?postId="+ postid);
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
