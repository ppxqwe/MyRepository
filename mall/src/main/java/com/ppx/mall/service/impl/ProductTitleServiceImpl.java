package com.ppx.mall.service.impl;

import com.ppx.mall.bean.ProductTitle;
import com.ppx.mall.dao.ProductTitleDao;
import com.ppx.mall.service.ProductTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTitleServiceImpl implements ProductTitleService {

    @Autowired
    ProductTitleDao productTitleDao;


    @Override
    public List<ProductTitle> findProductTitleAll() {
        return productTitleDao.findProductTitleAll();
    }

    @Override
    public Integer findTitleIdByTitle(String titleName){
        return productTitleDao.findProductTitleByTitleName(titleName);
    }


    public String finTitleById(Integer id){
        return productTitleDao.finTitleById(id);
    }
}
