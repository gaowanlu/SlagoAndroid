package site.linkway.slago.utils;

import org.litepal.crud.DataSupport;

import java.util.List;

import site.linkway.slago.SlagoDB.UserData;

//为UserData表服务
public class UserDataUtils {
    public static List<UserData> getAllUserData(){
        return DataSupport.findAll(UserData.class);
    }
    public static String getUserid(){
        String id="";
        //检测UserData表中的信息
        List<UserData> users=getAllUserData();
        for( UserData user:users){
            id=user.getUserid();
            break;
        }
        return id;
    }
    public static String getSlagoSession(){
        String v="";
        //检测UserData表中的信息
        List<UserData> users=getAllUserData();
        for( UserData user:users){
            v=user.getSlagoSession();
            break;
        }
        return v;
    }
}
