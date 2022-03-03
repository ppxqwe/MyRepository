package com.ppx.mall.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ppx.mall.bean.Product;
import com.ppx.mall.bean.ProductOrder;
import com.ppx.mall.dao.OrderDao;
import com.ppx.mall.dao.ProductDao;
import com.ppx.mall.dao.ProductTitleDao;
import com.ppx.mall.service.OrderService;
import com.ppx.mall.viewObject.ProductInfo;
import com.ppx.mall.viewObject.ViewCart;
import com.ppx.mall.viewObject.ViewOrder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductTitleDao productTitleDao;

    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期的格式化

    @Override
    public int addProductOrder(ProductOrder productOrder) {
        return orderDao.addProductOrder(productOrder);
    }

    @Override
    public List<ViewOrder> getViewOrder(String account) {
        Map<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<ProductOrder> productOrders=orderDao.findProductOrderByCondition(map);
        List<ViewOrder> viewOrders=new ArrayList<>();
        if(productOrders!=null&&productOrders.size()!=0){
            for(int i=0;i<productOrders.size();i++){
                ProductOrder p=productOrders.get(i);
                //一个订单
                ViewOrder vo=new ViewOrder();
                //设置订单基本信息
                vo.setId(p.getId());
                vo.setTime( df.format(p.getTime()));
                vo.setAllCount(p.getCount());
                vo.setAllCost(p.getCost());
                vo.setOrderId(p.getAccount().substring(5,8)+p.getTime().getTime()/100+ RandomUtil.randomNumbers(4));
                //设置订单详细商品信息
                List<ProductInfo> productInfos=new ArrayList<>();
                String s=p.getProductIds();
                String[] strings=s.split(","); //得到键值对 商品id:商品数量
                for(String s1:strings){
                    ProductInfo productInfo=new ProductInfo();
                    String[] strings1=s1.split(":");
                    Long productId=Long.parseLong(strings1[0]);
                    Integer count=Integer.parseInt(strings1[1]);
                    Product product=productDao.findProductById(productId);
                    //注入数据
                    productInfo.setProductId(productId);
                    productInfo.setTitle(productTitleDao.finTitleById(product.getTitleId()));
                    productInfo.setName(product.getName());
                    productInfo.setImgSrc(product.getImgSrc());
                    productInfo.setPrice(Double.parseDouble(product.getCurPrice().replace("元","")));
                    productInfo.setCount(count);
                    productInfo.setCost(productInfo.getPrice()*count);

                    productInfos.add(productInfo);
                }
                vo.setCommodities(productInfos);
                viewOrders.add(vo);
            }
        }
        return viewOrders;
    }

    @Override
    public int deleteProductOrderByIds(List<Long> ids) {
        return orderDao.deleteProductOrderByIds(ids);
    }
}
