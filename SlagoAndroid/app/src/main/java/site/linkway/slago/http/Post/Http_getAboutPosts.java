package site.linkway.slago.http.Post;

import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.getFindPosts;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_getAboutPosts {
    public static List<String> fetch(){
        List<String> back=new ArrayList<>();
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"/getAboutPosts?size=10");
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            getFindPosts json=gson.fromJson(nameRp.body().string(), getFindPosts.class);
            if(json.result!=null){
                back=json.result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
