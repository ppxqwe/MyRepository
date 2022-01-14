package com.ppx.mall.dao;

import com.ppx.mall.bean.Thumb;

import java.util.List;
import java.util.Map;

public interface ThumbDao {
    int addThumb(Thumb thumb);
    List<Thumb> findThumbByCondition(Map<String,Object> map);
    int deleteThumbById(Long id);
}
