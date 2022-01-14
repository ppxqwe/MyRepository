package com.ppx.mall.dao;

import com.ppx.mall.bean.Comment;

import java.util.List;
import java.util.Map;

public interface CommentDao {
    int addComment(Comment comment);

    List<Comment> findCommentByCondition(Map<String,Object> map);

    Comment findCommentById(Long id);

    int deleteCommentById(Long id);

    int deleteCommentByIds(List<Long> ids);

    int updateComment(Comment comment);
}
