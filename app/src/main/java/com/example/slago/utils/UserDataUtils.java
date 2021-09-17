package com.example.slago.utils;

import org.litepal.crud.DataSupport;

import java.util.List;

//为UserData表服务
public class UserDataUtils {
    public static List<com.example.slago.SlagoDB.UserData> getAllUserData(){
        return DataSupport.findAll(com.example.slago.SlagoDB.UserData.class);
    }
    public static String getUserid(){
        String id="";
        //检测UserData表中的信息
        List<com.example.slago.SlagoDB.UserData> users=getAllUserData();
        for( com.example.slago.SlagoDB.UserData user:users){
            id=user.getUserid();
            break;
        }
        return id;
    }
    public static String getSlagoSession(){
        String v="";
        //检测UserData表中的信息
        List<com.example.slago.SlagoDB.UserData> users=getAllUserData();
        for( com.example.slago.SlagoDB.UserData user:users){
            v=user.getSlagoSession();
            break;
        }
        return v;
    }
}
