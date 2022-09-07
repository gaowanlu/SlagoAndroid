package SlagoService.UserData.Userprofile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/apis/getUserProfile")
public class getUserProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*UserSexData
         * */
        UserProfileData userProfileData=new UserProfileData(req);
        String result= UserProfileDB.getProfile(userProfileData.reqId);
        //设置编码
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        if(result!=null){
            pw.write("{\"status\":200,\"result\":\""+result+"\"}");
        }else{
            pw.write("{\"status\":300,\"result\":\"\"}");
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
