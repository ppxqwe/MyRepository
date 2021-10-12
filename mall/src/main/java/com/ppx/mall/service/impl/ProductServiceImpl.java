package com.ppx.mall.service.impl;

import com.ppx.mall.bean.Product;
import com.ppx.mall.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ProductServiceImpl implements com.ppx.mall.service.ProductService {
    @Autowired
    ProductDao productDao;

    @Override
    public List<Product> getProductByTitleId(Integer titleId,Long start,Long num) {
        Map<String,Object> map=new HashMap();
        map.put("title_id",titleId);
        map.put("start",start);
        map.put("num",num);
        return productDao.findProductByCondition(map);
    }
}
