package com.ppx.mall.bean;

/**
 * slideshow
 * 
 * @author bianj
 * @version 1.0.0 2021-10-03
 */
public class Slideshow implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8287510951605058351L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    private Long id;

    private String productId;

    private String imgSrc;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImgSrc() {
        return this.imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}