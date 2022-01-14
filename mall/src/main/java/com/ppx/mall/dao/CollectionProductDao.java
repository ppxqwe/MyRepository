package com.ppx.mall.dao;

import com.ppx.mall.bean.CollectionProduct;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CollectionProductDao {
    List<CollectionProduct> findCollectionProductAll();
    List<CollectionProduct> findCollectionProductByCondition(Map<String,Object> map);
    int addCollectionProduct(CollectionProduct collectionProduct);
    int deleteCollectionProductById(Long id);
    @Select("select count(*) from collection_product where account=#{account}")
    int getCountByAccount(String account);
    CollectionProduct findCollectionProductById(Long id);
}
