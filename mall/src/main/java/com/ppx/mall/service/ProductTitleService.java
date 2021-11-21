package com.ppx.mall.service;

import com.ppx.mall.bean.ProductTitle;

import java.util.List;

public interface ProductTitleService {
    List<ProductTitle> findProductTitleAll();

    Integer findTitleIdByTitle(String titleName);

    String finTitleById(Integer id);
}
