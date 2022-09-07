package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//获得cookie值 传参key 返回value 如没有相应value 则返回null
public class CookieTool {
    public static String getCookie(HttpServletRequest req,String key){
        if(key==null){return null;}
        String result=null;
        //获取cookie
        Cookie[] cookies=req.getCookies();
        if(cookies!=null) {//有cookie
            for (int i = 0; i < cookies.length; i++) {
                if(key.equals(cookies[i].getName())){
                    result=cookies[i].getValue();
                }
            }
        }
        return result;
    }
}
