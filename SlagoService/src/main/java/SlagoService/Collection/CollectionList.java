package SlagoService.Collection;


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

@WebServlet("/apis/collectionlist")
public class CollectionList extends HttpServlet {
    private static  DaoAPI daoAPI = new Dao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        List<String> querylist=new ArrayList<>();
        String result=CreateResult(querylist,"200");
        try {
            String myid = CookieTool.getCookie(req, "id");
            String page = req.getParameter("page");
            String pagesize=req.getParameter("pagesize");
            querylist=daoAPI.getCollectionLists(myid,page,pagesize);
            result=CreateResult(querylist,"200");
        }finally {
            pw.write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{this.doGet(req,resp); }catch (Exception e){ }
    }

    public static String CreateResult(List<String> result, String status){
        String returnResult="{"+
                "\"status\":200,";
        returnResult+="\"list\":[";
        for(int i=0;i<result.size();i++){
            returnResult+="\""+result.get(i)+"\"";
            if(i!=result.size()-1){
                returnResult+=",";
            }
        }
        returnResult+="]}";
        return returnResult;
    }
}
