package me.Listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
//概念：web的三大组件之一Listener:监听器
//事件监听机制
//    *事件：一件事情
//    *事件源：事件发生的地方
//    *监听器：一个对象
//    *注册监听：将事件、事件源、监听器绑定在一起，在事件源上发生某个事件后，执行监听器代码
//* ServletContextListener:监听ServletContext对象的创建和销毁
//    void contextDestroyed(ServletContextEvent sce) ServletContext对象被销毁之前会调用该方法
//    void contextInitialized(ServletContextEvent sce) ServletContext对象创建后会调用该方法
//步骤：
//定义一个类，实现ServletContextListener接口
//复习方法
//配置
//    web.xml
//    注解

//@WebListener
public class LearnListener implements ServletContextListener {

    /*服务器启动后自动调用*/
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获取ServletContext对象
        ServletContext servletContext=sce.getServletContext();
        ServletContextListener.super.contextInitialized(sce);
    }

    /*服务器关闭后，ServletContext对象被销毁，当服务器正常关闭后该方法被调用*/
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
