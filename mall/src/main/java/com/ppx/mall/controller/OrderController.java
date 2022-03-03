package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.Cart;
import com.ppx.mall.bean.ProductOrder;
import com.ppx.mall.service.CartService;
import com.ppx.mall.service.OrderService;
import com.ppx.mall.util.ErrorResponse;
import com.ppx.mall.util.ResponseUtil;
import com.ppx.mall.util.SuccessResponse;
import com.ppx.mall.viewObject.ViewOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @ResponseBody
    @RequestMapping("payment")
    public ResponseUtil addOrder(@RequestBody JSONObject json){
        String account= Base64.decodeStr(json.get("account").toString());
        List ids=(List)json.get("ids");// 用户需要下单的所有商品id
        if(ids==null||ids.size()==0){
            return new ErrorResponse("没有要购买的商品");
        }

        ProductOrder productOrder=new ProductOrder();
        int count=0;
        Double cost=0.0;
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<ids.size();i++){
            Long cartId=Long.parseLong(ids.get(i).toString());
            //去购物车找到对应商品
            //Cart cart=cartService.findCartByAccountAndProductId(account,productId);
            Cart cart=cartService.findCartById(cartId);
            if(cart!=null){
                if(cart.getAccount().equals(account)){
                    count+=cart.getCount();
                    cost=cost+cart.getCost();
                    sb.append(cart.getProductId()+":"+cart.getCount()+",");
                    cartService.deleteCartById(cart.getId());  //删除购物车
                }
            }
        }
        if(count==0){
            return new ErrorResponse("没有找到商品");
        }
        productOrder.setAccount(account);
        productOrder.setProductIds(sb.toString());
        productOrder.setTime(new Date());
        productOrder.setCount(count);
        productOrder.setCost(cost);
        int result=orderService.addProductOrder(productOrder);
        if(result==1){
            return new SuccessResponse();
        }
        return new ErrorResponse("添加订单异常");
    }


    @ResponseBody
    @RequestMapping("getOrder")
    public ResponseUtil getOrder(@RequestBody JSONObject json){
        String account=Base64.decodeStr(json.get("account").toString());
        List<ViewOrder> viewOrders=orderService.getViewOrder(account);
        SuccessResponse sr=new SuccessResponse();
        sr.addMessage("order",viewOrders);
        return sr;
    }

    @ResponseBody
    @RequestMapping("/deleteOrder")
    public ResponseUtil deleteOrder(@RequestBody JSONObject json){
        List ids=(List)json.get("ids");
        if(ids==null||ids.size()<1){
            return new ErrorResponse("参数为空");
        }
        List<Long> ids1=new ArrayList<>();
        for(int i=0;i<ids.size();i++){
            ids1.add(Long.parseLong(ids.get(i).toString()));
        }
        orderService.deleteProductOrderByIds(ids1);
        return new SuccessResponse();
    }


}
