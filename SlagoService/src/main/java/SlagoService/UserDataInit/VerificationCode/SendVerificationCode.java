package SlagoService.UserDataInit.VerificationCode;

import utils.EmailUtils;
import utils.RandomString;

public class SendVerificationCode implements SendVerificationCodeInterface{
    @Override
    public void send(String email) {
        //发送验证码线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                String verificationCode= RandomString.CreateVerificationCode();
                //查询是否符合发送验证码的要求 距离上次发送的时间 等...
                boolean sendResult= EmailUtils.SendVerificationCode(email,
                        "为Slago Space 注册新用户",verificationCode,"注册账号");
                if(sendResult){//将邮箱与验证码更新到数据库
                    VerificationCodeDao.update(email,verificationCode);
                }
            }
        }
        ).start();
    }
}
