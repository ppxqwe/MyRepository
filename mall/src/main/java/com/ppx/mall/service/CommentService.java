package com.ppx.mall.service;

import com.ppx.mall.bean.Comment;
import com.ppx.mall.bean.Thumb;

import java.util.List;

public interface CommentService {

     Comment addComment(Comment comment);
     List<Comment> findCommentByProductIdAndTypeOne(Long productId);
     List<Comment> findCommentByIdAndTypeTwo(Long commentId);
     Comment findCommentById(Long id);
     int deleteCommentById(Long id);
     List<Comment> findCommentsByResponseId(Long id);
     List<Comment> findCommentsByResponseTwoId(Long id);
     int deleteCommentByIds(List<Long> ids);
     int updateComment(Comment comment);
}
