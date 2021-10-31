package com.ppx.mall.interceptor;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.util.Constants;
import com.ppx.mall.util.ResponseUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                      HttpServletResponse response, Object handler)
            throws Exception {
        Cookie[] cookies= request.getCookies();
        JSONObject j=new JSONObject();
        if(cookies==null){
            ResponseUtil r=new ResponseUtil();
            r.setStatus("error");
            r.addMessage("reason","cookie is null");
            j.put("responseUtil",r);
            response.getWriter().write(j.toJSONString());
            return false;
        }
        String JSESSIONID=null;
        String token = null;
        for(Cookie cookie:cookies){
            if(Constants.SERVER_COOKIE.equals(cookie.getName())){
                token=cookie.getValue();
            }
            if("JSESSIONID".equals(cookie.getName())){
                JSESSIONID=cookie.getValue();
            }
        }
        if(token==null||JSESSIONID==null){
            ResponseUtil r=new ResponseUtil();
            r.setStatus("error");
            r.addMessage("reason","cookie is error");
            j.put("responseUtil",r);
            response.getWriter().write(j.toJSONString());
            return false;
        }
        if(token.equals(DigestUtil.md5Hex(JSESSIONID+Constants.SERVER_TOKEN))){
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                    HttpServletResponse response, Object handler,
                    @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {


    }

}
