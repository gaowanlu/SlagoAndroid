package me.servletcontext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//@WebServlet("/getfile")
public class FileDownLoad extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //通过HttpServlet获取
        ServletContext context=this.getServletContext();
        //1、获取请求参数，filename
        String filename=req.getParameter("filename");
        //2、使用字节输入流加载文件到内存
        String realPath=context.getRealPath("/ROOT/img/"+filename);
        //3、用字节流关联
        FileInputStream fis=new FileInputStream(realPath);
        //4、设置响应头
        String mimeType=context.getMimeType(filename);//获取文件的mime类型
        resp.setHeader("content-disposition","attachment;filename="+filename);
        //5、将输入流的数据写出到输出流中
        ServletOutputStream sos=resp.getOutputStream();
        byte[] buff=new byte[1024*8];
        int len=0;
        while((len=fis.read(buff))!=-1){
            sos.write(buff,0,len);
        }
        fis.close();
        /*关于中文文件名称问题
        *1、获取客户端使用的浏览器版本信息
        *2、根据不同的版本信息，设置filename的编码不同
        *
        *
        *
        *
        *
        *
        * */
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }
    public static String getFileName(String agent,String filename) throws UnsupportedEncodingException {
        if(agent.contains("MSIE")){
            //IE浏览器
            filename= URLEncoder.encode(filename,"utf-8");
            filename=filename.replace("+"," ");
        }else if(agent.contains("Firefox")){
            //火狐
            //BASE64Encoder base64Encoder=new BASE64Encoder();

        }
        return filename;
        //不全，用时再说
    }
}
