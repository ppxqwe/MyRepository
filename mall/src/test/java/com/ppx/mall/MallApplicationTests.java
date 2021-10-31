package com.ppx.mall;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONPObject;
import com.ppx.mall.bean.ProductPromo;
import com.ppx.mall.bean.User;
import com.ppx.mall.dao.ProductDao;
import com.ppx.mall.bean.Product;
import com.ppx.mall.dao.ProductPromoDao;
import com.ppx.mall.dao.UserDao;
import com.ppx.mall.service.UserService;
import com.ppx.mall.util.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    UserDao userDao;
    //@Test
    public void Test04(){
        List<User> users=userDao.findUserAll();
        for(User user:users){
            System.out.println(user);
        }
    }

    @Autowired
    UserService userService;
    //@Test
    public void Test05(){
        //User user=userService.login("12312421","123123");
        //System.out.println(user);

    }

    //@Test
    public void Test06(){
        //userDao.addUser(new User("12312312","12312312","张三"));

    }

    //@Test
    public void Test07(){
//        String password=Base64.encode("12312312");
//        User user=new User("123123",password,"123123");
//        String password1=Base64.decodeStr(user.getPassword());
//        user.setPassword(password1.substring(1,password1.length()-1));
//        System.out.println(user);
    }

    //@Test
    void Test08(){
        ResponseUtil responseUtil=new ResponseUtil();
        responseUtil.setStatus("success");
        User user=new User();
        user.setAccount("12312312");
        responseUtil.addMessage("user",user);
        JSONPObject jsonpObject=new JSONPObject();
        jsonpObject.addParameter(responseUtil);
        System.out.println(jsonpObject.toJSONString());
    }

    //@Test
    void Test09(){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().replace("-","").toUpperCase());
        System.out.println(uuid);
    }

    //@Test
    void Test10(){
        User user=new User();
        user.setAccount("99999999");
        user.setPassword("11111111");
        user.setUserName("张三");
        User result=userService.register(user);
        System.out.println(result);
    }

    @Test
    void Test11(){
        String s="eyJ1c2VyIjp7ImFjY291bnQiOiIiLCJhZGRyZXNzIjoiIiwiYXZhdGFyU3JjIjoiIiwibmFtZSI6IiIsInBhc3N3b3JkIjoiIiwidGVsZXBob25lIjoiIiwidXNlck5hbWUiOiIxMjMifX0=";
        s= Base64.decodeStr(s);
        System.out.println(s);
    }
}
