package com.ppx.mall.expection;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class MyExpection{
    //@ExceptionHandler(Exception.class)
    public ModelAndView handler(Exception e){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("error");
        mv.addObject("message",e.getMessage());
        return mv;
    }
}
