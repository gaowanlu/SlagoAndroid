package site.linkway.slago.http.About;

import java.util.List;
import java.util.Map;

public class Http_checkAbouted {
    public static boolean fetch(String userid){
        boolean back=false;
        try{
            //检查是否已经关注
            back=Http_unabout.push(userid);
            if(true==back){//取消了关注，需要在关注回去
                Http_about.push(userid);
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            return back;
        }
    }
}
