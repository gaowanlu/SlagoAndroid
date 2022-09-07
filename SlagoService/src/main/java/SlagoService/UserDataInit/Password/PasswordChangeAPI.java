package SlagoService.UserDataInit.Password;


import SlagoService.UserDataInit.VerificationCode.VerificationCodeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//* 请求地址 `/ChangePwd`
//        * 请求格式: ?email=$&new=$&check=$
//        * 返回格式:
//        * 成功:{"status":200,"result":"true"}
//        * 失败:{"status":200,"result":"原因"}
@WebServlet("/ChangePwd")
public class PasswordChangeAPI extends HttpServlet {
    private static String checkParameter(String email,String pwd,String code){
        String back="";
        if(email==null||pwd==null||code==null){
            back+="请求参数不能为空 ";return back;
        }
        if(code.length()!=4){
            back+="验证码格式错误 ";return back;
        }
        if(pwd.length()<8){
            back+="密码不能小于8位 ";return back;
        }
        //检测验证码
        if(!VerificationCodeDao.right(email,code)){
            back+="验证码错误请重新申请发送 ";
        }
        if(back.equals("")){
            back="true";
        }
        return back;
    }

    private String GetJSON(String status,String result){
        return String.format("{\"status\":\"%s\",\"result\":\"%s\"}",status,result);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //处理request 提取数据并封装为对象
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String useremail=req.getParameter("email");
        String pwd=req.getParameter("new");
        String code=req.getParameter("check");
        String parameterResult=checkParameter(useremail,pwd,code);
        if(!parameterResult.equals("true")){//不符合要求
            pw.write(GetJSON("200",parameterResult));return;
        }
        //进行密码的更改数据库更新
        PasswordChangeInterface passwordChangeInterface=new PasswordChange();
        if(passwordChangeInterface.update(useremail,pwd)){
            pw.write(GetJSON("200","true"));
        }else{
            pw.write(GetJSON("200",parameterResult));
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{ this.doGet(req,resp); }catch (Exception e){ }
    }
}
