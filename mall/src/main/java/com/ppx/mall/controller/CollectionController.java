package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.CollectionProduct;
import com.ppx.mall.bean.Product;
import com.ppx.mall.service.CollectionService;
import com.ppx.mall.service.ProductService;
import com.ppx.mall.service.ProductTitleService;
import com.ppx.mall.util.ErrorResponse;
import com.ppx.mall.util.ResponseUtil;
import com.ppx.mall.util.SuccessResponse;
import com.ppx.mall.viewObject.ViewCollectionProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CollectionController {
    @Autowired
    CollectionService collectionService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductTitleService productTitleService;

    @RequestMapping("/addCollect")//添加收藏
    @ResponseBody
    public ResponseUtil addCollection(@RequestBody JSONObject json){
        Object accountObject=json.get("account");
        if(accountObject==null){
            return new ErrorResponse("账号为空");
        }
        String account= Base64.decodeStr(accountObject.toString());
        Long productId=Long.parseLong(json.get("id").toString());
        CollectionProduct isLoved=collectionService.isLove(account,productId);
        if(isLoved!=null){
            collectionService.deleteCollectionProductById(isLoved.getId());
            return new SuccessResponse();
        }
        CollectionProduct cp=new CollectionProduct(null,account,productId);
        int result=collectionService.addCollection(cp);
        if(result==1){
            return new SuccessResponse();
        }
        return new ErrorResponse("添加收藏失败");
    }

    @ResponseBody
    @RequestMapping("/getCollectDatas")
    public ResponseUtil getAll(@RequestBody JSONObject json){
        Object accountObject=json.get("account");
        if(accountObject==null){
            return new ErrorResponse("账号为空");
        }
        String account=Base64.decodeStr(accountObject.toString());
        Object pageObject=json.get("page");
        Integer page=0;
        if(pageObject==null){
            page=1;
        }else{
            page=Integer.parseInt(pageObject.toString());
        }
        //根据账号找到商品id
        List<ViewCollectionProduct> products=getData(account,page);
        SuccessResponse successResponse=new SuccessResponse();
        successResponse.addMessage("productList",products);
        if(products.size()!=0){  //合理数据 一个页码除了count 0 都应该有至少一个数据
            successResponse.addMessage("count",collectionService.getCountByAccount(account));
        }else{ //数据不合理，返回count 0
            successResponse.addMessage("count",0);
        }
        return successResponse;
    }

    @ResponseBody
    @RequestMapping("/deleteCollect")
    public ResponseUtil deleteCollect(@RequestBody JSONObject json){


        List ids=(List)json.get("ids");
        Integer page=(Integer)json.get("page");
        if(ids==null||ids.size()==0){
            return new ErrorResponse("没有要删除的收藏商品");
        }
        Long id=Long.parseLong(ids.get(0).toString());
        CollectionProduct cp=collectionService.getById(id);
        for(int a=0;a<ids.size();a++){
            Long delId=Long.parseLong(ids.get(a).toString());
            collectionService.deleteCollectionProductById(delId);
        }
        String account="";
        if(cp!=null){
            account=cp.getAccount();
            System.out.println(account);
        }
        List<ViewCollectionProduct> products=getData(account,page);
        SuccessResponse successResponse=new SuccessResponse();
        successResponse.addMessage("productList",products);
        successResponse.addMessage("count",collectionService.getCountByAccount(account));
        return successResponse;
    }

    public List<ViewCollectionProduct> getData(String account,Integer page){
        List<CollectionProduct> productList=collectionService.getCollectionByAccount(account,page,12);
        List<ViewCollectionProduct> products=new ArrayList<>();
        if(productList!=null&&productList.size()!=0){

            for(CollectionProduct cp:productList){
                Product p=productService.getProductById(cp.getProductId());
                if(p!=null){
                    ViewCollectionProduct vcp=new ViewCollectionProduct();
                    vcp.setId(cp.getId());
                    vcp.setProductId(p.getId());
                    vcp.setTitle(productTitleService.finTitleById(p.getTitleId()));
                    vcp.setImgSrc(p.getImgSrc());
                    vcp.setName(p.getName());
                    vcp.setCurPrice(p.getCurPrice());
                    vcp.setOriPrice(p.getOriPrice());
                    vcp.setDescription(p.getDescription());
                    products.add(vcp);
                }
            }
        }
        return products;
    }

}
