package SlagoService.UserDataInit.VerificationCode;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

public class VerificationCodeDao {
    private static String UPDATESQL= "UPDATE verificationcode SET code=?,date=? WHERE email=?;";
    private static String INSERTSQL= "INSERT INTO verificationcode VALUES(?,?,?);";


    //更新验证码
    public static boolean update(String email,String code){
        boolean back=false;
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            statement=connection.prepareStatement(UPDATESQL);
            statement.setString(1,code);
            statement.setTimestamp(2,new Timestamp(new Date().getTime()));
            statement.setString(3,email);
            int line=statement.executeUpdate();
            if(line==1){back=true;}
            else{
                statement=connection.prepareStatement(INSERTSQL);
                statement.setString(1,email);
                statement.setString(2,code);
                statement.setTimestamp(3,new Timestamp(new Date().getTime()));
                line=statement.executeUpdate();
                if(line==1){back=true;}
            }
        }finally {
            JDBCUtils.close(set,statement,connection);
            return back;
        }
    }

    private static String DELETESQL="DELETE FROM verificationcode WHERE email=? AND code=?;";
    private static String DELETESQL_="DELETE FROM verificationcode WHERE email=?;";

    //检查验证码是否正确,遵循只能验证一次原则,检索后即删除
    public static boolean right(String email,String code){
        boolean back=false;
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection= JDBCUtils.getConnection();
            statement=connection.prepareStatement(DELETESQL);
            statement.setString(1,email);
            statement.setString(2,code);
            int line=statement.executeUpdate();
            if(line==1){//验证成功
                back=true;
            }else{//验证失败、无论如何删除验证码
                statement=connection.prepareStatement(DELETESQL_);
                statement.setString(1,email);
                statement.executeUpdate();
            }
        }finally {
            JDBCUtils.close(statement,connection);
            return back;
        }
    }
}
