package com.ppx.mall.dao;

import com.ppx.mall.bean.ProductTitle;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ProductTitleDao {
    List<ProductTitle> findProductTitleAll();

    Integer findProductTitleByTitleName(String title);

    String finTitleById(Integer id);
}
