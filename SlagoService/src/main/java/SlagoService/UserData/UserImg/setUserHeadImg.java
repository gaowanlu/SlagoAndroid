package SlagoService.UserData.UserImg;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.CookieTool;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*功能:用户更新头像
* */
@WebServlet("/apis/setHeadImg")
public class setUserHeadImg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*创建解析器工厂*/
        DiskFileItemFactory factory=new DiskFileItemFactory();
        /*文件上传解析器*/
        ServletFileUpload upload=new ServletFileUpload(factory);
        //判断enctype属性是否为multipart/form-data
        boolean isMultipart=ServletFileUpload.isMultipartContent(req);
        if(isMultipart==false){//直接结束,不再处理
            return;
        }
        String result="{\"status\":\"300\",\"result\":\"false\"}";
        try {
            //解析请求，将表单中每个输入项封装成一个FileItem对象
            List<FileItem> fileItems = upload.parseRequest(req);
            // 迭代表单数据
            for (FileItem fileItem : fileItems) {
                //判断输入的类型是 普通输入项 还是文件
                if (fileItem.isFormField()) {//输入类型为普通字符
                    //普通输入项 ,得到input中的name属性的值,fileItem.getFieldName()
                    ////得到输入项中的值,fileItem.getString("UTF-8"),"UTF-8"防止中文乱码
                    //System.out.println(fileItem.getFieldName()+"\t"+fileItem.getString("UTF-8"));
                } else {
                    //上传的是文件，获得文件上传字段中的文件名
                    //注意IE或FireFox中获取的文件名是不一样的，IE中是绝对路径，FireFox中只是文件名
                    //String fileName = fileItem.getName();
                    //System.out.println(fileName);
                    //Substring是字符串截取，返回值是一个截取后的字符串
                    //lastIndexOf(".")是从右向左查,获取.之后的字符串
                    //String ext = fileName.substring(fileName.lastIndexOf("."));
                    //UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法, UUID的唯一缺陷在于生成的结果串会比较长
                    //String name = UUID.randomUUID()+ext;
                    //将FileItem对象中保存的主体内容保存到某个指定的文件中
                    if(fileItem.getFieldName().equals("headImg")){
                        if(UserHeadImgDB.updateHeadImg(CookieTool.getCookie(req,"id"),fileItem)==true){//送入数据库
                            result="{\"status\":\"200\",\"result\":\"true\"}";
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
        } catch (Exception e) {
        }finally {
            /*写入上传结果*/
            //设置编码
            resp.setCharacterEncoding("utf-8");
            resp.setHeader("content-type","text/json;charset=utf-8");
            PrintWriter pw=resp.getWriter();
            pw.write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            this.doGet(req,resp);
        }catch (Exception e){

        }
    }

}
