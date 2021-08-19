package com.example.bigworks.SlagoDB;

import org.litepal.crud.DataSupport;

public class UserData extends DataSupport {
    private long id;
    private String userid;////账号
    private String SlagoSession;//session
    private String name;//昵称
    private String sex;//性别
    private String profile;//个人介绍

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSlagoSession() {
        return SlagoSession;
    }

    public void setSlagoSession(String slagoSession) {
        SlagoSession = slagoSession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
