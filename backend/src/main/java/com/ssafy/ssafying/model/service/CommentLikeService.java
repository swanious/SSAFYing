package com.ssafy.ssafying.model.service;

import java.util.List;

import com.ssafy.ssafying.model.dto.CommentLikeDto;
import com.ssafy.ssafying.model.mapper.CommentLikeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
      
      @Autowired
      CommentLikeMapper commentLikeMapper;

      public List<CommentLikeDto> commentLikeSelectAll(){
            return commentLikeMapper.commentLikeSelectAll();
      }

      public int commentLikeInsert(CommentLikeDto commentLikeDto){
            commentLikeMapper.commentLikeInsert(commentLikeDto);
            return commentLikeMapper.commentLikeSelectByCommentId(commentLikeDto.getCommentId()).size();
      }

      public int commentLikeDelete(int commentId, String userId){
            commentLikeMapper.commentLikeDelete(commentId, userId);
            return commentLikeMapper.commentLikeSelectByCommentId(commentId).size();
      }

      public boolean commentLikeOnAndOff(int commentId,String userId){
            return commentLikeMapper.commentLikeOnAndOff(commentId, userId) > 0 ? true : false;
      }

      public int commentLikeCountByCommentId(int commentId){
            return commentLikeMapper.commentLikeSelectByCommentId(commentId).size();
      }
}
