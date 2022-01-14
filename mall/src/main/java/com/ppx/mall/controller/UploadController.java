package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.ppx.mall.bean.User;
import com.ppx.mall.service.UserService;
import com.ppx.mall.util.ErrorResponse;
import com.ppx.mall.util.ResponseUtil;
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
    public ResponseUtil saveImg2(MultipartFile avatarFile, String account){
        if (StrUtil.isEmpty(account)) {
            return new ErrorResponse("账号为空");
        }
        account=Base64.decodeStr(account);
        try{
            if(avatarFile==null){
                return new ErrorResponse("file is null");
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
            String avatarSrc="http://ppxtest.xyz:80/imgServer/image/"+account+".jpg";

            if(StrUtil.isEmpty(oldImg)){//如果该账号没有存储过头像 头像路径一定为空
                //直接更新图片
                user.setAvatarSrc(avatarSrc);
                int count=userService.setUser(user);//更新头像
                if(count==1){//更新成功
                    SuccessResponse s=new SuccessResponse();
                    s.addMessage("avatarSrc",avatarSrc);
                    return s;
                }
            }else{
                //不为空就要删除图片
                String imgFileName=oldImg.substring(38);//截取出图片的文件名
                //删除原来存的图片
                File file=new File(path+imgFileName);
                boolean result=file.delete(); //正常情况下result==true
                System.out.println("删除图片"+result);

                user.setAvatarSrc(avatarSrc);
                int count=userService.setUser(user);//更新头像
                if(count==1){//更新成功
                    SuccessResponse s=new SuccessResponse();
                    s.addMessage("avatarSrc",avatarSrc);
                    return s;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ErrorResponse("上传头像错误");
    }
}
