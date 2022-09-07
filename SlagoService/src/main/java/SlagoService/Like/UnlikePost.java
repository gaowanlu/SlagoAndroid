package SlagoService.Like;

import SlagoService.Like.Dao;
import SlagoService.Like.DaoAPI;
import utils.CookieTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/apis/unlikePost")
public class UnlikePost extends HttpServlet {
    private static DaoAPI daoAPI = new Dao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String result=CreateResult("false","300");
        try {
            String myid = CookieTool.getCookie(req, "id");
            String postid = req.getParameter("postId");
            result = CreateResult(daoAPI.unlikePost(myid, postid),"200");
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
