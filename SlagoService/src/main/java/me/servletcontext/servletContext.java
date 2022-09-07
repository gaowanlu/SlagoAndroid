package me.servletcontext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
* ServletContext内容学习
* */
//@WebServlet("/learnServletContext")
public class servletContext extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //通过request对象获取
        ServletContext context1=req.getServletContext();
        //通过HttpServlet获取
        ServletContext context2=this.getServletContext();
        System.out.println(context1==context2);//true
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
ServletContext对象
概念:代表整个web应用，可以和程序的容器(服务器)来通信
获取:
    通过request对象获取
        request.getServletContext()
    通过HttpServlet获取
        this.getServletContext()
功能:
    1、获取MIME类型
        * MIME类型:在互联网通信中定义的一种文件数据类型
            *格式：大类型/小类型 text/html  image/jpeg
        * 获取
            在web.xml中有详细内容
            String [ServletContextObj].getMinmeType(String file)
            例如("a.jpg") return "image/jpeg"
    2、域对象：共享数据
        1、setAttribute(String name,Object value)
        2、getAttribute(String name)
        3、removeAttribute(String name)
        * ServletContext对象范围:所有用户所有请求的数据
    3、获取文件的真实(服务器)路径
        * String ServletContext.getRealPath(String )
        例子:
        String realPath=context.getRealPath("/b.txt"); //web目录下资源访问
        String realPath=context.getRealPath("/WEB-INF/b.txt"); //WEB-INF目录下资源访问
        String realPath=context.getRealPath("/WEB-INF/classes/b.txt"); //src目录下资源访问 或者用class loader
        File file=new File(realPath)

*/
