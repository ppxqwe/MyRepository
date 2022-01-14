package com.ppx.mall.service;

import com.ppx.mall.bean.ProductOrder;
import com.ppx.mall.viewObject.ViewOrder;

import java.util.List;

public interface OrderService {
    int addProductOrder(ProductOrder productOrder);
    List<ViewOrder> getViewOrder(String account);
}
