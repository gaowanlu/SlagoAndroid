package SlagoService.UserDataInit.Login;

import utils.CookieTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


class RequestData{
    public String cookie;//cookie
    public String id;//用户名
    public RequestData(HttpServletRequest req){
        cookie= CookieTool.getCookie(req,"SlagoSession");
        id= CookieTool.getCookie(req,"id");
    }
}

//访问数据库根据RequestData的id获取响应的id 密码 cookie
class UserData{
    public String cookie;
    public String id;
    public UserData(RequestData reqdata){
        //检索数据库中是否有id这个用户
        String[] findid=LoginDB.findId(reqdata.id);
        id=findid[0];
        cookie=findid[2];
    }
    public boolean equalRequestData(RequestData reqdata){
        boolean[] judge={false,false};
        if(cookie!=null&&reqdata.cookie!=null&&cookie.equals(reqdata.cookie)){
            judge[0]=true;
        }
        if(id!=null&&reqdata.id!=null&&id.equals(reqdata.id)){
            judge[1]=true;
        }
        return judge[0]&&judge[1];
    }
}


/*
* 功能模块：身份验证，验证客户端的cookie
* name              value
* id                用户id
* SlagoSession      125char
* */
@WebServlet("/SlagoService_Authentication")
public class Authentication extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //处理request 提取数据并封装为对象
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        RequestData reqData=new RequestData(req);
        UserData findData=new UserData(reqData);
        if(findData.equalRequestData(reqData)){//身份验证成功
            pw.write("{\"status\":\"200\",\"result\":\"true\"}");
        }else{//身份验证失败
            pw.write("{\"status\":\"404\",\"result\":\"false\"}");
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
