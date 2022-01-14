package com.ppx.mall.config;

import com.ppx.mall.interceptor.CartInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
  /*  @Autowired
    CartInterceptor cartInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration a=registry.addInterceptor(cartInterceptor);
    }*/
}
