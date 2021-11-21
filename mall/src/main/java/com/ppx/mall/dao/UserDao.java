package com.ppx.mall.dao;

import com.ppx.mall.bean.Product;
import com.ppx.mall.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> findUserAll();
    User findUserById(String account);
    List<User> findUserByIds(List<String> accounts);
    List<User> findUserByCondition(Map<String,Object> map);
    int addUser(User user);
    int updateUser(User user);

}
