package com.ppx.mall.service.impl;

import com.ppx.mall.bean.ProductOrder;
import com.ppx.mall.dao.ProductOrderDao;
import com.ppx.mall.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    ProductOrderDao productOrderDao;
    @Override
    public int addProduct(ProductOrder productOrder) {
        return productOrderDao.addProductOrder(productOrder);
    }
}
