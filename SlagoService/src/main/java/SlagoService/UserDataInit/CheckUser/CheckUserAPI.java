package SlagoService.UserDataInit.CheckUser;

import SlagoService.UserDataInit.Register.AddNewUser.AddNewUser;
import SlagoService.UserDataInit.Register.AddNewUser.AddNewUserInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//* 请求地址 `/CheckUser`
//        * 请求格式: ?email=$  |  name=$  |  id=$  任意组合
//        * 返回格式:
//        * 成功:{"status":200,"result":"true|false"}
@WebServlet("/CheckUser")
public class CheckUserAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try{ this.doPost(req,resp); }catch (Exception e){ }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //处理request 提取数据并封装为对象
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String email=req.getParameter("email");
        String name=req.getParameter("name");
        String id=req.getParameter("id");
        boolean back=false;
        if(checkParameter(email,name,id)){
            pw.write(GETJSON("200",back));return;
        }
        CheckUserInterface checkUser=new CheckUser();
        back=checkUser.checkname(name)||checkUser.checkId(id)||checkUser.checkEmail(email);
        pw.write(GETJSON("200",back));
    }
    private String GETJSON(String status,boolean result){
        return String.format("{\"status\":%s,\"result\":%s}",status,Boolean.toString(result));
    }
    private boolean checkParameter(String email,String name,String id){
        return email==null&&name==null&&id==null;
    }
}
