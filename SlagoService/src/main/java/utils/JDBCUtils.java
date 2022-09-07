package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    //1、定义DataSource
    private static DataSource dataSource;
    static {
        //1、导入jar包
        //2、定义配置文件
        //3、加载配置文件
        Properties properties=new Properties();
        InputStream inputStream= JDBCUtils.class.getClassLoader().getResourceAsStream("utils/druid.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4、获取连接池对象
        try {
            dataSource= DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    //释放资源
    public static void close(Statement statement,Connection connection){
        close(null,statement,connection);
    }
    public static void close(ResultSet resultSet,Statement statement,Connection connection){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(connection!=null){
            try{
                connection.close();
            }catch (Exception e){

            }
        }
    }

    //获取连接池
    public static DataSource getDataSource(){
        return dataSource;
    }

}
