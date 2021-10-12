package com.ppx.mall.dao;

import com.ppx.mall.bean.Slideshow;

import java.util.List;

public interface SlideshowDao {
    List<Slideshow> findSlideshowByIds(List<Long> ids);
}
