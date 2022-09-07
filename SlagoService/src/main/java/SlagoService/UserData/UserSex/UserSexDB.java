package SlagoService.UserData.UserSex;


import utils.CookieTool;
import utils.JDBCUtils;

import java.sql.*;

public class UserSexDB {
    public static boolean updateSex(String id,String sex){
        PreparedStatement stmt=null;
        Connection conn=null;
        int result=-1;
        try {
            conn = JDBCUtils.getConnection();
            String sql="UPDATE user SET sex=? WHERE id=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,sex);
            stmt.setString(2,id);
            result=stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,stmt,conn);
            if(result==1){return true;}else{return false;}
        }
    }
    public static String getSex(String id){
        if(id==null){return null;}
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        String result="";
        try {
            conn = JDBCUtils.getConnection();
            String sql="SELECT sex FROM user WHERE id=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,id);
            set=stmt.executeQuery();
            while(set.next()){
                result+=set.getString("sex");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set,stmt,conn);
            return result;
        }
    }
}
