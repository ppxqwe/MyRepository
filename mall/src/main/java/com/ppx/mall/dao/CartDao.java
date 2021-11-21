package com.ppx.mall.dao;

import com.ppx.mall.bean.Cart;

import java.util.List;
import java.util.Map;

public interface CartDao {
    int addCart(Cart cart);
    List<Cart> findCartAll();
    List<Cart> findCartByCondition(Map<String,Object> map);
    int updateCart(Cart cart);
    Cart findCartById(Long id);
    int deleteCartByAccount(String account);
    int deleteCartById(Long id);
}
