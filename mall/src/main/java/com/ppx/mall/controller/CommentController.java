package com.ppx.mall.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.Comment;
import com.ppx.mall.bean.Thumb;
import com.ppx.mall.bean.User;
import com.ppx.mall.service.CommentService;
import com.ppx.mall.service.ThumbService;
import com.ppx.mall.service.UserService;
import com.ppx.mall.util.ErrorResponse;
import com.ppx.mall.util.ResponseUtil;
import com.ppx.mall.util.SuccessResponse;
import com.ppx.mall.viewObject.ViewComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    ThumbService thumbService;

    @ResponseBody
    @RequestMapping("/addComment")
    public ResponseUtil addComment(@RequestBody JSONObject json){
        try{
            Long productId = Long.parseLong(json.get("productId").toString());
            String account=Base64.decodeStr(json.get("account").toString());
            String content=json.get("comment").toString();
            Long time=Long.parseLong(json.get("time").toString());
            if(!StrUtil.isEmpty(account)&& !StrUtil.isEmpty(content)){
                Comment comment=new Comment(
                        null,productId, account, content, time, 0, 0, null, null
                );
                Comment result=commentService.addComment(comment);
                if(result!=null){
                    SuccessResponse s=new SuccessResponse();
                    s.addMessage("id",result.getId());
                    return s;
                }
            }
        }catch (Exception e){
            System.out.println("添加评论异常");
            e.printStackTrace();
        }
        return new ErrorResponse("添加评论失败");
    }

    @ResponseBody
    @PostMapping("/getComment")//返回所有一级评论
    public ResponseUtil getComment(@RequestBody JSONObject json){
        try {
            Long productId=Long.parseLong(json.get("productId").toString());
            Object o=json.get("account");
            String account=null;
            if(o!=null){
                account= Base64.decodeStr(o.toString());
            }
            List<Comment> commentList=commentService.findCommentByProductIdAndTypeOne(productId);//查询所有一级评论
            List<ViewComment> commentResult=new ArrayList<>();;
            if(commentList!=null&&commentList.size()>0){
                for(Comment c:commentList){
                    User user=userService.getUser(c.getAccount()); //找到该评论的用户信息
                    if(user!=null){
                        if(StrUtil.isEmpty(user.getAvatarSrc())){
                            user.setAvatarSrc("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
                        }
                        ViewComment vc=new ViewComment();
                        vc.setAvatar(user.getAvatarSrc()); //
                        vc.setComment(c.getContent());
                        vc.setId(c.getId());
                        vc.setThumb(c.getThumb());
                        vc.setTime(c.getTime());
                        vc.setType(c.getType());
                        vc.setUserName(user.getUserName());//
                        List<Comment> comments=commentService.findCommentByIdAndTypeTwo(c.getId());//添加回复该评论的数量
                        vc.setReplayCount(comments==null?0:comments.size());
                        //设置该评论是否为已点赞评论
                        if(!StrUtil.isEmpty(account)){
                            Thumb thumb=thumbService.findThumb(account,c.getId());
                            if(thumb!=null){
                                vc.setIsLiked(true);
                            }
                        }
                        commentResult.add(vc);
                    }
                }
            }
            SuccessResponse s=new SuccessResponse();
            s.addMessage("comments",commentResult);
            return s;
        }catch (Exception e){
            System.out.println("获取评论异常");
            e.printStackTrace();
        }
        return new ErrorResponse("获取评论异常");
    }

    @ResponseBody
    @RequestMapping("/addReplayComment") //向某条评论添加回复评论
    public ResponseUtil addReplayComment(@RequestBody JSONObject json){
        Long id=Long.parseLong(json.get("commentId").toString());
        String account=Base64.decodeStr(json.get("account").toString());
        Long productId=Long.parseLong(json.get("productId").toString());
        String content=json.get("comment").toString();
        Long time=Long.parseLong(json.get("time").toString());
        if(account!=null){
            Comment comment=commentService.findCommentById(id);//被回复的那条评论
            if(comment!=null){
                if(productId.equals(comment.getProductId())){
                    Comment c=new Comment();
                    c.setProductId(productId);
                    c.setAccount(account);
                    c.setContent(content);
                    c.setTime(time);
                    c.setType(1);
                    c.setThumb(0);
                    if(comment.getResponseId()==null){
                        c.setResponseId(comment.getId());//回复一级评论
                    } else{//回复二级评论
                        c.setResponseId(comment.getResponseId());
                        c.setResponseTwoId(comment.getId());
                    }
                    Comment result=commentService.addComment(c);
                    if(result!=null){
                        SuccessResponse s=new SuccessResponse();
                        s.addMessage("id",result.getId());
                        return s;
                    }
                }
            }
        }
        return new ErrorResponse("回复异常");
    }

    @ResponseBody
    @RequestMapping("/getReplayComment")
    public ResponseUtil getReplayComment(@RequestBody JSONObject json){
        Object o=json.get("account");
        String account=null;
        if(o!=null){
            account=Base64.decodeStr(o.toString());//账号留着以后做是否点赞的判断 非必要参数
        }
        Long id=Long.parseLong(json.get("id").toString());//被回复的那条评论的id
        List<Comment> commentList=commentService.findCommentByIdAndTypeTwo(id);
        List<ViewComment> commentResult=new ArrayList<>();
        if(commentList!=null&&commentList.size()>0) {
            for (Comment c : commentList) {
                User user=userService.getUser(c.getAccount()); //找到该二级评论的用户信息
                if(user!=null){
                    if(StrUtil.isEmpty(user.getAvatarSrc())){
                        user.setAvatarSrc("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
                    }
                    ViewComment vc=new ViewComment();
                    vc.setType(c.getType());
                    vc.setId(c.getId());
                    vc.setTime(c.getTime());
                    vc.setComment(c.getContent());
                    vc.setAvatar(user.getAvatarSrc());
                    vc.setUserName(user.getUserName());
                    vc.setThumb(c.getThumb());
                    if(c.getResponseId()!=null){
                        if(c.getResponseTwoId()!=null){
                            //二级回复二级
                            Long responseId=c.getResponseTwoId();
                            Comment comment=commentService.findCommentById(responseId);
                            User user1=userService.getUser(comment.getAccount());
                            if(user1!=null){
                                vc.setReplaiedName(user1.getUserName());
                            }
                        }else{
                            //二级回复一级
                            vc.setReplaiedName("");
                        }
                    }
                    if(!StrUtil.isEmpty(account)){
                        Thumb thumb=thumbService.findThumb(account,c.getId());
                        if(thumb!=null){
                            vc.setIsLiked(true);
                        }
                    }
                    //没有replayCount
                    vc.setReplayCount(0);
                    //设置该评论是否为已点赞评论
                    commentResult.add(vc);
                }
            }
        }
        SuccessResponse s=new SuccessResponse();
        s.addMessage("comments",commentResult);
        return s;
    }

    @ResponseBody
    @RequestMapping("/deleteComment")
    public ResponseUtil deleteComment(Long commentId){
        try{
            Comment comment=commentService.findCommentById(commentId);
            if(comment==null){
                return new ErrorResponse("评论不存在");
            }
            int result=commentService.deleteCommentById(commentId);//删除该评论
            List<Comment> commentList=null;
            //如果该评论是一级评论，删除所有responseId为该id的评论
            if(comment.getType()==0){
                commentList=commentService.findCommentsByResponseId(comment.getId());
            }
            //如果该评论是二级评论，删除所有responseTwoId为该id的评论
            if(comment.getType()==1){
                commentList=commentService.findCommentsByResponseTwoId(comment.getId());
            }
            if(commentList!=null&&commentList.size()>0){
                List<Long> ids=new ArrayList<>();
                for(Comment c:commentList){
                    ids.add(c.getId());
                }
                int result1=commentService.deleteCommentByIds(ids);
            }
            return new SuccessResponse();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("删除评论异常");
        }
        return new ErrorResponse("删除评论异常");
    }

    @ResponseBody
    @RequestMapping("/isLikeComment")
    public ResponseUtil isLikeComment(@RequestBody JSONObject json){
        try{
            String account=Base64.decodeStr(json.get("account").toString());
            Long commentId=Long.parseLong(json.get("commentId").toString());
            Comment comment=commentService.findCommentById(commentId);
            //查看是否点赞过
            Thumb thumb=thumbService.findThumb(account,commentId);
            int thumbResult=0;
            if(thumb==null){
                //如果没点赞 点赞数加一
                comment.setThumb(comment.getThumb()+1);
                thumbResult=thumbService.addThumb(new Thumb(null,account,commentId));
            }else{
                //如果点赞过 点赞数减一
                comment.setThumb(comment.getThumb()-1);
                thumbResult=thumbService.deleteThumbById(thumb.getId());
            }
            int commentResult=commentService.updateComment(comment);
            if(thumbResult==1&&commentResult==1){
                return new SuccessResponse();
            }
        }catch (Exception e){
            System.out.println("点赞异常");
            e.printStackTrace();
        }
        return new ErrorResponse("点赞异常");
    }
}
