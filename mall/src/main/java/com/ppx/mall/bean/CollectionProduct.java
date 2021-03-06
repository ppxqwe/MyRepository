package com.ppx.mall.bean;

/**
 * collection_product
 * 
 * @author bianj
 * @version 1.0.0 2022-01-04
 */
public class CollectionProduct implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5431178115092230506L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    private Long id;

    private String account;

    private Long productId;

    public CollectionProduct(Long id, String account, Long productId) {
        this.id = id;
        this.account = account;
        this.productId = productId;
    }

    public CollectionProduct() {
    }
    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /* This code was generated by TableGo tools, mark 2 end. */

    @Override
    public String toString() {
        return "CollectionProduct{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", productId=" + productId +
                '}';
    }
}