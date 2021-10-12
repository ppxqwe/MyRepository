package com.ppx.mall.dao;

import com.ppx.mall.bean.ProductPromo;

import java.util.List;

public interface ProductPromoDao {

    List<ProductPromo> findPromoByTitle(Integer titleId);
}
