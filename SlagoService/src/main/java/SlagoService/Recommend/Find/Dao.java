package SlagoService.Recommend.Find;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dao implements DaoAPI{
    @Override
    public List<String> query(String size) {
        List<String> ids=new ArrayList<>();
        if(size==null||size.equals("")){return ids;}
        int sizeInt=0;
        try{sizeInt=Integer.parseInt(size);}catch (Exception e){return ids;}
        if(sizeInt<=0||sizeInt>10){return ids;}
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            String sql="SELECT posts.id from (SELECT * from post order by rand() limit 15) as posts order by posts.postdate limit ?;";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,sizeInt);
            set=statement.executeQuery();
            while(set.next()){
                ids.add(set.getString("id"));
            }
        }finally {
            JDBCUtils.close(set,statement,connection);
            return ids;
        }
    }
}
