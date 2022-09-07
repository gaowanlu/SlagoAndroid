package SlagoService.UserDataInit.Register.AddNewUser;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddNewUser implements AddNewUserInterface{
    private static String INSERTSQL="INSERT INTO user(email,sex,name,password) VALUES(?,?,?,?);";
    @Override
    public boolean add(String useremail, String sex, String name, String password) {
        boolean back=false;
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection= JDBCUtils.getConnection();
            statement=connection.prepareStatement(INSERTSQL);
            statement.setString(1,useremail);
            statement.setString(2,sex);
            statement.setString(3,name);
            statement.setString(4,password);
            if(statement.executeUpdate()==1){back=true;}
        }catch (Exception e){
          e.printStackTrace();
        } finally {
            JDBCUtils.close(statement,connection);
            return back;
        }
    }
}
