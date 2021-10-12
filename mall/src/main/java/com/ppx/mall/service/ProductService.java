package com.ppx.mall.service;

import com.ppx.mall.bean.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductByTitleId(Integer titleId,Long start,Long num);
}
