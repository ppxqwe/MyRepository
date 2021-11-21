package com.ppx.mall.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.User;

public class ParseRequestParam {

    /**
     *
     * @param data 被base64的User的json对象 对象的属性也Base64了
     * @return 完整的User对象
     */
    public static User parse(String data){
        JSONObject j=JSONObject.parseObject(data);
        if(j==null){
            return null;
        }
        data=(String)j.get("params");
        data= Base64.decodeStr(data);
        User user=JSONObject.parseObject(data,User.class);
            user.setAccount(Base64.decodeStr(user.getAccount()));
            user.setPassword(Base64.decodeStr(user.getPassword()));
            user.setUserName(Base64.decodeStr(user.getUserName()));
            user.setAvatarSrc(Base64.decodeStr(user.getAvatarSrc()));
            user.setAddress(Base64.decodeStr(user.getAddress()));
            user.setTelephone(Base64.decodeStr(user.getTelephone()));
            user.setName(Base64.decodeStr(user.getName()));
        return user;
    }
}
