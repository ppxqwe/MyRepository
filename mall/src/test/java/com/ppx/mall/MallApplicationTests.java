package com.ppx.mall;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.ppx.mall.bean.*;
import com.ppx.mall.dao.*;
import com.ppx.mall.service.ProductTitleService;
import com.ppx.mall.util.ParseRequestParam;
import com.ppx.mall.service.UserService;
import com.ppx.mall.util.SuccessResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        SuccessResponse responseUtil=new SuccessResponse();
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

    //@Test
    void Test11(){
        String s="{\"params\":\"eyJhY2NvdW50IjoiT1RrNU9UazVPVGs9IiwicGFzc3dvcmQiOiJNVEV4TVRFeE1URT0ifQ==\"}";
        String value=(String)JSONObject.parseObject(s).get("params");
        System.out.println(value);
    }

    //@Test
    void Test12(){
        String s="{\"account\":\"OTk5OTk5OTk=\",\"password\":\"MTExMTExMTE=\"}";
        User user=ParseRequestParam.parse(s);
        System.out.println(user);
    }

    //@Test
    void Test13(){
        String s="eyJ1c2VyIjp7ImFjY291bnQiOiIiLCJhZGRyZXNzIjoiIiwiYXZhdGFyU3JjIjoiIiwibmFtZSI6IiIsInBhc3N3b3JkIjoiIiwidGVsZXBob25lIjoiIiwidXNlck5hbWUiOiIxMjMifX0=";
        String userStr=Base64.decodeStr(s);
        System.out.println(userStr);
    }

    //@Test
    void Test14(){
        String data="";
        System.out.println(JSONObject.parseObject(data));
    }
    //@Test
    void Test15(){
        User user=new User();
        user.setAccount("12312312");
        user.setPassword("111");
        int result=userDao.updateUser(user);
        System.out.println(result);
    }

    //@Test
    void Test16() {
        String avatarSrc="http://ppxtest.xyz:8077/imgServer/image/"+"12312312"+".jpg";
        String fileName=avatarSrc.substring(40);
        System.out.println(fileName);
        System.out.println("http://ppxtest.xyz:8077/imgServer/image/".length());
    }

    //@Test
    void Test17(){

        String s="MTExMTExMTE=";
        System.out.println(Base64.decodeStr(s));
    }

    //@Test
    void Test18(){
        User user=userService.getUser("19999999");
        System.out.println(user);
        user.setAvatarSrc("www.josn.cn");
        userService.setUser(user);
    }

    //@Test
    void Test19(){
       String s="5bCP5a6d";
        System.out.println(Base64.decodeStr(s));
    }

    @Autowired
    ProductImgDao productImgDao;
    //@Test
    void Test20(){

    }

    @Autowired
    CartDao cartDao;


    //@Test
    void Test21(){
        //cartDao.addCart(new Cart(1L,5L,"hello","http://www.baidu.com",5.0,5,25.0,"123"));
        /*List<Cart> list=cartDao.findCartAll();
        for(Cart c:list){
            System.out.println(c.getId());
        }*/

        /*Map<String,Object> map=new HashMap();
        map.put("account",123);
        List<Cart> list=cartDao.findCartByCondition(map);
        System.out.println(list.size());
        for(Cart c:list){
            System.out.println(c);
        }*/
        String a="A";
        a=a+1;
        System.out.println(a);
    }


    @Autowired
    ProductTitleService productTitleService;
    //@Test
    void Test22(){
        System.out.println(productTitleService.finTitleById(3));
    }

    //@Test
    void Test23(){
        String s=null;
        System.out.println(Base64.decodeStr(s));
    }

}
