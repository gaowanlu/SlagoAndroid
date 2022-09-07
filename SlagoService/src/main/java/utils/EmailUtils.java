package utils;

import jodd.mail.*;

import java.util.Date;

public class EmailUtils {
    private static SmtpServer smtpServer= MailServer.create()
            .ssl(true)
            .host(SlagoEmailData.host)
            .port(25)
            .auth(SlagoEmailData.username,SlagoEmailData.password)
            .buildSmtpMailServer();
    //发送文字信息
    public static boolean SendEmail(String user,String subject,String message){
        boolean back=false;
        SendMailSession session = smtpServer.createSession();
        try {
            Email email = Email.create()
                    .from(SlagoEmailData.username)
                    .to(user)
                    .subject(subject)
                    .textMessage(message);
            session.open();
            session.sendMail(email);
            back=true;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
            return back;
        }
    }
    //发送验证码
    public static boolean SendVerificationCode(String user,String subject,String message,String process){
        boolean back=false;
        SendMailSession session = smtpServer.createSession();
        String date=new Date().toString();
        try {
            Email email = Email.create()
                    .from(SlagoEmailData.username)
                    .to(user)
                    .subject(subject)
                    .htmlMessage(
                            "<div style=\"width:100%;display:flex;justifyContent:center;flex-wrap:wrap;background-color:#24292f;padding:10px;\">" +
                                    "<div style=\"width:100%;color:#fafafa;\"><h1>来自Slago Space的邮箱验证</h1></div>"+
                                    "<div style=\"width:100%;color:#fafafa;\"><h3>"+"尊敬的用户:您好!"+"</h3></div>"+
                                    "<div style=\"width:100%;color:#fafafa;\"><h3>"+"您正在进行-"+process+"-操作</h3></div>"+
                                    "<div style=\"width:100%;color:#fafafa;\"><h3>验证码为:"+message+"</h3></div>"+
                                    "<div style=\"width:100%;color:#fafafa;\"><span>注意:注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n" +
                                    "                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)</span></div>"+
                                    "<div style=\"width:100%;color:#fafafa;\"><span>"+
                                    "<p>此为系统邮件，请勿回复<br>\n" +
                                    "请保管好您的邮箱，避免账号被他人盗用\n" +
                                    "</p>\n" +
                                    "<p>Slago Space开发团队</p>\n" +
                                    "————————————————\n" +"</span></div>"+
                                    "<div style=\"width:100%;color:#fafafa;\"><span>Date:"+date+"</span></div>"+
                                    "</div>","utf-8");
            session.open();
            session.sendMail(email);
            back=true;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
            return back;
        }
    }
}
