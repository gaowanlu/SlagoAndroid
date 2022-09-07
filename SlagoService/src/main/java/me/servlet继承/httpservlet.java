package me.servlet继承;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
/*
*GenericServlet将servlet接口中其他的方法做了默认空实现，只将service()方法抽象
*
* */
//class genericservlet extends GenericServlet {
//    @Override
//    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
//
//    }
//}

/*
* HttpServlet：对http协议的一种封装，简化操作
* HttpServletRequest req学习
* */
//@WebServlet("/http")
public class httpservlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方式
        System.out.println(req.getMethod());
        //获取虚拟目录
        System.out.println(req.getContextPath());
        //获取get方式请求参数
        System.out.println(req.getQueryString());
        //获取请求URI
        System.out.println(req.getRequestURI());
        //获取版本及协议
        System.out.println(req.getProtocol());
        //获取客户机IP
        System.out.println(req.getRemoteAddr());

        //获取请求头数据
        Enumeration<String>headerNames=req.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            String value=req.getHeader(name);
            System.out.println(name+"--"+value);
        }
        /*
        String getParameter(String name):
        根据参数名称获取参数值
        String[] getParameterValues(String name):
        根据参数名称获取参数值的数组
        Enumeration<String>getParameterNames():
        获取所有请求的参数名称
        Map<String,String[]>getParameterMap():
        获取所有参数的map集合
        */
        System.out.println(req.getParameter("username"));
        System.out.println(req.getParameter("password"));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        BufferedReader getReader() 获取字符输入流
        ServletInputStream getInputStream() 获取字节输入流
        */
        BufferedReader br=req.getReader();
        String line;
        while((line=br.readLine())!=null){
            System.out.println(line);
        }
        /*
        中文乱码问题:
            *get方式：tomcat8以上已解决
            *post方式：会乱码
                * 解决：
                在获取参数前，设置request.setCharacterEncoding("utf-8")
        */
    }
}
/*请求转发
* 一种在服务器内部的资源跳转方式
*Servlet之间相互调用
*步骤
* 1 通过request对象获取请求转发器对象：
*   RequestDispatcher getRequestDispatcher(String path)
* 2 使用RequestDispatcher对象来进行转发
*   forward(ServletRequest request,ServletResponse response)
*   request.getRequestDispatcher("other").forward(request,response)
* 特点:
*  1、浏览器地址栏路径不发生变化
*  2、只能转发到当前服务器内部资源中
* */
/*
* http
* 请求消息数据格式
* 请求行
* 请求头
* 请求空行
* 请求体
* GET:请求参数在URL后 url长度有限制
* POST:请求参数在请求体  url长度无限制
*
* 请求头
* User-Agent:浏览器信息
* Accept:可响应格式
* Accept-Encoding：可支持的压缩格式
* Aceept-Language:可接受语言
* Referer: 我从哪里来
* Connection:keep-alive //发起请求者，一直保持连接
* Upgrade-Insecure-Requests:1
* */

/*
共享数据
域对象：一个对象作用范围的对象，可以在范围内共享数据
request域 代表一次请求的范围，一般用于请求转发的多个资源中共享数据
 void setAttribute(String name,Object obj) 存储数据
 Object getAttritude(Stirng name) 通过键获取值
 void removeAttribute(String name)通过键移除键值对
* */

/*ServletContext getServletContext()
*
* */