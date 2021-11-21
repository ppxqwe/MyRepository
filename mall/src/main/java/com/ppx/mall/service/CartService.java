package com.ppx.mall.service;

import com.ppx.mall.bean.Cart;
import com.ppx.mall.dao.CartDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CartService {
    int addCart(Cart cart);
    Cart findCartByAccountAndProductId(String account, Long productId);
    int updateCart(Cart cart);
    List<Cart> getCartByAccount(String account);
    Cart findCartById(Long id);
    int deleteCartByAccount(String account);
    int deleteCartById(Long id);
}
