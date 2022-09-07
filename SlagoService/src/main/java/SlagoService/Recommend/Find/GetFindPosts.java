package SlagoService.Recommend.Find;

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

//* 请求地址 `/apis/getFindPosts`
//        * 请求格式:  ?size=6[0<size<=10]
//        * 返回格式:
//        * 成功:{"status":200,"result":["12","43","65","3"]}
//        * 失败:{"status":300,"result":[]}


@WebServlet("/apis/getFindPosts")
public class GetFindPosts extends HttpServlet {
    private static DaoAPI daoAPI=new Dao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        List<String> list=new ArrayList<>();
        String result=CreateResult(list,"300");
        try {
            String myid = CookieTool.getCookie(req, "id");
            String size = req.getParameter("size");
            result = CreateResult(daoAPI.query(size),"200");
        }finally {
            pw.write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{this.doGet(req,resp); }catch (Exception e){ }
    }

    public static String CreateResult(List<String> result, String status){
        String arrayInner="";
        for(int i=0;i<result.size();i++){
            arrayInner+="\""+result.get(i)+"\"";
            if(i!=result.size()-1){
                arrayInner+=",";
            }
        }
        return String.format("{\"status\":%s,\"result\":[%s]}",status,arrayInner);
    }
}
