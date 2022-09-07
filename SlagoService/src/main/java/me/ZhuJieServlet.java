package me;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
/*
* 使用Servlet注解来映射路由
* */

//@WebServlet(urlPatterns="/demo2") //用注解指定路由
public class ZhuJieServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("demo2");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
