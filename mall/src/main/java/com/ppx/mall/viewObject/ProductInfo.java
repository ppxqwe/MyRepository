package com.ppx.mall.viewObject;

import lombok.Data;

@Data
public class ProductInfo {
    private Long productId;
    private String title;
    private String name;
    private String imgSrc;
    private Double price;
    private Integer count;
    private Double cost;
}
