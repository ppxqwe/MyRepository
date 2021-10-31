package com.ppx.mall.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.ppx.mall.bean.User;
import com.ppx.mall.dao.UserDao;
import com.ppx.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public User login(String account,String password){
        String key= DigestUtil.md5Hex(account+password);
        User user=userDao.findUserById(account);
        if(user==null){
            return null;
        }
        if(key.equals(user.getPassword())){
            return user;
        }
        return null;
    }

    public User register(User user){
        //对用户密码进行加密
        String key=DigestUtil.md5Hex(user.getAccount()+user.getPassword());
        user.setPassword(key);
        if(userDao.addUser(user)==1){
            return userDao.findUserById(user.getAccount());
        }
        return null;
    }

    public boolean existAccount(String account){
        if(userDao.findUserById(account)!=null){
            //账号已存在
            return true;
        }
        return false;
    }

    public boolean existName(String userName){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userName",userName);
        if(userDao.findUserByCondition(map)!=null){
            return true;
        }
        return false;
    }

    public User getUser(String account){
        return userDao.findUserById(account);
    }

}
