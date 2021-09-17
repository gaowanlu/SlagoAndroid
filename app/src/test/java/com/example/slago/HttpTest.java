package com.example.slago;


import com.example.slago.http.AccountSecurity.Http_changePwd;
import com.example.slago.http.AccountSecurity.Http_checkUser;
import com.example.slago.http.AccountSecurity.Http_registerNewCount;
import com.example.slago.http.AccountSecurity.Http_sendVerificationCode;

import org.junit.Test;
import java.util.Hashtable;

public class HttpTest {
    @Test
    public void main() {
        //改密码
        Hashtable<String,Object> changePwd= Http_changePwd.push("2209120827@qq.com","12345678","1234");
        boolean result=(Boolean) changePwd.get("result");
        String info=(String) changePwd.get("info");//获得失败情况
        System.out.println(result+" "+info);

        //注册
        Hashtable<String,Object> registerNewCount= Http_registerNewCount.push("2209120827@qq.com","12345678","erfg","万禄");
        boolean result1=(Boolean) registerNewCount.get("result");//是否成功
        String info1=(String) registerNewCount.get("info");//错误信息

        System.out.println(result1+" "+info1);

        //发验证码
        Hashtable<String,Object> sendVerificationCode= Http_sendVerificationCode.push("2209120827@qq.com");
        boolean result2=(Boolean) sendVerificationCode.get("result");//是否发送成功

        System.out.println(result2+" ");

        //验证某个用户是否存在,可以根据账号id 邮箱 昵称 来查询，id或邮箱或昵称 都可以查询，只需提供一个即可，每个人的昵称必须不同
        //修改昵称或设置前应该校验某个昵称是否已经别别人占用了,或者注册邮箱或改密码是需要校验邮箱的可用性
        System.out.println(Http_checkUser.get("email","2209120827@qq.com"));
        System.out.println(Http_checkUser.get("name","hello"));
        System.out.println(Http_checkUser.get("id","156156"));
        //以上三个返回值类型为boolean
    }
}
