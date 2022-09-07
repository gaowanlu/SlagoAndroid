package SlagoService.UserData.Userprofile;

import SlagoService.UserData.UserSex.UserSexData;
import utils.CookieTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/apis/setUserProfile")
public class setUserProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*session test*/
        /*UserSexData
         * */
        UserProfileData userProfileData=new UserProfileData(req);
        boolean result=userProfileData.updateProfile(CookieTool.getCookie(req,"id"));
        //设置编码
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        if(result==true){
            pw.write("{\"status\":200,\"result\":true}");
        }else{
            pw.write("{\"status\":300,\"result\":false}");
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
