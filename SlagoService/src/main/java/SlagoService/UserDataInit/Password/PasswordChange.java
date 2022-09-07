package SlagoService.UserDataInit.Password;

import utils.JDBCUtils;
import utils.RandomString;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PasswordChange implements PasswordChangeInterface{
    public static String UPDATESQL="UPDATE user SET password=? ,cookie=? WHERE email=?;";
    @Override
    public boolean update(String email,String newPwd) {
        boolean back=false;
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection= JDBCUtils.getConnection();
            statement=connection.prepareStatement(UPDATESQL);
            statement.setString(1,newPwd);
            statement.setString(2, RandomString.CreateUserCookie());
            statement.setString(3,email);
            if(statement.executeUpdate()==1){back=true;}
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement,connection);
            return back;
        }
    }
}
