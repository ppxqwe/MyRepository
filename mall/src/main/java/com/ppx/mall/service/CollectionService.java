package com.ppx.mall.service;

import com.ppx.mall.bean.CollectionProduct;

import java.util.List;

public interface CollectionService {
    int addCollection(CollectionProduct c);
    List<CollectionProduct> getCollectionByAccount(String account,Integer page,Integer pageCount);
    CollectionProduct isLove(String account,Long productId);
    int deleteCollectionProductById(Long id);
    int getCountByAccount(String account);
    CollectionProduct getById(Long id);
}
