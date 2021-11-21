package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.User;
import com.ppx.mall.util.*;
import com.ppx.mall.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public ResponseUtil login(@RequestBody String params, HttpServletRequest req, HttpServletResponse resp){
        if(StrUtil.isEmpty(params)){
            return new ErrorResponse("参数为空");
        }
        User user=ParseRequestParam.parse(params);
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("login req Exception");
        }
        if(user.getAccount()==null||user.getPassword()==null){
            return new ErrorResponse("账号或密码为空");
        }
        User resultUser= userService.login(user.getAccount(),user.getPassword());
        if(resultUser==null){
            return new ErrorResponse("用户名或密码错误");
        }
        req.getSession().setAttribute("session",resultUser);
        //返回之前数据脱敏
        user=new User(resultUser.getAccount(),
                "",
                resultUser.getUserName(),
                resultUser.getAvatarSrc(),
                resultUser.getAddress(),
                resultUser.getTelephone(),
                resultUser.getName()
                );
        return ParseResponseParam.parse(user);
    }

    @ResponseBody
    @RequestMapping("/register")
    public ResponseUtil register(HttpServletRequest req, @RequestBody String params){
        if(StrUtil.isEmpty(params)){
            return new ErrorResponse("注册册数为空");
        }
        User user=ParseRequestParam.parse(params);
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("register req Exception");
        }

        if(user.getAccount()==null||user.getPassword()==null||user.getUserName()==null){
            return new ErrorResponse("注册数据不完整");
        }
        if(userService.existAccount(user.getAccount())){
           return new ErrorResponse("账号已存在");
        }
        if(userService.existName(user.getUserName())){
           return new ErrorResponse("用户名已存在");
        }
        System.out.println("---------"+user+"---------");
        User result=userService.register(user);
        if(result!=null){
            return new SuccessResponse();
        }
        return new ErrorResponse("注册错误");
    }

    @ResponseBody
    @RequestMapping("/refresh")
    public ResponseUtil refresh(@RequestBody String params, HttpServletRequest req){
        if(StrUtil.isEmpty(params)){
            return new ErrorResponse("刷新参数为空");
        }
        //解析出一个user
        //User user=ParseRequestParam.parse(params);
        JSONObject j=JSONObject.parseObject(params);//将协议体的数据转成json
        params=(String)j.get("params");//根据params的value
        params=Base64.decodeStr(params);//解密value
        j=JSONObject.parseObject(params);//value转成json
        String account=(String)j.get("account");
        account=Base64.decodeStr(account);
        //去服务器找
        User user=userService.getUser(account);
        if(user==null){
           return new ErrorResponse("账号不存在");
        }
        User sessionUser=(User)req.getSession().getAttribute("session");
        if(sessionUser!=null&&sessionUser.getUserName().equals(user.getUserName())){
            return ParseResponseParam.parse(user);
        }
        return new ErrorResponse("刷新失败");
    }

    @ResponseBody
    @RequestMapping("/logout")//用户退出
    public SuccessResponse logout(HttpServletRequest req, @RequestBody JSONObject j){
        SuccessResponse r=new SuccessResponse();
        boolean s=(boolean)j.get("isLogin");
        if(!s){
            req.getSession().invalidate();
            r.setStatus("success");
            return r;
        }
        r.setStatus("error");
        return r;
    }

    //==========================================
    @ResponseBody
    @RequestMapping("/updateUserName")//更改昵称
    public ResponseUtil updateUserName(@RequestBody String data,HttpServletRequest req){
        User user=ParseRequestParam.parse(data);
        User sessionUser=(User)req.getSession().getAttribute("session");
        if(user==null||sessionUser==null||StrUtil.isEmpty(user.getAccount())||StrUtil.isEmpty(sessionUser.getAccount())){
            return new ErrorResponse("error","data is null");
        }
        if(!user.getAccount().equals(sessionUser.getAccount())){
            return new ErrorResponse("data is not equals");
        }
        boolean b=userService.existName(user.getUserName());
        if(b){
            return new ErrorResponse("用户名已存在");
        }
        sessionUser.setUserName(user.getUserName());
        int result=userService.setUser(user);
        if(result==1){
            return new SuccessResponse();
        }
        return new ErrorResponse("update error");
    }


    @ResponseBody
    @RequestMapping("/checkPassword")//验证密码
    public ResponseUtil checkPassword(@RequestBody String data,HttpServletRequest req){
        User user=ParseRequestParam.parse(data);
        User sessionUser=(User)req.getSession().getAttribute("session");
        if(user==null||sessionUser==null||StrUtil.isEmpty(user.getAccount())||StrUtil.isEmpty(sessionUser.getAccount())){
            return new ErrorResponse("data is null");
        }
        if(!user.getAccount().equals(sessionUser.getAccount())){
            return new ErrorResponse("data is not equals");
        }
         user = userService.login(user.getAccount(), user.getPassword());
        if(user!=null){
            return new SuccessResponse();
        }
        return new ErrorResponse("password error");
    }


    @ResponseBody
    @RequestMapping("updatePassword")//修改密码
    public ResponseUtil updatePassword(@RequestBody String data,HttpServletRequest req){
        User user=ParseRequestParam.parse(data);
        User sessionUser=(User)req.getSession().getAttribute("session");
        if(user==null||sessionUser==null||StrUtil.isEmpty(user.getAccount())||StrUtil.isEmpty(sessionUser.getAccount())){
            return new ErrorResponse("data is null");
        }
        if(!user.getAccount().equals(sessionUser.getAccount())){
            return new ErrorResponse("data is not equals");
        }
        String newPassword=DigestUtil.md5Hex(user.getAccount()+user.getPassword());
        if(sessionUser.getPassword().equals(newPassword)){
            return new ErrorResponse("新旧密码不能相同");
        }
        sessionUser.setPassword(newPassword);
        int result=userService.setUser(sessionUser);
        if(result==1){
            return new SuccessResponse();
        }
        return new ErrorResponse("update password is null");
    }


    @ResponseBody
    @RequestMapping("/setReceiptInfo")//设置收货信息
    public ResponseUtil setReceiptInfo(@RequestBody String data,HttpServletRequest req){
        User user=ParseRequestParam.parse(data);
        User sessionUser=(User)req.getSession().getAttribute("session");
        if(user==null||sessionUser==null||StrUtil.isEmpty(user.getAccount())||StrUtil.isEmpty(sessionUser.getAccount())){
            return new ErrorResponse("data is null");
        }
        if(!user.getAccount().equals(sessionUser.getAccount())){
            return new ErrorResponse("data is not equals");
        }
        sessionUser.setAddress(user.getAddress());
        sessionUser.setTelephone(user.getTelephone());
        sessionUser.setName(user.getName());
        int result=userService.setUser(sessionUser);
        if(result==1){
            return new SuccessResponse();
        }
        return new ErrorResponse("setReceiptInfo error");
    }


    @ResponseBody
    @RequestMapping("/modifyReceInfo")//修改用户信息
    public ResponseUtil modifyReceInfo(@RequestBody JSONObject data,HttpServletRequest req){
        String params=(String)data.get("params");
        params=Base64.decodeStr(params);
        data=JSONObject.parseObject(params);
        int type=(int)data.get("type");
        String account=Base64.decodeStr((String)data.get("account"));
        User user=new User();
        user.setAccount(account);
        User sessionUser=(User)req.getSession().getAttribute("session");
        if(user==null||sessionUser==null||StrUtil.isEmpty(user.getAccount())||StrUtil.isEmpty(sessionUser.getAccount())){
            return new ErrorResponse("data is null");
        }
        if(!user.getAccount().equals(sessionUser.getAccount())){
            return new ErrorResponse("data is not equals");
        }
        switch (type){
            case 1:
                user.setName(Base64.decodeStr((String)data.get("name")));
                break;
            case 2:
                user.setTelephone(Base64.decodeStr((String)data.get("telephone")));
                break;
            case 3:
                user.setAddress(Base64.decodeStr((String)data.get("address")));
                break;
        }
        int result=userService.setUser(user);
        if(result==1){
            return new SuccessResponse();
        }
        return new ErrorResponse("修改用户信息错误");
    }
}
