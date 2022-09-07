package SlagoService.UserDataInit.Register;

import SlagoService.UserDataInit.Register.AddNewUser.AddNewUser;
import SlagoService.UserDataInit.Register.AddNewUser.AddNewUserInterface;
import SlagoService.UserDataInit.Register.CheckEmail.CheckEmail;
import SlagoService.UserDataInit.Register.CheckEmail.CheckEmailInterface;
import SlagoService.UserDataInit.VerificationCode.VerificationCodeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


// //RegisterNewCount?email=?&sex=?&name=?&password=?&checkCode=?
@WebServlet("/RegisterNewCount")
public class RegsterNewCount extends HttpServlet {
    //验证接口参数,返回原因，合法返回 '' 不合法返回原因
    public static String CheckParameter(String useremail,String sex,String name,String password,String checkCode){
        String back="";
        if(useremail==null||sex==null||name==null||password==null||checkCode==null){
            back+="请求参数存在空白 ";return back;
        }
        //验证邮箱是否已经有账号注册过了
        CheckEmailInterface checkEmail=new CheckEmail();
        if(checkEmail.exist(useremail)){//已经存在
            back+="账号已经存在";
            return back;
        }
        if(!sex.equals("男")&&!sex.equals("女")&&!sex.equals("保密")){
            back+=" 性别参数非法 ";
        }
        if((password.length()<8)){
            back+=" 密码不能小于8位 ";
        }
        //验证码验证
        if(!VerificationCodeDao.right(useremail,checkCode)){
            back+="验证码错误请重新请求发送验证码进行验证 ";
        }
        return back;
    }

    //生成返回JSON格式
    public static String GetJSON(String status,String result,String id){
        return String.format("{\"status\":\"%s\",\"result\":%s,\"id\":\"%s\"}",status,result,id);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //处理request 提取数据并封装为对象
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String useremail=req.getParameter("email");
        String sex=req.getParameter("sex");
        String name=req.getParameter("name");
        String password=req.getParameter("password");
        String checkCode=req.getParameter("checkCode");
        boolean back=false;

        System.out.println(useremail+sex+name+password+checkCode);

        //检查API参数以及验证码
        String checkParameterResult=CheckParameter(useremail,sex,name,password,checkCode);
        if(!checkParameterResult.equals("")){
            pw.write(GetJSON("200","false",checkParameterResult));
            return;
        }
        AddNewUserInterface addNewUserInterface=new AddNewUser();
        back=addNewUserInterface.add(useremail,sex,name,password);
        if(back){
            pw.write(GetJSON("200","true",useremail));
        }else{
            pw.write(GetJSON("200","false",checkParameterResult));
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{ this.doGet(req,resp); }catch (Exception e){ }
    }
}
