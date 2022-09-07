package me.jdbc;
/*
* 数据库连接池
* 存在多个连接对象，需要时提供服务
* */
public class springjdbc {
    public static void main(String[] args) {

    }
}
/*
1、数据库连接池

## 数据库连接池
    概念:一个容器(集合)，存放数据库连接的容器
        当系统初始化后，容器被创建，容器中会申请一些连接对象，当
        用户来访问数据库时，从容器中获取连接对象，用户访问完后
        会将连接对象归还给容器。
    1、c3p0
    2、Druid
* */
