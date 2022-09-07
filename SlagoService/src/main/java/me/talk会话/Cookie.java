package me.talk会话;

public class Cookie {

}
/*
会话技术
1、会话：一次会话中包含多次请求和响应
    * 一次会话：浏览器第一次给服务器发送请求，会话建立，直到有一方断开为止
2、功能：在一次会话的范围内的多次请求间，共享数据
3、方式：
    1、客户端会话技术：Cookie
    2、服务端会话技术：Session

Cookie
1、概念:客户端会话技术，将数据保存到客户端
2、快速入门
    * 使用步骤
    1、创建Cookie对象，绑定数据
        * new Cookie(String name,String value)
    2、发送Cookie对象
        * response.addCokie(Cookie cookie)
    3、获取Cookie,拿到数据
        * Cookie[] request.getCookies()
例子:LoginService

3、实现原理
    * 基于响应头set-cookie和请求头cookie实现
4、cookie的细节
    1、一次可以发送多个cookie
        * 创建多个cookie对象，使用response调用多次addCookie方法
    2、cookie在浏览器中保存多长时间
        1、默认情况下，当浏览器关闭后，Cookie数据被销毁
        2、持久化存储
            *CookieObject.setMaxAge(int seconds)
            1、正数：将Cookie数据写到硬盘的文件中，
                    持久化存储，参数为存活的时间
            2、负数:默认值（浏览器关闭，Cookie消失）
            3、零:删除cookie信息
    3、cookie能不能存中文?
        * 在tomcat8之前，cookie中不能直接存储中文数据
        * 在tomcat8之后，cookie支持中文数据
    4、cookie共享
        * 假设在一个tomcat服务器中，部署了多个web项目，能否共享
            * 默认情况cookie不能共享
            IP/hello hello设置的cookie作用域为IP/hello *
            * setPath(String path):设置cookie的获取范围，
            默认情况设置为当前的虚拟目录
            如果要共享，则可以设置为'/'
         * 不同的tomcat服务器间cookie共享问题
            setDomain(String path)
            如果一级域名相同，那么多台服务器间cookie可以共享
            例:
            setDomain(".baidu.com")
     5、Cookie的特点和作用
        1、cookie存储数据在客户端浏览器
        2、浏览器对于单个cookie的大小有限制(4kb),
            对同一个域名下的总cookie个数有限制(30个)
        作用:
        1、cookie一般用于存储少量的不太敏感的数据
        2、在不登录的情况下，完成服务器对客户端的身份识别
*/

/*
JSP:入门 略暂时不用
1、概念

*/

/*
Session：
概念:服务器端会话技术，再一次会话的多次请求间共享数据，将数据保存在服务器端的对象中，HttpSession
快速入门:
1、获取HttpSession对象
HttpSession session=request.getSession();
2、使用HttpSession对象
Object getAttribute(String name)
void setAttribute(String name,Object value)
void removeAttribute(String name)
3、Session的实现依赖于Cookie客户端与服务端都存储cookie内容
4、细节
    1、客户端关闭后，服务器不关闭，两次获取session是否为同一个？不是
    2、客户端不关闭，服务器关闭后，两次获取的session是同一个吗?
        不是同一个，但要确保数据不丢失
        *session的钝化
            在服务器正常关闭之前，将session对象系列化到硬盘上
        *session的活化
            在服务器启动后，将session文件转化为内存中的session对象即可

      3、session什么时候被销毁
        服务器关闭、session对象调用invalidate()、session默认失效时间为30分钟
        选择性配置修改web.xml
        <session-config>
            <session-timeout>30</session-timeout>
        </session-config>
5、session的特点
    session用于存储一次会话的多次请求数据，存在服务器端
    session可以存储任意类型，任意大小的数据
    session与cookie的区别
        session存储数据在服务端，Cookie在客户端
        session没有数据大小限制，Cookie有
        session数据安全，Cookie相对于不安全
*/
