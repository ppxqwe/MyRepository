package com.ppx.mall.dao;

import com.ppx.mall.bean.ProductImg;

import java.util.List;

public interface ProductImgDao {

    List<ProductImg> findProductImgAll();
    List<ProductImg> selectImgByProduct(Long productId);
    int addProductImg(ProductImg p);
}
