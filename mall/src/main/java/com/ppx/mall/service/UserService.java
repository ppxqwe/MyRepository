package com.ppx.mall.service;

import com.ppx.mall.bean.User;

public interface UserService {
    User login(String account,String password);//用户登录
    User register(User user);//注册用户
    boolean existAccount(String account);//判断账号是否存在
    boolean existName(String userName);//判断昵称是否存在
    User getUser(String account);//根据账号返回User
}
