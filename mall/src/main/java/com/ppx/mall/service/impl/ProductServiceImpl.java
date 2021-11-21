package com.ppx.mall.service.impl;

import com.ppx.mall.bean.Product;
import com.ppx.mall.bean.ProductImg;
import com.ppx.mall.dao.ProductDao;
import com.ppx.mall.dao.ProductImgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ProductServiceImpl implements com.ppx.mall.service.ProductService {
    @Autowired
    ProductDao productDao;

    @Autowired
    ProductImgDao productImgDao;

    @Override
    public List<Product> getProductByTitleId(Integer titleId,Long start,Long num) {
        Map<String,Object> map=new HashMap();
        map.put("titleId",titleId);
        map.put("start",start);
        map.put("num",num);
        return productDao.findProductByCondition(map);
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.findProductById(id);
    }

    public List<ProductImg> getProductImg(Long id){
        List<ProductImg> imgs=productImgDao.selectImgByProduct(id);
        Long i=1L;
        for(ProductImg p:imgs){
            p.setId(i++);
        }
        return imgs;
    }
}
