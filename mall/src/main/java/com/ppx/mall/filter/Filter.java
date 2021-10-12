package com.ppx.mall.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="customFilter",urlPatterns={"/*"})
public class Filter implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        try {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.setHeader("Access-Control-Allow-Origin", "*");
            filterChain.doFilter(servletRequest, servletResponse);
        }catch(Exception e){
            System.out.println("出现异常");
        }
    }

    @Override
    public void destroy() {

    }
}
