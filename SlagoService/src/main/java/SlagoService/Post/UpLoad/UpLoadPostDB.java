package SlagoService.Post.UpLoad;

import org.apache.commons.fileupload.FileItem;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

public class UpLoadPostDB {
    /*
    * id 用户id
    * content 帖子文字内容
    * imgs 图片FileItem数组 长度为6
    * */
    public static boolean addPost(String id, String content, FileItem[] imgs){
        if(id==null||content==null||imgs==null){return false;}
        //检测图片数量与content是否满足发帖要求
        boolean judge=false;
        for(int i=0;i<6;i++){
            if(imgs[i]!=null){
                judge=true;
                break;
            }
        }
        if(!judge){
            return false;
        }
        //插入一条新帖子
        boolean result=false;
        PreparedStatement stmt=null;
        Connection conn=null;
        try {
            conn = JDBCUtils.getConnection();
            String sql="INSERT INTO post(userid,posttext,postdate) VALUES(?,?,?);";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,id);
            stmt.setString(2,content);
            stmt.setTimestamp(3,new Timestamp(new Date().getTime()));
            int line=stmt.executeUpdate();
            if(line==1){//插入成功
                //获取这个postid
                String postid=null;
                sql="SELECT MAX(id) AS id FROM post WHERE userid=?;";
                stmt=conn.prepareStatement(sql);
                stmt.setString(1,id);
                ResultSet set=stmt.executeQuery();
                while(set.next()){
                    postid=set.getString("id");
                }
                if(postid!=null){//有图片就插入图片
                    for(int i=0;i<imgs.length;i++){
                        if(imgs[i]!=null){
                            //将图片存储到数据库
                            sql="INSERT INTO img(img,imgsize,imgtype,postid) VALUES(?,?,?,?);";
                            stmt= conn.prepareStatement(sql);
                            stmt.setBinaryStream(1,imgs[i].getInputStream());
                            stmt.setString(2,String.valueOf(imgs[i].getSize()));
                            stmt.setString(3,imgs[i].getContentType());
                            stmt.setString(4,postid);
                            stmt.executeUpdate();
                        }
                    }
                    result=true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,stmt,conn);
            return result;
        }
    }
}
