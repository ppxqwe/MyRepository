package com.ppx.mall.dao;

import com.ppx.mall.bean.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> findUserAll();
    User findUserById(String account);
    List<User> findUserByIds(List<String> accounts);
    User findUserByCondition(Map<String,Object> map);
    int addUser(User user);
}
