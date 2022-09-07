package SlagoService.UserData.UserImg;

import SlagoService.Post.getPostImg.tool.ResizeImg;
import org.apache.commons.fileupload.FileItem;
import utils.JDBCUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserHeadImgDB {
    public static boolean updateHeadImg(String id, FileItem fileItem){
        if(id==null||fileItem==null){return false;}
        PreparedStatement stmt=null;
        Connection conn=null;
        int result=-1;
        try {
            conn = JDBCUtils.getConnection();
            String sql="UPDATE user SET headImg=?,headImgType=?,headImgSize=? WHERE id=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setBinaryStream(1,fileItem.getInputStream());
            stmt.setString(2,fileItem.getContentType());
            stmt.setString(3,String.valueOf(fileItem.getSize()));
            stmt.setString(4,id);
            result=stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,stmt,conn);
            if(result==1){return true;}else{return false;}
        }
    }
    public static void sendImg(String id,HttpServletResponse resp){
        if(id==null||resp==null){return;}
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        try {
            conn = JDBCUtils.getConnection();
            String sql="SELECT headImg,headImgType,headImgSize FROM user WHERE id=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,id);
            set=stmt.executeQuery();
            while(set.next()){
                resp.setContentType(set.getString("headImgType"));//设置输出的文件类型
                InputStream ins = null;
                OutputStream ous = null;
                ins = set.getBinaryStream("headImg");
                ResizeImg.zoomImage(resp,ins,(int)set.getLong("headImgSize"),50,set.getString("headImgType"));
            }
        }catch (Exception e){
        } finally {
            JDBCUtils.close(set,stmt,conn);
        }
    }
}
