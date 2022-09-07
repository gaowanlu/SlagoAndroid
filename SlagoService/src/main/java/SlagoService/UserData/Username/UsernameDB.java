package SlagoService.UserData.Username;


import utils.JDBCUtils;

import java.sql.*;

public class UsernameDB {
    /*更新指定用户的昵称
    * */
    public static int updateName(UsernameData user){
        if(user==null||user.cookie==null||user.id==null||user.newname==null){
            return 0;//参数错误，返回失败
        }
        PreparedStatement stmt=null;
        Connection conn=null;
        int result=0;
        try {
            conn = JDBCUtils.getConnection();
            String sql="UPDATE user SET name=? WHERE id=? AND cookie=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,user.newname);
            stmt.setString(2,user.id);
            stmt.setString(3,user.cookie);
            result=stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,stmt,conn);
            return result;
        }
    }
    /*获得指定用户的昵称
    * */
    public static String getUserName(getUserNamedata user){
        if(user==null||user.id==null){
            return "{\"status\":300}";
        }
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        String result="{\"status\":300,\"name\":\"\"}";
        try {
            conn = JDBCUtils.getConnection();
            String sql="SELECT name FROM user WHERE id=? ;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,user.id);
            set=stmt.executeQuery();
            while(set.next()){
                String name=set.getString("name");
                result="{\"status\":200,\"name\":\""+name+"\"}";
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set,stmt,conn);
            return result;
        }
    }
}
