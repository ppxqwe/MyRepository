package com.ppx.mall;

import com.ppx.mall.bean.ProductPromo;
import com.ppx.mall.dao.ProductDao;
import com.ppx.mall.bean.Product;
import com.ppx.mall.dao.ProductPromoDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MallApplicationTests {
    @Autowired
    ProductDao productDao;

    @Autowired
    ProductPromoDao productPromoDao;
    //@Test
    public void Test01() {
        List<Product> productList=productDao.findProductAll();
        for(Product product:productList){
            System.out.println(product);
        }
    }

    //@Test
    public void Test02(){
        for(int i=0;i<6;i++){
            Product product=new Product();
            product.setTitleId(7);
            product.setDescription("...");
            productDao.addProduct(product);
        }
    }
    //@Test
    public void Test03(){
        List<ProductPromo> productPromoList=productPromoDao.findPromoByTitle(3);
        for(ProductPromo p:productPromoList){
            System.out.println(p.getProductId());
        }
    }

}
