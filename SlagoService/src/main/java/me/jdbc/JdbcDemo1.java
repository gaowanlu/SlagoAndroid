package me.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class JdbcDemo1 {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;
    static {
            URL="jdbc:mariadb://10.60.66.152:3306/lib4";
            USER="root";
            PASSWORD="gaowanlu";
            DRIVER="org.mariadb.jdbc.Driver";
    }
    public static void main(String[]argv){
        System.out.println(testPreparedStatement());
//        Statement stmt=null;
//        Connection conn=null;
//        try {
//            //1、导入驱动jar包
//            //2、注册驱动
//            Class.forName(DRIVER);
//            //3、获取数据库连接对象
//            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            //4、定义sql语句
//            String sql = "select * from 订单;";
//            //5、获取执行sql语句的对象 Statement
//            stmt= conn.createStatement();
//            //6、执行sql
//            ResultSet set = stmt.executeQuery(sql);
//            while (set.next()) {
//                String dingdanbianhao = set.getString("订单编号");
//                String dinghuodate = set.getString("订货日期");
//                String kehubianhao = set.getString("客户编号");
//                System.out.print(dingdanbianhao + " " + dinghuodate + " " + kehubianhao + "\n");
//            }
//        }catch ( ClassNotFoundException e){
//            e.printStackTrace();
//        }catch (SQLException e){
//            e.printStackTrace();
//        } finally {
//            //7、释放资源
//            if(stmt!=null){
//                try{
//                    stmt.close();
//                }catch (SQLException e){
//
//                }
//            }
//            if(conn!=null){
//                try{
//                    conn.close();
//                }catch (SQLException e){
//
//                }
//            }
//        }
    }

    /*test PreparedStatement
    * */
    public static String testPreparedStatement(){
        String returnResult="\nlib4.订单:";
        returnResult+="\n订单编号 订单日期   客户编号\n";
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        try {
            //1、导入驱动jar包
            //2、注册驱动
            Class.forName(DRIVER);
            //3、获取数据库连接对象
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //4、定义sql语句
            String sql = "select * from 订单;";
            //5、获取执行sql语句的对象 Statement
            stmt= conn.prepareStatement(sql);
            //6、执行sql
//            String input_bianhao="";
//            Scanner sc=new Scanner(System.in);
//            input_bianhao=sc.nextLine();
//            stmt.setString(1,input_bianhao);
            set=stmt.executeQuery();
            while (set.next()) {
                String dingdanbianhao = set.getString("订单编号");
                String dinghuodate = set.getString("订货日期");
                String kehubianhao = set.getString("客户编号");
                returnResult+=(dingdanbianhao + " " + dinghuodate + " " + kehubianhao + "\n");
            }
            return returnResult;
        }catch ( ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            //7、释放资源
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (SQLException e){

                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch (SQLException e){

                }
            }
            if(set!=null){
                try{
                    set.close();
                }catch (SQLException e){

                }
            }
            return returnResult;
        }
    }
}
/*
JDBC中常见重要对象
DriverManager:驱动管理对象
    1、注册驱动
    2、获取数据库连接
Connection:数据库连接对象
    1、获取执行sql的对象
        Statement createStatement()
        PreparedStatedment preparedStatement(String sql)
    2、管理事务
        开启事务
        void setAutoCommmit(boolean autocommit) 设置为false关闭自动提交
        提交事务
        commit()
        回滚事务
        rollback()
Statement：(静态sql)执行sql的对象
    1、执行sql
        boolean execute(String sql) 执行任意sql
        int executeUpdate(String sql)
            执行DML(insert、update、delete)语句
            DDL语句 （CREATE ALTER DROP）
            返回值:影响的行数（可以判断是否执行成功）
        ResultSet executeQuery(String sql)
            执行DQL select语句
ResultSet：结果集对象
       .next() 游标向下移动一行
       .getXXX(NAME) XXX:数据类型
PreparedStatement：(动态sql)执行sql的对象
1、SQL注入问题：在拼接SQL时，有一些sql的特殊关键字参与字符串的拼接。会造成安全性问题
    输入用户随便，输入密码 a' or 'a' = 'a
    SQL:select * from user where username = 'fhdsjkf' and password = 'a' or 'a'='a'
2、解决SQL注入问题：使用PreparedStatement对象解决
3、预编译的SQL：参数使用？作为占位符
4、步骤
    1、导入驱动jar包
    2、注册驱动
    3、获取数据库连接对象
    4、定义sql
        注意：使用占位符
    5、获取执行sql语句的对象PreparedStatement
        Connection.prepareStatement(String sql)
    5、给？赋值
        setXXX(参数1,参数2)
        * 参数1 :?的位置编号 从1开始
        * 参数2 :?的值
    7、执行sql，接受返回结果，不需要传递sql
    8、处理结果
    9、释放资源

JDBC控制事务
1、事务：一个包含多个步骤的业务操作，如果这个业务操作被事务管理，则这多个步骤
        要么同时成功，要么同时失败。
2、操作
    1、开启事务
    2、提交事务
    3、回滚事务
3、使用Connection对象来管理事务
    Connection.setAutoCommit()
              .commit()所有sql都执行完提交事务
              .rollback()发生异常时
*/
