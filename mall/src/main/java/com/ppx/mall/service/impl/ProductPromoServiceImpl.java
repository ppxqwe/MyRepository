package com.ppx.mall.service.impl;

import com.ppx.mall.bean.ProductPromo;
import com.ppx.mall.dao.ProductPromoDao;
import com.ppx.mall.service.ProductPromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPromoServiceImpl implements ProductPromoService {

    @Autowired
    ProductPromoDao productPromoDao;

    public List<ProductPromo> findPromoByTitle(Integer titleId){
        return productPromoDao.findPromoByTitle(titleId);
    }
}
