package com.example.slago.SlagoDB;

import org.litepal.crud.DataSupport;

public class UserData extends DataSupport {
    private long id;
    private String userid;//账号
    private String SlagoSession;//session
    private String name;//昵称
    private String sex;//性别
    private String profile;//个人介绍
    private String likeNum;//被喜欢的数量
    private String aboutNum;//关注别人的数量
    private String fansNum;//粉丝数量

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

    public String getSex() { return sex; }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }
    public String getLikeNum(){
        return likeNum;
    }

    public String getAboutNum() {
        return aboutNum;
    }

    public void setAboutNum(String aboutNum) {
        this.aboutNum = aboutNum;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }
}
