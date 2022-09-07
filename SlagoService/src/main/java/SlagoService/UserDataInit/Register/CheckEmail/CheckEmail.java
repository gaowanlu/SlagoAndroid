package SlagoService.UserDataInit.Register.CheckEmail;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckEmail implements CheckEmailInterface{
    private static String QUERYSQL="SELECT id FROM user WHERE email=?";

    @Override
    public boolean exist(String email) {
        if(email==null||email.equals(""))
            return false;
        //在数据库中查找用户是否已经有使用过这个邮箱
        boolean back=false;
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            statement=connection.prepareStatement(QUERYSQL);
            statement.setString(1,email);
            set=statement.executeQuery();
            if(set.next()){back=true;}
        }finally {
            JDBCUtils.close(set,statement,connection);
            return back;
        }
    }
}
