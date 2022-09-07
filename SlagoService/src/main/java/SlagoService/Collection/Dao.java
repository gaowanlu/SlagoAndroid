package SlagoService.Collection;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dao implements DaoAPI{
    @Override
    public String collectionPost(String myid, String postid) {
        if(myid==null||postid==null){return "false";}
        String result="false";
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            String sql="SELECT userid,postid FROM collection WHERE userid=? and postid=?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,myid);
            statement.setString(2,postid);
            set=statement.executeQuery();
            if(!(set.next())){
                sql="INSERT INTO collection VALUES (?,?);";
                statement=connection.prepareStatement(sql);
                statement.setString(1,myid);
                statement.setString(2,postid);
                int line=statement.executeUpdate();
                if(1==line){
                    result="true";
                }
            }else{
                result="true";
            }
        }finally {
            JDBCUtils.close(set,statement,connection);
            return result;
        }
    }

    @Override
    public String uncollectionPost(String myid, String postid) {
        if(myid==null||postid==null){return "false";}
        String result="false";
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection= JDBCUtils.getConnection();
            String sql="DELETE FROM collection WHERE userid = ? and postid = ?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,myid);
            statement.setString(2,postid);
            int line=statement.executeUpdate();
            if(1==line){
                result="true";
            }
        }finally {
            JDBCUtils.close(statement,connection);
            return result;
        }
    }

    @Override
    public List<String> getCollectionLists(String myid, String page, String pagesize) {
        if(myid==null||page==null||pagesize==null) return new ArrayList<>();
        List<String> list=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            String sql="SELECT postid FROM collection WHERE userid=? limit ?,?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,myid);
            int start=(Integer.valueOf(page)-1)*Integer.valueOf(pagesize);
            if(start<0){
                statement.setInt(2,0);
            }else
                statement.setInt(2,start);
            statement.setInt(3,Integer.valueOf(pagesize));
            set=statement.executeQuery();
            while(set.next()){
                list.add(set.getString("postid"));
            }
        }finally {
            JDBCUtils.close(set,statement,connection);
            return list;
        }
    }
}
