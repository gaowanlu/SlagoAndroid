package me.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Filter内容学习
 * */
public class LearnFilter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

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
Filter:过滤器
web中的过滤器：当访问服务器的资源时，过滤器可以将请求拦截先来，完成一些特殊的功能
过滤器的作用：
一般用于完成通过的操作，登陆验证，统一编码处理，敏感字符过滤。

生命流程：init在服务器开启时执行一次
服务器正常关闭：执行一次destroy
每次拦截：做一次doFilter

过滤器配置详解:
路径配置
具体资源 /index.html
拦截目录 /user/*
后缀名拦截 *.html
拦截所有资源 /*

拦截方式配置：
注解配置：
    设置dispatcherTypes属性
    1、REQUEST 默认值。浏览器直接请求资源
    2、FORWARD 转发访问资源
    3、INCLUDE 包含访问资源
    4、ASYNC 异步访问资源
    5、ERROR 错误跳转资源
web.xml配置

过滤器链（配置多个过滤器）
执行顺序：如果有两个过滤器，过滤器1和过滤器2
1
2
资源执行
2
1
过滤器执行先后顺序
1、注解配置：按照类名的字符串比较规则比较，值小的先执行
Afilter Bfilter Afilter先执行
2、web.xml配置：谁先定义在上面，谁先执行
*/
//拦截地址
//@WebFilter(value="/*",dispatcherTypes={DispatcherType.REQUEST,DispatcherType.FORWARD})
//@WebFilter(value="/*",dispatcherTypes=DispatcherType.REQUEST)
//@WebFilter("/*")
class FilterDemo implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FilterDemo被执行了");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
        //去请求相应servlet
        //请求完毕后继续往下面执行
        System.out.println("FilterDemo回来了");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

