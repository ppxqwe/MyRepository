package com.ppx.mall.viewObject;

import lombok.Data;
//收藏页面用于展示的单个商品数据
@Data
public class ViewCollectionProduct {
    private Long id;
    private Long productId;
    private String title;//标题
    private String imgSrc;
    private String name;
    private String curPrice;
    private String oriPrice;
    private String description;//小描述
}
