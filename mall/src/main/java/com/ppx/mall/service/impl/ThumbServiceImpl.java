package com.ppx.mall.service.impl;

import com.ppx.mall.bean.Thumb;
import com.ppx.mall.dao.ThumbDao;
import com.ppx.mall.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ThumbServiceImpl implements ThumbService {

    @Autowired
    ThumbDao thumbDao;

    @Override
    public int addThumb(Thumb thumb) {
        return thumbDao.addThumb(thumb);
    }

    @Override
    public Thumb findThumb(String account, Long commentId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",account);
        map.put("commentId",commentId);
        List<Thumb> thumbList= thumbDao.findThumbByCondition(map);
        if(thumbList!=null&&thumbList.size()>0){
            return thumbList.get(0);
        }
        return null;
    }

    @Override
    public int deleteThumbById(Long id) {
        return thumbDao.deleteThumbById(id);
    }
}
