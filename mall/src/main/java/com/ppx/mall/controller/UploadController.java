package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.ppx.mall.bean.User;
import com.ppx.mall.service.UserService;
import com.ppx.mall.util.SuccessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;


@Controller
public class UploadController {

    @Autowired
    UserService userService;
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
    public SuccessResponse saveImg2(MultipartFile avatarFile, String account){
        SuccessResponse responseUtil=new SuccessResponse();
        if (StrUtil.isEmpty(account)) {
            return null;
        }
        account=Base64.decodeStr(account);
        try{
            if(avatarFile==null){
                responseUtil.setStatus("error");
                responseUtil.addMessage("reason","file is null");
                return responseUtil;
            }
            String path="/www/server/tomcat9/webapps/imgServer/image/";
            User user=userService.getUser(account);//拿到user
            String oldImg=user.getAvatarSrc();//拿到原来的图片路径

            //生成UUID
            UUID uuid = UUID.randomUUID();
            String randomId=uuid.toString().replace("-","").toUpperCase();
            //拼接在账号后面 base64
            account=Base64.encode(account+randomId);
            //存储图片
            avatarFile.transferTo(new File(path,account+".jpg"));
            String avatarSrc="http://ppxtest.xyz:8077/imgServer/image/"+account+".jpg";
            if(StrUtil.isEmpty(oldImg)){
                //直接更新图片
                user.setAvatarSrc(avatarSrc);
                int count=userService.setUser(user);//更新头像
                if(count==1){//更新成功
                    responseUtil.setStatus("success");
                    responseUtil.addMessage("avatarSrc",avatarSrc);
                    return responseUtil;
                }
            }
            //不为空就要删除图片
            String imgFileName=oldImg.substring(40);//截取出图片的文件名
            //删除原来存的图片
            File file=new File(path+imgFileName);
            boolean result=file.delete();
            if(result){//删除成功
                user.setAvatarSrc(avatarSrc);
                int count=userService.setUser(user);//更新头像
                if(count==1){//更新成功
                    responseUtil.setStatus("success");
                    responseUtil.addMessage("avatarSrc",avatarSrc);
                    return responseUtil;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        responseUtil.setStatus("error");
        responseUtil.addMessage("reason","saveImg error");
        return responseUtil;
    }
}
