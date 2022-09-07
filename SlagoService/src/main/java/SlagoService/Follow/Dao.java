package SlagoService.Follow;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dao implements DaoAPI{
    @Override
    public  String following(String myid, String other) {
        if(myid==null||other==null){return "false";}
        if(myid.equals(other)){return "true";}
        String result="false";
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
            //检查是否已经存在
            String sql="SELECT userid,followed FROM follow WHERE userid=? and followed=?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,myid);
            statement.setString(2,other);
            set=statement.executeQuery();
            if(!(set.next())){//没有关注过了
                //进行关注
                sql="INSERT INTO follow VALUES (?,?);";
                statement=connection.prepareStatement(sql);
                statement.setString(1,myid);
                statement.setString(2,other);
                int line=statement.executeUpdate();
                if(1==line){
                    result="true";
                }
            }else{//已经关注过了
                result="true";
            }
        }finally {
            JDBCUtils.close(set,statement,connection);
            return result;
        }
    }

    @Override
    public String unFollow(String myid, String other) {
        if(myid==null||other==null){return "false";}
        if(myid.equals(other)){return "false";}
        String result="false";
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection= JDBCUtils.getConnection();
            //删除一条follow数据
            String sql="DELETE FROM follow WHERE userid = ? and followed = ?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,myid);
            statement.setString(2,other);
            int line=statement.executeUpdate();
            if(1==line){
                result="true";
            }
        }finally {
            JDBCUtils.close(statement,connection);
            return result;
        }
    }

    /*
    * myid:用户id
    * page:第几页
    * pagesize:每页有几个数据
    * */
    @Override
    public List<String> getFansLists(String myid, String page, String pagesize) {
        String sql="SELECT userid FROM follow WHERE followed=? limit ?,?;";
        return common(myid,page,pagesize,sql);
    }
    public static  List<String>  common(String myid, String page, String pagesize,String sql){
        if(myid==null||page==null||pagesize==null) return new ArrayList<>();
        List<String> list=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try{
            connection= JDBCUtils.getConnection();
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
                list.add(set.getString(1));
            }
        }finally {
            JDBCUtils.close(set,statement,connection);
            return list;
        }
    }
    @Override
    public List<String> getAboutLists(String myid, String page, String pagesize) {
        String sql="SELECT followed FROM follow WHERE userid=? limit ?,?;";
        return common(myid,page,pagesize,sql);
    }
}
