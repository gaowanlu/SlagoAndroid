package SlagoService.UserData.LikeAboutCollection;


import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class getLikeAboutFansDB {
    public static String getResult(String id){
        if(id==null){return "{\"status\":300}";}
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        String likeNum="0";
        String aboutNum="0";
        String collectionNum="0";
        String result="{\"status\":200,\"likeNum\":\"0\",\"aboutNum\":\"0\",\"fansNum\":\"0\"}";
        try {
            conn = JDBCUtils.getConnection();
            String sql="SELECT * FROM user WHERE id=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,id);
            set=stmt.executeQuery();
            while(set.next()){
                likeNum=set.getString("likeNum");
                aboutNum=set.getString("AboutNum");
                collectionNum=set.getString("fansNum");
            }
            result="{\"status\":200,\"likeNum\":\""+likeNum+"\",\"aboutNum\":\""+aboutNum+"\",\"fansNum\":\""+collectionNum+"\"}";
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set,stmt,conn);
            return result;
        }
    }
}
