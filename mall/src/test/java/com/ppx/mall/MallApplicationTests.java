package com.ppx.mall;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.ppx.mall.bean.*;
import com.ppx.mall.dao.*;
import com.ppx.mall.service.CollectionService;
import com.ppx.mall.service.ProductTitleService;
import com.ppx.mall.service.impl.CommentServiceImpl;
import com.ppx.mall.util.ParseRequestParam;
import com.ppx.mall.service.UserService;
import com.ppx.mall.util.SuccessResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
        String avatarSrc="http://ppxtest.xyz:80/imgServer/image/"+"12312312"+".jpg";
        String fileName=avatarSrc.substring(40);
        System.out.println(fileName);
        System.out.println("http://ppxtest.xyz:80/imgServer/image/".length());
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

    @Autowired
    CommentDao commentDao;

    @Autowired
    CommentServiceImpl commentService;
    //@Test
    void Test23(){
        //Comment c=new Comment(null,1L,"33333333","可以哈哈哈",new Date(),2,0,2L);
        //commentService.addComment(c);
        /* List<Comment> commentList=commentService.findCommentByProductIdAndTypeOne(1L);
        for(Comment c:commentList){
            System.out.println(c);
        }*/
        List<Comment> commentList=commentService.findCommentByIdAndTypeTwo(1L);
        for(Comment c:commentList){
            System.out.println(c);
        }
    }

    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zoneId).toInstant();
            return instant.toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //@Test
    void Test24(){
        LocalDateTime l=timestampToLocalDateTime(1639467335613L);
        System.out.println(l);
        LocalDateTime time=timestampToLocalDateTime(1639467335613L);
        System.out.println(time);
        System.out.println(localDateTimeToTimestamp(time));
    }

    //@Test
    void Test25(){
        List<Long> ids=new ArrayList<>();
        ids.add(124L);
        ids.add(122L);
        ids.add(120L);
        int result=commentDao.deleteCommentByIds(ids);
        System.out.println(result);
    }

    @Autowired
    CollectionProductDao collectionProductDao;
    @Autowired
    CollectionService collectionService;
    //@Test
    void Test26(){
        //System.out.println(collectionProductDao.findCollectionProductAll());
        String account="11111111";
        List<CollectionProduct> list=collectionService.getCollectionByAccount(account,1,12);
       for(CollectionProduct cp:list){
           System.out.println(cp);
       }
    }

    @Test
    void Test27(){
//        String account="55555555";
//        System.out.println(Base64.encode(account));// MTExMTExMTE=
        /*int count=collectionProductDao.getCountByAccount("11111111");
        System.out.println(count);*/
        /*SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期的格式化
        String v=df.format(new Date());
        System.out.println(v);*/
    }




}
