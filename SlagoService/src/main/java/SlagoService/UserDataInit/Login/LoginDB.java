package SlagoService.UserDataInit.Login;



import utils.JDBCUtils;

import java.sql.*;

public  class LoginDB {
    public static String[] findId(String id){
        String[] returnData=new String[3];
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        try {
            //3、获取数据库连接对象
            conn = JDBCUtils.getConnection();
            //4、定义sql语句
            String sql = "select * from user WHERE id=?;";
            //5、获取执行sql语句的对象 Statement
            stmt= conn.prepareStatement(sql);
            //6、执行sql
            stmt.setString(1,id);
            set=stmt.executeQuery();
            while (set.next()) {
                returnData[0] = set.getString("id");
                returnData[1] = set.getString("password");
                returnData[2] = set.getString("cookie");
            }
            return returnData;
        }catch (Exception e){

        } finally {
            JDBCUtils.close(set,stmt,conn);
            return returnData;
        }
    }

    public static boolean updateCookie(String id,String password,String newCookie){
        if(id==null||password==null||newCookie==null){
            return false;
        }
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        boolean stat=false;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE  user SET cookie=? WHERE id=? AND password=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,newCookie);
            stmt.setString(2,id);
            stmt.setString(3,password);
            int result=stmt.executeUpdate();
            if(result==1){
                stat=true;
            }
        }catch (Exception e){

        } finally {
            JDBCUtils.close(set,stmt,conn);
            return stat;
        }
    }
}
