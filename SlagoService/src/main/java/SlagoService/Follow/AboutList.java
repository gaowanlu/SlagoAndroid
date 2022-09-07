package SlagoService.Follow;

import utils.CookieTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/apis/aboutlist")
public class AboutList extends HttpServlet {
    private static DaoAPI daoAPI = new Dao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        List<String> querylist=new ArrayList<>();
        String result=FansList.CreateResult(querylist,"200");
        try {
            String myid = CookieTool.getCookie(req, "id");
            String page = req.getParameter("page");
            String pagesize=req.getParameter("pagesize");
            querylist=daoAPI.getAboutLists(myid,page,pagesize);
            result=FansList.CreateResult(querylist,"200");
        }finally {
            pw.write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{this.doGet(req,resp); }catch (Exception e){ }
    }
}
