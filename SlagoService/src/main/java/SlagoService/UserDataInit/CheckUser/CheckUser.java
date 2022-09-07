package SlagoService.UserDataInit.CheckUser;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckUser implements CheckUserInterface{
    private static String queryTemplate="SELECT id FROM user WHERE %s=?;";
    private static boolean queryUser(String col,String value){
        String sql=String.format(queryTemplate,col);
        boolean back=false;
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            statement=connection.prepareStatement(sql);
            statement.setString(1,value);
            if(statement.executeQuery().next()){back=true;}
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set,statement,connection);
            return back;
        }
    }
    @Override
    public boolean checkId(String id) {
        if(id==null||id.equals("")){return false;}
        return queryUser("id",id);
    }

    @Override
    public boolean checkEmail(String email) {
        if(email==null||email.equals("")){return false;}
        return queryUser("email",email);
    }

    @Override
    public boolean checkname(String name) {
        if(name==null||name.equals("")){return false;}
        return queryUser("name",name);
    }
}
