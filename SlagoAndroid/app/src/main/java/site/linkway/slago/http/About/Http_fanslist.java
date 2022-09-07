package site.linkway.slago.http.About;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.LikeList;

public class Http_fanslist {
    public static List<String> fetch(int page, int pagesize){
        List<String> back=new ArrayList<>();
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"/fanslist?page="+Integer.toString(page)+"&pagesize="+Integer.toString(pagesize));
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();
            LikeList json=gson.fromJson(nameRp.body().string(), LikeList.class);
            if(json.list!=null){
                back=json.list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
