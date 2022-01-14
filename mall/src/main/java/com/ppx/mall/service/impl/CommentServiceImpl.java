package com.ppx.mall.service.impl;

import com.ppx.mall.bean.Comment;
import com.ppx.mall.bean.Thumb;
import com.ppx.mall.dao.CommentDao;
import com.ppx.mall.dao.ThumbDao;
import com.ppx.mall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    public Comment addComment(Comment comment){
        int result=commentDao.addComment(comment);
        if(result==1){
            Map<String,Object> map=new HashMap<>();
            map.put("productId",comment.getProductId());
            map.put("account",comment.getAccount());
            map.put("time",comment.getTime());
            List<Comment> commentList=commentDao.findCommentByCondition(map);
            if(commentList!=null&&commentList.size()>=1){
                return commentList.get(0);
            }
        }
        return null;
    }

    public List<Comment> findCommentByProductIdAndTypeOne(Long productId){
        Map<String,Object> map=new HashMap<>();
        map.put("productId",productId);
        map.put("type",0);
        return commentDao.findCommentByCondition(map);
    }

    public List<Comment> findCommentByIdAndTypeTwo(Long commentId){
        Map<String,Object> map=new HashMap<>();
        map.put("responseId",commentId);
        map.put("type",1);
        return commentDao.findCommentByCondition(map);
    }

    @Override
    public Comment findCommentById(Long id) {
        return commentDao.findCommentById(id);
    }

    @Override
    public int deleteCommentById(Long id) {
        return commentDao.deleteCommentById(id);
    }

    @Override
    public List<Comment> findCommentsByResponseId(Long id) {
        Map<String,Object> map=new HashMap<>();
        map.put("responseId",id);
        return commentDao.findCommentByCondition(map);
    }

    @Override
    public List<Comment> findCommentsByResponseTwoId(Long id) {
        Map<String,Object> map=new HashMap<>();
        map.put("responseTwoId",id);
        return commentDao.findCommentByCondition(map);
    }

    @Override
    public int deleteCommentByIds(List<Long> ids) {
        return commentDao.deleteCommentByIds(ids);
    }

    @Override
    public int updateComment(Comment comment) {
        return commentDao.updateComment(comment);
    }

}
