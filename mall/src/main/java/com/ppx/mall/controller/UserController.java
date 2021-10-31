package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.User;
import com.ppx.mall.service.UserService;
import com.ppx.mall.util.Constants;
import com.ppx.mall.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public ResponseUtil login(@RequestBody User  user, HttpServletRequest req, HttpServletResponse resp){
        ResponseUtil responseUtil=new ResponseUtil();
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("login req Exception");
        }
        String account=Base64.decodeStr(user.getAccount());
        user.setAccount(account);
        String password=Base64.decodeStr(user.getPassword());
        user.setPassword(password);
        if(account==null||password==null){
            //return new ResponseUtil("error","账号或密码为空");
            responseUtil.setStatus("error");
            responseUtil.addMessage("reason","账号或密码为空");
            return responseUtil;
        }
        User resultUser= userService.login(account,password);
        if(resultUser==null){
            //return new ResponseUtil("error","用户名或密码错误");
            responseUtil.setStatus("error");
            responseUtil.addMessage("reason","用户名或密码错误");
            return responseUtil;
        }
        //生成凭证
        //JSESSIONID进行base64加密后 拼上md5(JESSION+服务器密钥)
        String jsessionid=Base64.encode(req.getSession().getId());
        String token=DigestUtil.md5Hex(jsessionid+Constants.SERVER_TOKEN);
        Cookie cookie = new Cookie(Constants.SERVER_COOKIE,token);
        // 设置cookie的参数
        cookie.setPath("/");
        // 单位秒 24小时*30
        cookie.setMaxAge(60 * 60 * 24*30);
        // cookie放入相应，返回客户端
        resp.addCookie(cookie);
        responseUtil.setStatus("success");
        //返回之前数据脱敏
        resultUser.setAccount("");
        resultUser.setPassword("");
        JSONObject j=new JSONObject();
        j.put("user",resultUser);
        String userString=Base64.encode(j.toJSONString());
        responseUtil.addMessage("user",userString);
        return responseUtil;
    }

    @ResponseBody
    @RequestMapping("/register")
    public ResponseUtil register(HttpServletRequest req,@RequestBody User user){
        ResponseUtil responseUtil=new ResponseUtil();
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("register req Exception");
        }
        String account=Base64.decodeStr(user.getAccount());
        user.setAccount(account);

        String password=Base64.decodeStr(user.getPassword());
        user.setPassword(password);
        //user.setName(Base64.decodeStr(user.getName().substring(1,user.getName().length()-1)));

        String userName=Base64.decodeStr(user.getUserName());
        user.setUserName(userName);
        if(user.getAccount()==null||user.getPassword()==null||user.getUserName()==null){
            responseUtil.setStatus("error");
            responseUtil.addMessage("reason","数据不完整");
            return responseUtil;
        }
        if(userService.existAccount(user.getAccount())){
            responseUtil.setStatus("error");
            responseUtil.addMessage("reason","账号已存在");
            return responseUtil;
        }
        if(userService.existName(user.getUserName())){
            responseUtil.setStatus("error");
            responseUtil.addMessage("reason","用户名已存在");
            return responseUtil;
        }
        System.out.println("---------"+user+"---------");
        User result=userService.register(user);
        if(result!=null){
            responseUtil.setStatus("success");
            return responseUtil;
        }
        responseUtil.setStatus("error");
        responseUtil.addMessage("reason","未知错误");
        return responseUtil;
    }

    @ResponseBody
    @RequestMapping("/refresh")
    public ResponseUtil refresh(String account){
        ResponseUtil responseUtil=new ResponseUtil();
        account=Base64.decodeStr(account);
        User user=userService.getUser(account);
        if(user==null){
            responseUtil.setStatus("error");
            responseUtil.addMessage("reason","账号不存在");
            return responseUtil;
        }
        user.setPassword("");
        responseUtil.setStatus("success");
        JSONObject j=new JSONObject();
        j.put("user",user);
        String userString=Base64.encode(j.toJSONString());
        responseUtil.addMessage("user",userString);
        return responseUtil;
    }
}
