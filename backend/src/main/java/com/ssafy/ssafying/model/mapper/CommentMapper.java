package com.ssafy.ssafying.model.mapper;

import java.util.List;

import com.ssafy.ssafying.model.dto.CommentDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
      public List<CommentDto> commentSelectAll();
      public int commentInsert(CommentDto commentDto);
      public int commentUpdate(CommentDto commentDto);
      public List<CommentDto> commentSelectByArticleId(int articleId);
      public int commentDelete(int commentId);
      public CommentDto commentSelectRecent();
      public CommentDto commentSelectByCommentId(int commentId);
      public List<CommentDto> commentSelectByUserId(String nickname);
      public List<CommentDto> commentSelectLimit(String limit);
}
