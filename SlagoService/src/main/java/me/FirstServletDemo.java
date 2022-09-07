package me;

import javax.servlet.*;
import java.io.IOException;
/*
* servlet类为单实例，但是在多个用户访问servlet时可能会引起访问冲突
* 解决方案：尽量不要在servlet中定义属性，避免访问操作冲突，即使定义了也要尽可能避免赋值操作
* */
public class FirstServletDemo implements Servlet {
    static int count=0;

    //初始化方法 在Servlet被创建时，执行，只会执行一次，第一次访问时才会init
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
    }

    /*
    * 外部获得ServletConfig
    * ServletConfig:Servlet的配置对象
    * */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /*
    * 提供服务的方法，每一次serlet被访问 ，执行，执行多次
    * */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("提供服务的方法"+count++);
    }

    /*
    * 获得servlet的信息，版本，作者等，提供servlet信息
    * */
    @Override
    public String getServletInfo() {
        return null;
    }

    //在serlet被杀死时执行
    //只有服务器正常结束运行时，才会调用destroy方法
    @Override
    public void destroy() {

    }
}

/*
* 创建servlet类实现接口 配置web.xml
* */

/*servlet3.0支持注解配置 不用配置web.xml
*
* */
