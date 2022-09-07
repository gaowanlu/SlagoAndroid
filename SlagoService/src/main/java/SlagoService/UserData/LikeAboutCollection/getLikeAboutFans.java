package SlagoService.UserData.LikeAboutCollection;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*返回用户的关注收藏喜欢数量*/
@WebServlet("/apis/getLikeAboutFans")
public class getLikeAboutFans extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getLikeAboutFansData data=new getLikeAboutFansData(req);
        //设置编码
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        resp.getWriter().write(data.getResult());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }
    class getLikeAboutFansData{
        public String id;
        public getLikeAboutFansData(HttpServletRequest req){
            id=req.getParameter("id");
        }
        public String getResult(){
            return getLikeAboutFansDB.getResult(id);
        }
    }
}
