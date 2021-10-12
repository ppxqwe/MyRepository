package com.ppx.mall.service.impl;

import com.ppx.mall.bean.Slideshow;
import com.ppx.mall.dao.SlideshowDao;
import com.ppx.mall.service.SlideshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlideshowServiceImpl implements SlideshowService {
    @Autowired
    SlideshowDao slideshowDao;

    @Override
    public List<Slideshow> getSlideshw(List<Long> ids) {
        return slideshowDao.findSlideshowByIds(ids);
    }
}
