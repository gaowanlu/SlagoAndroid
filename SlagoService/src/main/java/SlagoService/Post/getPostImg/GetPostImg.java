package SlagoService.Post.getPostImg;


import SlagoService.Post.getPostImg.tool.ResizeImg;
import utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

///apis/getPostImg/id=23
@WebServlet("/apis/getPostImg")
public class GetPostImg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id=null;
        id=req.getParameter("id");
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="SELECT img,imgsize,imgtype,postid FROM img WHERE id=?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,id);
            set=statement.executeQuery();
            if(set.next()){
                //响应图片
                String imgType=set.getString("imgtype");
                resp.setContentType(imgType);//设置输出的文件类型
//                resp.setContentLength((int)set.getLong("imgsize"));//设置输出的文件长度
                long fileSize=(int)set.getLong("imgsize");
                InputStream ins = null;
//                OutputStream ous = null;
                ins = set.getBinaryStream("img");
//                ous = resp.getOutputStream();
//                byte [] b = new byte[1024];
//                int len = 0;
//                while((len = ins.read(b)) !=-1){
//                    ous.write(b,0,len);
//                }
                try {
                    ResizeImg.zoomImage(resp,ins,512,fileSize,imgType);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
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
