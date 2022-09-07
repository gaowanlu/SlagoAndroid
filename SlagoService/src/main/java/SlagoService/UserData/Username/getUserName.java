package SlagoService.UserData.Username;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

class getUserNamedata{
    public String id;
    public getUserNamedata(HttpServletRequest req){
        id= req.getParameter("id");
    }
    public String getResult(){
        return UsernameDB.getUserName(this);
    }
}

@WebServlet("/apis/getUserName")
public class getUserName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getUserNamedata user=new getUserNamedata(req);
        //设置编码
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        pw.write(user.getResult());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }
}
