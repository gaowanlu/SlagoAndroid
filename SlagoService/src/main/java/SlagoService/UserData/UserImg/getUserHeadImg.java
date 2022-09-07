package SlagoService.UserData.UserImg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 返回用户请求的用户的头像
* */
@WebServlet("/apis/getUserHeadImg")
public class getUserHeadImg  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getUserHeadImgData reqdata=new getUserHeadImgData(req);
        reqdata.sendImg(resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }

    class getUserHeadImgData{
        public String id;
        public getUserHeadImgData(HttpServletRequest req){
            id=req.getParameter("id");
        }
        public void sendImg(HttpServletResponse resp){
            //获得数据库中的二进制数据，并以图片的形式给resp送入
            UserHeadImgDB.sendImg(id,resp);
        }
    }
}
