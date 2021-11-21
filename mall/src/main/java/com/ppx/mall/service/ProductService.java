package com.ppx.mall.service;

import com.ppx.mall.bean.Product;
import com.ppx.mall.bean.ProductImg;

import java.util.List;

public interface ProductService {
    List<Product> getProductByTitleId(Integer titleId,Long start,Long num);
    Product getProductById(Long id);
    List<ProductImg> getProductImg(Long id);
}
