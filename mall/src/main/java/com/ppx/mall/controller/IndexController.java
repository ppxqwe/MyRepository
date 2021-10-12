package com.ppx.mall.controller;

import com.ppx.mall.bean.Product;
import com.ppx.mall.bean.ProductPromo;
import com.ppx.mall.bean.ProductTitle;
import com.ppx.mall.bean.Slideshow;
import com.ppx.mall.dao.ProductPromoDao;
import com.ppx.mall.service.ProductPromoService;
import com.ppx.mall.service.ProductTitleService;
import com.ppx.mall.service.ProductService;
import com.ppx.mall.service.SlideshowService;
import com.ppx.mall.viewObject.ViewProductBody;
import com.ppx.mall.viewObject.ViewProductHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductTitleService productTitleService;

    @Autowired
    SlideshowService slideshowService;

    @Autowired
    ProductPromoService productPromoService;

    @ResponseBody
    @RequestMapping("/head")
    public List<ViewProductHead> indexHead(){
        //创建返回容器
        List<ViewProductHead> resultList=new ArrayList<ViewProductHead>();
        //查询所有标题
        List<ProductTitle> productKindList=productTitleService.findProductTitleAll();
        //获取返回的产品数据 每个标题6个产品
        for(ProductTitle productTitle:productKindList){
            List<Product> products=productService.getProductByTitleId(productTitle.getId(),0L,6L);
            ViewProductHead viewProduct=new ViewProductHead(
                    productTitle.getId(),
                    productTitle.getTitle(),
                    products
                    );
            resultList.add(viewProduct);
        }
        return resultList;
    }

    @ResponseBody
    @RequestMapping("/slideshow")
    public List<Slideshow> getSlideshow(){
        List<Long> ids=new ArrayList<Long>();
        ids.add(1L);ids.add(2L);ids.add(3L);ids.add(4L);
        return slideshowService.getSlideshw(ids);
    }
    @ResponseBody
    @RequestMapping("/body")
    public ViewProductBody indexBody(String title){
        ViewProductBody vpb=null;
        List<ProductPromo> productPromoList=null;
        List<Product> products=null;
        if("手机".equals(title)){
            //找宣传图
            productPromoList=productPromoService.findPromoByTitle(1);
            //获取产品集合
            products=productService.getProductByTitleId(1,0L,6L);
        }
        if("家电".equals(title)){
            //找宣传图
            productPromoList=productPromoService.findPromoByTitle(3);
            //获取产品集合
            products=productService.getProductByTitleId(3,0L,6L);
        }
        //将宣传图放入容器
        if(productPromoList.size()==1){
            vpb.setProduct1(productPromoList.get(0));
        }else if(productPromoList.size()==2){
            vpb.setProduct1(productPromoList.get(0));
            vpb.setProduct2(productPromoList.get(1));
        }
        //将产品放入容器
        if(products!=null||products.size()!=0){
            vpb.setProducts(products);
        }
        return vpb;
    }

//    @RequestMapping(value = "/getImage",produces = MediaType.IMAGE_JPEG_VALUE)
//    @ResponseBody
//    public byte[] getImage(String path) throws IOException {
//        String fileName=new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent()+"/image/"+path;
//        System.out.println(fileName);
//        FileInputStream inputStream = new FileInputStream(fileName);
//        byte[] bytes = new byte[inputStream.available()];
//        inputStream.read(bytes, 0, inputStream.available());
//        return bytes;
//    }
}
