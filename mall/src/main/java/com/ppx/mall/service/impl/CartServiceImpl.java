package com.ppx.mall.service.impl;

import com.ppx.mall.bean.Cart;
import com.ppx.mall.bean.Product;
import com.ppx.mall.dao.CartDao;
import com.ppx.mall.dao.ProductDao;
import com.ppx.mall.dao.ProductTitleDao;
import com.ppx.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartDao cartDao;

    @Autowired
    ProductTitleDao productTitleDao;

    @Autowired
    ProductDao productDao;

    @Override
    public int addCart(Cart cart) {
        return cartDao.addCart(cart);
    }

    @Override
    public Cart findCartByAccountAndProductId(String account,Long productId){
        Map<String,Object> map=new HashMap();
        map.put("account",account);
        map.put("productId",productId);
        List<Cart> carts= cartDao.findCartByCondition(map);
        if(carts.size()>=1){
            return carts.get(0);
        }
        return null;
    }

    @Override
    public int updateCart(Cart cart) {
        return cartDao.updateCart(cart);
    }

    @Override
    public List<Cart> getCartByAccount(String account) {
        Map<String,Object> map=new HashMap();
        map.put("account",account);
        List<Cart> cartList= cartDao.findCartByCondition(map);
        for(Cart c:cartList){
            //根据cart的productId属性找到titleId
            Product p=productDao.findProductById(c.getProductId());
            //根据titleId找到对应的分类
            c.setTitle(productTitleDao.finTitleById(p.getTitleId()));
        }
        return cartList;
    }

    @Override
    public Cart findCartById(Long id) {
        return cartDao.findCartById(id);
        //return null;
    }

    @Override
    public int deleteCartByAccount(String account){
        return cartDao.deleteCartByAccount(account);
        //return 0;
    }

    @Override
    public int deleteCartById(Long id) {
        return cartDao.deleteCartById(id);
        //return 0;
    }
}
