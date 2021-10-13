package site.linkway.slago.http.Post;

import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.getUserPosts;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//获取指定用户的帖子
//        请求地址/apis/getUserAllPost
//        请求格式:?num=12&page=2&userid=1901420313
//        返回格式
//        成功: {"status":200,"list":["22","21","20","19","18","17","16","15","14","13","12","11"],"countPostNum":7}
//        失败: {"status":300,"list":[]}
public class Http_getUserAllPost {
    public static getUserPosts fetch(int page, int size, String userid){
        getUserPosts back=null;
        try{
            OkHttpClient client=new OkHttpClient();
            Request nameRq= Utils.SessionRequest(APIData.URL_MIPR+"/getUserAllPost?num="+
                    Integer.toString(size)+"&page="+Integer.toString(page)+"&userid="+userid);
            Gson gson=new Gson();
            Response nameRp = client.newCall(nameRq).execute();

            getUserPosts json=gson.fromJson(nameRp.body().string(), getUserPosts.class);

            if(json.list!=null){
                back=json;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }
}
