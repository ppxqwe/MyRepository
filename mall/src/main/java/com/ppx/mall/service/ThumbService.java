package com.ppx.mall.service;

import com.ppx.mall.bean.Thumb;


public interface ThumbService {

    int addThumb(Thumb thumb);

    Thumb findThumb(String account,Long commentId);

    int deleteThumbById(Long id);
}
