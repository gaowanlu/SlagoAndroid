package SlagoService.Like;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dao implements DaoAPI{
    @Override
    public String likePost(String myid, String postid) {
        if(myid==null||postid==null){return "false";}
        String result="false";
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            //检查是否已经存在
            String sql="SELECT userid,postid FROM likes WHERE userid=? and postid=?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,myid);
            statement.setString(2,postid);
            set=statement.executeQuery();
            if(!(set.next())){//没有点过赞
                //进行点赞
                sql="INSERT INTO likes VALUES (?,?);";
                statement=connection.prepareStatement(sql);
                statement.setString(1,myid);
                statement.setString(2,postid);
                int line=statement.executeUpdate();
                if(1==line){
                    result="true";
                }
            }else{//已经点过赞了
                result="true";
            }
        }finally {
            JDBCUtils.close(set,statement,connection);
            return result;
        }
    }

    @Override
    public String unlikePost(String myid, String postid) {
        if(myid==null||postid==null){return "false";}
        String result="false";
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection= JDBCUtils.getConnection();
            String sql="DELETE FROM likes WHERE userid = ? and postid = ?;";
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
    public List<String> getLikeLists(String myid, String page, String pagesize) {
        if(myid==null||page==null||pagesize==null) return new ArrayList<>();
        List<String> list=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            String sql="SELECT postid FROM likes WHERE userid=? limit ?,?;";
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
