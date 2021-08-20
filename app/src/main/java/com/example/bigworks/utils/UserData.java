package com.example.bigworks.utils;

import org.litepal.crud.DataSupport;

import java.util.List;

public class UserData {
    public static List<com.example.bigworks.SlagoDB.UserData> getAllUserData(){
        return DataSupport.findAll(com.example.bigworks.SlagoDB.UserData.class);
    }
    public static String getUserid(){
        String id="";
        //检测UserData表中的信息
        List<com.example.bigworks.SlagoDB.UserData> users=getAllUserData();
        for( com.example.bigworks.SlagoDB.UserData user:users){
            id=user.getUserid();
            break;
        }
        return id;
    }
    public static String getSlagoSession(){
        String v="";
        //检测UserData表中的信息
        List<com.example.bigworks.SlagoDB.UserData> users=getAllUserData();
        for( com.example.bigworks.SlagoDB.UserData user:users){
            v=user.getSlagoSession();
            break;
        }
        return v;
    }
}
