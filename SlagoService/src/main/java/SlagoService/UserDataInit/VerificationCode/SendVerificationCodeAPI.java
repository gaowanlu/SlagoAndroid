package SlagoService.UserDataInit.VerificationCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

///SendVerificationCode?email=?
@WebServlet("/SendVerificationCode")
public class SendVerificationCodeAPI extends HttpServlet {
    //生成返回JSON格式
    public static String GetJSON(String status,String result){
        return String.format("{\"status\":\"%s\",\"result\":%s}",status,result);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //处理request 提取数据并封装为对象
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        String useremail=req.getParameter("email");
        SendVerificationCodeInterface sendVerificationCodeInterface=new SendVerificationCode();
        sendVerificationCodeInterface.send(useremail);
        pw.write(GetJSON("200","true"));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{ this.doGet(req,resp); }catch (Exception e){ }
    }
}
