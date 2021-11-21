package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.Cart;
import com.ppx.mall.bean.Product;
import com.ppx.mall.bean.User;
import com.ppx.mall.service.CartService;
import com.ppx.mall.service.ProductService;
import com.ppx.mall.util.ErrorResponse;
import com.ppx.mall.util.ResponseUtil;
import com.ppx.mall.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    /**
     * 添加商品到购物车
     * 参数 user + 商品id
     * 返回 success
     */
    @ResponseBody
    @RequestMapping("/addCart")
    public ResponseUtil addCart(@RequestBody JSONObject j,HttpServletRequest req){
        ResponseUtil r=this.checkUserLogin(j,req);
        if(r!=null){
            return r;
        }
        //拿到商品id
        Long id=Long.parseLong((String)j.get("id"));
        Product p=productService.getProductById(id);
        if(ObjectUtil.isEmpty(p)){
            return new ErrorResponse("error","商品不存在");
        }
        User user=(User)req.getSession().getAttribute("session");
        Cart c=cartService.findCartByAccountAndProductId(user.getAccount(),p.getId());
        if(ObjectUtil.isEmpty(c)){//如果该用户购物车没有该商品 添加一个新的
            Cart cart=new Cart(null,p.getId(),p.getName(),p.getImgSrc()
                    ,Double.parseDouble(p.getCurPrice().replace("元","")),
                    1,Double.parseDouble(p.getCurPrice().replace("元","")),
                    user.getAccount());
            int result=cartService.addCart(cart);
            if(result==1){
                return new SuccessResponse("success");
            }
        }
        //如果该用户添加过该商品 则购物车商品+1
        c.setPrice(Double.parseDouble(p.getCurPrice().replace("元","")));
        c.setCount(c.getCount()+1);
        c.setCost(c.getCount()*c.getPrice());
        int result=cartService.updateCart(c);
        if(result==1){
            return new SuccessResponse("success");
        }
        return new ErrorResponse("error","添加购物车失败");
    }

    /**
     * 查询购物车中所有商品
     * 参数 user
     * 返回 success
     */
    @ResponseBody
    @RequestMapping("/getCart")
    public ResponseUtil getCart(@RequestBody JSONObject j,HttpServletRequest req){
        ResponseUtil responseUtil=this.checkUserLogin(j,req);
        if(responseUtil!=null){
            return responseUtil;
        }
        User user=(User)req.getSession().getAttribute("session");
        List<Cart> cartList=cartService.getCartByAccount(user.getAccount());
        SuccessResponse r= new SuccessResponse("success");
        r.addMessage("cart",cartList);
        return r;
    }

    /**
     * 从购物车修改商品
     * 参数 user + 购物车id+count
     * 返回 success
     */
    @ResponseBody
    @RequestMapping("/updateCount_order")
    public ResponseUtil updateCount_order(@RequestBody JSONObject j,HttpServletRequest req){
        ResponseUtil r=this.checkUserLogin(j,req);
        if(r!=null){
            return r;
        }
        //获取cart id
        Long cartId=Long.parseLong(j.get("id").toString());
        //数据库查找cart
        Cart cart=cartService.findCartById(cartId);
        if(cart!=null){
            //根据cartId找到的账号必须与user账号相同
            User user=(User)req.getSession().getAttribute("session");
            if(!user.getAccount().equals(cart.getAccount())){
                return new ErrorResponse("购物车与账号不匹配");
            }
            //count=-1 或 count=1
            int count=(int)j.get("count");
            if(count==1||count==-1){
                cart.setCount(cart.getCount()+count);
                cart.setCost(cart.getCount()*cart.getPrice());
                /*if(cart.getCount()<0){
                    cartService.deleteCartById(cart.getId());
                }*/
                int result=cartService.updateCart(cart);
                if(result==1){
                    return new SuccessResponse("success");
                }
            }
            return new ErrorResponse("数量不正确，count必须是1或-1");
        }
        return new ErrorResponse("error","updateCount接口出现错误");
    }


    @ResponseBody
    @RequestMapping("/updateCount_noorder")//直接修改购物车商品数量
    public ResponseUtil updateCount_noorder(@RequestBody JSONObject j,HttpServletRequest req){
        ResponseUtil r=this.checkUserLogin(j,req);
        if(r!=null){
            return r;
        }
        Long cartId=Long.parseLong(j.get("id").toString());
        Cart cart=cartService.findCartById(cartId);
        if(cart!=null){
            //根据cartId找到的账号必须与user账号相同
            User user=(User)req.getSession().getAttribute("session");
            if(!user.getAccount().equals(cart.getAccount())){
                return new ErrorResponse("购物车与账号不匹配");
            }
            int count=(int)j.get("count");
            if(count<=0){
                return new ErrorResponse("数量不合法");
            }
            cart.setCount(count);
            cart.setCost(cart.getCount()*cart.getPrice());
            cartService.updateCart(cart);
            return new SuccessResponse();
        }
        return new ErrorResponse("error","修改数量错误");
    }


    @ResponseBody
    @RequestMapping("/clearCart")//根据账号清空购物车
    public ResponseUtil clearCart(String account,HttpServletRequest req){
        account=Base64.decodeStr(account);
        User user=(User)req.getSession().getAttribute("session");
        if(user==null){
            return new ErrorResponse("用户未登录");
        }
        if(!user.getAccount().equals(account)){
            return new ErrorResponse("用户不一致");
        }
        int result=cartService.deleteCartByAccount(user.getAccount());
        if(result!=-1){
            return new SuccessResponse();
        }
        return new ErrorResponse("清空购物车失败");
    }


    @ResponseBody
    @RequestMapping("/deleteCart")//删除购物车中的某个商品
    public ResponseUtil deleteCart(String account,Long id,HttpServletRequest req){
        account=Base64.decodeStr(account);
        User user=(User)req.getSession().getAttribute("session");
        if(user==null){
            return new ErrorResponse("用户未登录");
        }
        if(!user.getAccount().equals(account)){
            return new ErrorResponse("账号不一致");
        }
        Cart cart=cartService.findCartById(id);
        if(cart.getAccount().equals(user.getAccount())){
            int result=cartService.deleteCartById(id);
            if(result==1){
                return new SuccessResponse();
            }
        }
        return new ErrorResponse("删除错误");
    }


    //判断参数是否正确
    public ResponseUtil checkUserLogin(JSONObject j,HttpServletRequest req){
        if(j==null){
            return new ErrorResponse("参数为空");
        }
        String account=(String)j.get("account");
        account=Base64.decodeStr(account);
        User user=(User)req.getSession().getAttribute("session");
        if(user==null){
            return new ErrorResponse("用户未登录");
        }
        if(!user.getAccount().equals(account)){
            return new ErrorResponse("用户不一致");
        }
        //没有出现错误
        return null;
    }
}
