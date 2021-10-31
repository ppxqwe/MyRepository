package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import com.ppx.mall.util.ResponseUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


@Controller
public class UploadController {

    /*@RequestMapping("/saveImg1")
    public void saveImg1(MultipartFile file,HttpServletResponse resp) throws IOException {
        //ImageIcon imageIcon=new ImageIcon(new URL("http://ppxtest.xyz:8077/imgServer/image/53b0c2da50272.jpg"));
        //ImageIcon imageIcon=new ImageIcon(file.getBytes());
        if(file==null){
            resp.getWriter().write("img is null");
            return;
        }
        ImageIcon imageIcon=new ImageIcon(file.getBytes());
        BufferedImage image=toBufferedImage(imageIcon.getImage());
        resp.setContentType("image/jpeg");
        // 设置不缓存
        resp.setDateHeader("Expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        // 输出图片
        ServletOutputStream ous =null;
        try {
            ous = resp.getOutputStream();
            ImageIO.write(image, "jpeg", ous);
            ous.flush();
            ous.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @ResponseBody
    @RequestMapping("/saveImg")
    public ResponseUtil saveImg2(MultipartFile avatarFile,String account){
        String name=Base64.decodeStr(account)+".jpg";
        ResponseUtil responseUtil=new ResponseUtil();
        try{
            if(avatarFile==null){
                responseUtil.setStatus("error");
                responseUtil.addMessage("reason","file is null");
                return responseUtil;
            }
            String path="/www/server/tomcat9/webapps/imgServer/image/";
            avatarFile.transferTo(new File(path,name));
            responseUtil.setStatus("success");
            responseUtil.addMessage("avatarSrc","http://ppxtest.xyz:8077/imgServer/image/"+name);
            return responseUtil;
        }catch (Exception e){
            e.printStackTrace();
        }
        responseUtil.setStatus("error");
        responseUtil.addMessage("reason","saveImg error");
        return responseUtil;

        /*resp.setContentType("image/jpeg");
        // 设置不缓存
        resp.setDateHeader("Expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        // 输出图片
        ServletOutputStream ous =null;
        try {
            ous = resp.getOutputStream();
            ous.write(avatarFile.getBytes());
            ous.flush();
            ous.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    //Image对象转化成BufferedImage对象
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.TRANSLUCENT;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }

        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }
}
