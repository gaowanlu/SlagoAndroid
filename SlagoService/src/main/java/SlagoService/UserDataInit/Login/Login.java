package SlagoService.UserDataInit.Login;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/*
* 功能根据用户提供的id password
* 来设置新的cookie到客户端，并更新数据库中的cookie
* cookie
* name  value
* id    账号
* SlagoSession cookie
* */
@WebServlet("/SlagoService_Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //处理request 提取数据并封装为对象
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        //身份校验
        RequestData requestData=new RequestData(req);
        String newSession=requestData.updateSlagoSession();
        if(newSession.equals("ERROR")){//更新失败
            pw.write("{\"status\":\"300\"}");
        }else{//更新成功
            //设置新的cookie
            Cookie id=new Cookie("id",requestData.id);
            Cookie session=new Cookie("SlagoSession",newSession);
            //设置有效时间为7天
            id.setMaxAge(86400*7);
            session.setMaxAge(86400*7);
            resp.addCookie(id);
            resp.addCookie(session);
            pw.write("{\"status\":\"200\",\"result\":\"true\"}");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }
    /*根据请求数据做出业务处理
    * */
    class RequestData{
        private String  cookieAllCh="ZXCVBNMASDFGHJKLQWERTYUIOP1234567890zxcvbnmasdfghjklqwertyuiop";
        private String cookieStringCreator(){
            String cookie="";
            Random ran = new Random();
            for(int i=0;i<125;i++){
                int index = ran.nextInt(cookieAllCh.length());
                char ch = cookieAllCh.charAt(index);
                cookie+=ch;
            }
            return cookie;
        }
        public String id;
        public String password;
        public RequestData(HttpServletRequest req){
            id=req.getParameter("id");
            password=req.getParameter("password");
        }
        public String updateSlagoSession(){
            String newCookie=cookieStringCreator();
            //根据账号密码信息更新cookie
            boolean result=LoginDB.updateCookie(id,password,newCookie);
            if(result==true){//身份校验成功且，更新了cookie
                return newCookie;
            }else{//更新失败
                return "ERROR";
            }
        }
    }
}
