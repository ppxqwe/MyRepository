package com.ppx.mall.bean;

import java.util.Date;

/**
 * product_order
 * 
 * @author bianj
 * @version 1.0.0 2022-01-14
 */
public class ProductOrder implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7361050427292735678L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    private Long id;

    private String productIds;

    private String account;

    private Date time;

    private Integer count;

    private Double cost;

    public ProductOrder() {
    }

    public ProductOrder(Long id, String productIds, String account, Date time, Integer count, Double cost) {
        this.id = id;
        this.productIds = productIds;
        this.account = account;
        this.time = time;
        this.count = count;
        this.cost = cost;
    }
    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductIds() {
        return this.productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    /* This code was generated by TableGo tools, mark 2 end. */

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", productIds='" + productIds + '\'' +
                ", account='" + account + '\'' +
                ", time=" + time +
                ", count=" + count +
                ", cost=" + cost +
                '}';
    }
}