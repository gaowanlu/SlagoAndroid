package me.response;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
/*
* 随机生成验证码,并以字节方式返回给客户端
* */
//@WebServlet("/response_img")
public class img extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int width=100;
        int height=50;
        //1、创建对象，在内存中图片
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //2、美化图片
        Graphics g=image.getGraphics();//创建画笔对象
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);
        //画边框
        g.setColor(Color.BLUE);
        g.drawRect(0,0,width-1,height-1);
        //写验证码
        String str="ZXCVBNMASDFGHJKLQWERTYUIOP1234567890zxcvbnmasdfghjklqwertyuiop";
        g.setFont(new Font("宋体",0,20));
        for(int i=0;i<4;i++) {
            //创建随机角标
            Random ran = new Random();
            int index = ran.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index);
            g.drawString(ch+"", (width/5*i)+20, height/2);
        }
        //画干扰线
        g.setColor(Color.red);
        Random ran = new Random();
        for(int i=0;i<10;i++){
            int x1=ran.nextInt(width);
            int x2=ran.nextInt(width);
            int y1=ran.nextInt(height);
            int y2=ran.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }
        //将图片给客户端
        ImageIO.write(image,"jpg",resp.getOutputStream());

        System.out.println("Cookie "+req.getHeader("cookie"));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException{
        this.doGet(req,resp);
    }
}
