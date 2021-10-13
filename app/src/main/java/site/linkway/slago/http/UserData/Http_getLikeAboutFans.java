package site.linkway.slago.http.UserData;


import site.linkway.slago.SlagoDB.UserData;
import site.linkway.slago.http.APIData;
import site.linkway.slago.http.Utils;
import site.linkway.slago.json.getLikeAboutFans;
import site.linkway.slago.utils.UserDataUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_getLikeAboutFans {
    public static int[] fetch(String userid){
        int[] back=new int[3];
        for(int i=0;i<back.length;i++){back[i]=0;}
        OkHttpClient client=new OkHttpClient();
        Request likeaboutfansRq= Utils.SessionRequest(APIData.URL_MIPR+"getLikeAboutFans?id="+userid);
        Gson gson=new Gson();
        try{
            Response likeaboutfansRp=client.newCall(likeaboutfansRq).execute();
            getLikeAboutFans getLikeAboutFans_=gson.fromJson(likeaboutfansRp.body().string(),getLikeAboutFans.class);
            //更新用户信息
            UserData userData = new UserData();
            userData.setLikeNum(getLikeAboutFans_.likeNum);
            userData.setAboutNum(getLikeAboutFans_.aboutNum);
            userData.setFansNum(getLikeAboutFans_.fansNum);
            userData.updateAll("userid = ?", UserDataUtils.getUserid());

            back[0]=Integer.valueOf(getLikeAboutFans_.likeNum);
            back[1]=Integer.valueOf(getLikeAboutFans_.aboutNum);
            back[2]=Integer.valueOf(getLikeAboutFans_.fansNum);
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            return back;
        }
    }
}
