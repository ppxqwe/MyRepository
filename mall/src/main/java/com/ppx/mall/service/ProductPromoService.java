package com.ppx.mall.service;

import com.ppx.mall.bean.ProductPromo;

import java.util.List;

public interface ProductPromoService {
    List<ProductPromo> findPromoByTitle(Integer titleId);
}
