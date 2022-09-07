package SlagoService.Follow;

import utils.CookieTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/apis/following")
public class Following extends HttpServlet {
    private static DaoAPI daoAPI = new Dao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String result=CreateResult("false","300");
        try {
            //获取用户id与要关注的用户id
            String myid = CookieTool.getCookie(req, "id");
            String otherid = req.getParameter("otherId");
            result = CreateResult(daoAPI.following(myid, otherid),"200");
        }finally {
            pw.write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{this.doGet(req,resp); }catch (Exception e){ }
    }

    public static String CreateResult(String result,String status){
        if(result==null){result="false";}
        if(status==null){status="300";}
        return "{"+
                "\"result\":"+result+","+
                "\"status\":"+status+
                "}";
    }
}
