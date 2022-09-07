package SlagoService.Post.getUserAllPost;


import utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//获取用户帖子列表，提供查看的用户id与请求的根据日期排序的最后n个帖子[before,last]
//http://127.0.0.1:12344/apis/getUserAllPost?num=12&page=2&userid=1901420313
@WebServlet("/apis/getUserAllPost")
public class GetUserAllPost extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //将JSON返回
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String userid=null;
        Integer num=null;
        Integer page=null;
        String returnJSON="{\"status\":300,\"list\":[]}";
        try{
            num= Integer.valueOf(req.getParameter("num"));
            page=Integer.valueOf(req.getParameter("page"));
            if(null==num||null==page){
                pw.write(returnJSON);
                return;
            }
        }catch (Exception e){
            pw.write(returnJSON);
        }
        //1 2 3 4 5 6 7 8 9 10 // userid=1 num=10 page=1 返回 10 9 8 7 6 5 4 3 2 1
        userid=req.getParameter("userid");
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try {
            connection=JDBCUtils.getConnection();
            List<String> returnList=new ArrayList<>();
            String sql="SELECT * FROM post WHERE userid=? ORDER BY postdate desc limit ?,?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,userid);
            if((page-1)*num<0){
                statement.setInt(2,0);
            }else
                statement.setInt(2,(page-1)*num);
            statement.setInt(3,num);
            set=statement.executeQuery();
            while(set.next()){
                String postid=set.getString("id");
                returnList.add(postid);
            }
            //统计共多少页
            sql="SELECT COUNT(*) from post WHERE userid=?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,userid);
            set=statement.executeQuery();
            set.next();
            long countPostNum=set.getLong(1);
            //返回JSON数据
            returnJSON="{\"status\":200,\"list\":[";
            for(int i=0;i<returnList.size();i++){
                returnJSON+="\""+returnList.get(i)+"\"";
                if(i!=returnList.size()-1){
                    returnJSON+=",";
                }
            }
            returnJSON+="],\"countPostNum\":"+Long.toString(countPostNum)+"}";
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(set,statement,connection);
            pw.write(returnJSON);
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
