package SlagoService.UserData.Userprofile;

import utils.CookieTool;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserProfileDB {
    public static boolean updateProfile(String id,String profile){
        PreparedStatement stmt=null;
        Connection conn=null;
        int result=-1;
        try {
            conn = JDBCUtils.getConnection();
            String sql="UPDATE user SET profile=? WHERE id=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,profile);
            stmt.setString(2,id);
            result=stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,stmt,conn);
            if(result==1){return true;}else{return false;}
        }
    }
    public static String getProfile(String id){
        if(id==null){return null;}
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        String result="";
        try {
            conn = JDBCUtils.getConnection();
            String sql="SELECT profile FROM user WHERE id=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,id);
            set=stmt.executeQuery();
            while(set.next()){
                result+=set.getString("profile");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set,stmt,conn);
            return result;
        }
    }
}
