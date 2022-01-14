package com.ppx.mall.viewObject;

import lombok.Data;

import java.util.List;

@Data
public class ViewOrder {
    private Long id;
    private List<ProductInfo> commodities;
    private String time;
    private Integer allCount;
    private Double allCost;

}
