package SlagoService.Filter;

import utils.CookieTool;
import utils.JDBCUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Session判断
@WebFilter("/apis/*")
public class Session implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    private static boolean judgeSession(String id,String session){
        if(id==null||session==null){return false;}
        boolean result=false;
        PreparedStatement stmt=null;
        Connection conn=null;
        ResultSet set=null;
        try {
            conn = JDBCUtils.getConnection();
            String sql="SELECT id,cookie FROM user WHERE id=? AND cookie=?;";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1,id);
            stmt.setString(2,session);
            set=stmt.executeQuery();
            if(set.next()){//身份正确
                result=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set,stmt,conn);
            return result;
        }
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        //获取cookie
        Cookie[] cookies=req.getCookies();
        if(cookies!=null) {//有cookie
            String id= CookieTool.getCookie(req,"id");
            String session=CookieTool.getCookie(req,"SlagoSession");
            if(judgeSession(id,session)==true){//身份正确
                //放行
                filterChain.doFilter(servletRequest,servletResponse);
            }else{//身份错误:发送{"sessionStatus":"false"}
                //处理request 提取数据并封装为对象
                resp.setCharacterEncoding("utf-8");
                resp.setHeader("content-type","text/json;charset=utf-8");
                PrintWriter pw=resp.getWriter();
                pw.write("{\"status\":404}");
            }
        }else{//没有cookie,不放行，拦截
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
