package com.ppx.mall.service.impl;

import com.ppx.mall.bean.CollectionProduct;
import com.ppx.mall.dao.CollectionProductDao;
import com.ppx.mall.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectionControllerImpl implements CollectionService {
    @Autowired
    CollectionProductDao collectionProductDao;

    public int addCollection(CollectionProduct c){
        return collectionProductDao.addCollectionProduct(c);
    }

    public List<CollectionProduct> getCollectionByAccount(String account,Integer page,Integer count){
        Map<String,Object> map=new HashMap<>();
        map.put("account",account);
        map.put("start",(page-1)*count);
        map.put("count",count);
        return collectionProductDao.findCollectionProductByCondition(map);
    }

    @Override
    public CollectionProduct isLove(String account, Long productId) {
        Map<String,Object> map=new HashMap<>();
        map.put("account",account);
        map.put("productId",productId);
        List<CollectionProduct> list=collectionProductDao.findCollectionProductByCondition(map);
        if(list!=null&&list.size()>0){
            return list.get(0);//已添加收藏
        }
        return null;
    }
    public int deleteCollectionProductById(Long id){
        return collectionProductDao.deleteCollectionProductById(id);
    }

    @Override
    public int getCountByAccount(String account) {
        return collectionProductDao.getCountByAccount(account);
    }

    @Override
    public CollectionProduct getById(Long id) {
        return collectionProductDao.findCollectionProductById(id);
    }
}
