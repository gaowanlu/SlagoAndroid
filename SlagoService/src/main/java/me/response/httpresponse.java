package me.response;

import me.jdbc.JdbcDemo1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
* HttpServletResponse resp学习
* */
//@WebServlet("/response_httpResponse")
public class httpresponse extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("httpResponseGET");
        /*
        中文乱码问题：获取流对象之前，设置流的默认编码，ISO-8859-1 设置为GBK
        */
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        //1、获取字符输出流
        PrintWriter pw=resp.getWriter();
        //2、输出数据：将数据库lib中的订单表输出
        pw.write(JdbcDemo1.testPreparedStatement());
        //获取字节输出流
        //ServletOutputStream sos=resp.getOutputStream();
        //输出数据
        //sos.write("你好".getBytes("utf-8"));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }
}
/*
HTTP/1.1 200
Content-Type: text/html;charset=UTF-8
Content-Length: 0
Date: Tue, 22 Jun 2021 13:08:12 GMT
Keep-Alive: timeout=20
Connection: keep-alive
1、相应行
协议/版本 响应状态码
1XX 服务器接受客户端消息，但没有接收完毕，等待一段时间后，发送1XX多状态
2XX 成功。代表 200
3XX 重定向。代表 302（重定向） 304（访问缓存）
4XX 客户端错误 404没有对应资源 405没有响应doget或dopost
5XX 服务器内部错误

2、响应头
    1、Content-Type:服务器告诉客户端本次响应数据格式以及编码格式
    2、Content-disposition:
    服务器告诉客户端以什么格式打开响应体数据
        默认:in-line 在当前页面打开
        attachment;filename=xxx:以附件形式打开响应体。文件下载

3、响应空行
4、响应体：传输的数据
*/


/*
Response对象
功能:设置响应消息
    设置响应行
        设置状态码 setStatus(int sc)
    设置响应头
        setHeader(String name,String value)
    设置响应体
        获取输出流
        字节输出流:PrintWriter getWriter()
        字节输出流:ServletOutputStream getOutputStream()
        使用输出流，将数据输出到客户端
重定向实例
    response.setStatus(302)
    response.setHeader("location","/day")
重定向 redirect
转发   forward
*/
