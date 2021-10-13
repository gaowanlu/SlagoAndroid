package site.linkway.slago.http.Like;

import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.LikeList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//获取用户点过赞的列表(本人只能获取自己的)
//        请求地址 /apis/likelist
//        请求格式 ?page=&pagesize=
//        返回格式: {"status":200,"list":["2","30","32","34"]}
public class Http_likelist {
    public static List<String> fetch(int page,int pagesize){
        List<String> back=new ArrayList<>();
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"/likelist?page="+Integer.toString(page)+"&pagesize="+Integer.toString(pagesize));
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
