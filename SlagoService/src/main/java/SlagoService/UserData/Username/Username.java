package SlagoService.UserData.Username;


import utils.CookieTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

class UsernameData{
    public String id;
    public String cookie;
    public String newname;
    public UsernameData(HttpServletRequest req){
        id= CookieTool.getCookie(req,"id");
        cookie=CookieTool.getCookie(req,"SlagoSession");
        newname=req.getParameter("newname");
    }
    //操纵数据库更新返回0或者1即影响的行数
    public int update(){
        return UsernameDB.updateName(this);
    }
}


@WebServlet("/apis/updateUserName")
public class Username extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UsernameData user=new UsernameData(req);
        int result=user.update();
        //设置编码
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        if(result==0){
            pw.write("{\"status\":300,\"result\":false}");
        }else if(result==1){
            pw.write("{\"status\":200,\"result\":true}");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }
}
