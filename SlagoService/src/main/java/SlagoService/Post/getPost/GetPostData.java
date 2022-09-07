package SlagoService.Post.getPost;

import utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//获取帖子内容
//http://127.0.0.1:12344/apis/getPostData?postid=10
@WebServlet("/apis/getPostData")
public class GetPostData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String postid=null;
        postid=req.getParameter("postid");
        if(null==postid){
            pw.write("{\"status\":300}");
            return;
        }
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        String returnJSON="{\"status\":300}";
        try {
            connection=JDBCUtils.getConnection();
            String sql="SELECT userid,posttext,postdate FROM post WHERE id=?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,postid);
            set=statement.executeQuery();
            if(set.next()){
                returnJSON="{" +
                        "\"status\":200,"+
                        "\"userid\":\""+set.getString("userid")+"\"," +
                        "\"posttext\":\""+set.getString("posttext")+"\"," +
                        "\"postdate\":\""+set.getString("postdate")+"\",";
                //查询关于这篇帖子的全部图片的id
                sql="SELECT id FROM img WHERE postid=? ORDER BY id;";
                statement=connection.prepareStatement(sql);
                statement.setString(1,postid);
                set=statement.executeQuery();
                returnJSON+="\"imgs\":[";
                List<String> imgs=new ArrayList<>();
                while(set.next()){
                    imgs.add(set.getString("id"));
                }
                for(int i=0;i<imgs.size();i++){
                    returnJSON+="\""+imgs.get(i)+"\"";
                    if(i!=imgs.size()-1){
                        returnJSON+=",";
                    }
                }
                returnJSON+="]}";
                //将JSON返回
                pw.write(returnJSON);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(set,statement,connection);
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

