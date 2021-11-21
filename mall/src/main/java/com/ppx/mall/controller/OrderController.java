package com.ppx.mall.controller;

import com.ppx.mall.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @Autowired
    ProductOrderService productOrderService;

    @ResponseBody
    @RequestMapping("/buy")
    public void buy(){

    }
}
