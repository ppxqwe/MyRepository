package com.ppx.mall.viewObject;

import com.ppx.mall.bean.Product;
import com.ppx.mall.bean.ProductPromo;
import lombok.Data;

import java.util.List;
@Data
public class ViewProductBody {
    private ProductPromo product1;
    private ProductPromo product2;
    private List<Product> products;
}
