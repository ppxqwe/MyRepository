package com.ppx.mall.viewObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewCart {
    private Long id;

    private Long productId;

    private String name;

    private String imgSrc;

    private Double price;

    private Integer count;

    private Double cost;

    private String title;
}
