package SlagoService.Post.UpLoad;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.CookieTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//发布帖子
@WebServlet("/apis/uploadpost")
public class UpLoadPost extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/json;charset=utf-8");
        PrintWriter pw=resp.getWriter();
        /*创建解析器工厂*/
        DiskFileItemFactory factory=new DiskFileItemFactory();
        /*文件上传解析器*/
        ServletFileUpload upload=new ServletFileUpload(factory);
        //判断enctype属性是否为multipart/form-data
        boolean isMultipart=ServletFileUpload.isMultipartContent(req);
        if(isMultipart==false){//直接结束,不再处理
            pw.write("{\"status\":300,\"result\":false}");
            return;
        }
        String result="{\"status\":300,\"result\":false}";
        try {
            //解析请求，将表单中每个输入项封装成一个FileItem对象
            List<FileItem> fileItems = upload.parseRequest(req);
            // 迭代表单数据
            String content=null;
            FileItem[] imgs={null,null,null,null,null,null};
            for (FileItem fileItem : fileItems) {
                //判断输入的类型是 普通输入项 还是文件
                if (fileItem.isFormField()) {//输入类型为普通字符
                    if(fileItem.getFieldName().equals("content")==true){
                        content=fileItem.getString("UTF-8");
                    }
                } else {
                    String name=fileItem.getFieldName();
                    for(int j=0;j<6;j++){
                        if(name.equals("img"+Integer.toString(j))){
                            imgs[j]=fileItem;
                        }
                    }
                }
            }
            //判断是否合法
//            if (content!=null&&)
            //将数据送入数据库
            boolean addResult=UpLoadPostDB.addPost(CookieTool.getCookie(req,"id"),content,imgs);
            //判断是否成功
            if(addResult==true){
                result="{\"status\":200,\"result\":true}";
            }
        } catch (FileUploadException e) {

        } catch (Exception e) {
        }finally {//响应
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
