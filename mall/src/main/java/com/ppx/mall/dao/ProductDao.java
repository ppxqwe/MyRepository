package com.ppx.mall.dao;

import com.ppx.mall.bean.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    List<Product> findProductAll();
    List<Product> findProductByCondition(Map<String,Object> map);
    int addProduct(Product product);
    Product findProductById(Long id);
}
