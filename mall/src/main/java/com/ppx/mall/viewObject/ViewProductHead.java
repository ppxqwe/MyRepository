package com.ppx.mall.viewObject;

import com.ppx.mall.bean.Product;

import java.util.List;

public class ViewProductHead {
    private Integer id;
    private String title;
    private List<Product> products;

    public ViewProductHead(Integer id, String title, List<Product> products) {
        this.id = id;
        this.title = title;
        this.products = products;
    }

    public ViewProductHead() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
