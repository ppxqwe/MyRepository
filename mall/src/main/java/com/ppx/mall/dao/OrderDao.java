package com.ppx.mall.dao;

import com.ppx.mall.bean.ProductOrder;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    int addProductOrder(ProductOrder productOrder);
    List<ProductOrder> findProductOrderByCondition(Map<String,Object> map);
}
