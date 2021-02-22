package com.ssafy.ssafying.model.mapper;

import java.util.List;

import com.ssafy.ssafying.model.dto.CommentLikeDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeMapper {
      public List<CommentLikeDto> commentLikeSelectAll();
      public int commentLikeInsert(CommentLikeDto commentLikeDto);
      public List<CommentLikeDto> commentLikeSelectByCommentId(int commentId);
      public int commentLikeDelete(int commentId, String userId);
      public int commentLikeOnAndOff(int commentId, String userId);
}
