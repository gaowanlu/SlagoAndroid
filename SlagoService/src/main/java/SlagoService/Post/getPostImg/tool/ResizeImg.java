package SlagoService.Post.getPostImg.tool;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class ResizeImg {
    /*
     * 图片缩放,w，h为缩放的目标宽度和高度
     * src为源文件目录，dest为缩放后保存目录
     */
    public static void zoomImage(String src,String dest,int w,int h) throws Exception {

        double wr=0,hr=0;
        File srcFile = new File(src);
        File destFile = new File(dest);

        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板

        wr=w*1.0/bufImg.getWidth();     //获取缩放比例
        hr=h*1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile); //写入缩减后的图片
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*
     * 图片按比率缩放
     * size为文件大小
     */
    public static void zoomImage(HttpServletResponse resp,InputStream ins, Integer size, long fileSize, String imgType) throws Exception {
        //System.out.println("originSize:"+fileSize);
        Double rate = 1.0;
        // 获取长宽缩放比例
        if((size *1024* 1.0) / fileSize<1.0){
            rate=(size *1024* 1.0) / fileSize;
        }
        OutputStream outs=resp.getOutputStream();
        String realType=imgType.split("/")[1];
        BufferedImage bufImg = ImageIO.read(ins);
        Image Itemp = bufImg.getScaledInstance(bufImg.getWidth(), bufImg.getHeight(), bufImg.SCALE_SMOOTH);
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(rate, rate), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,realType, outs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
