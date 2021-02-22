package com.ssafy.ssafying.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.CommentDto;
import com.ssafy.ssafying.model.dto.UserDto;
import com.ssafy.ssafying.model.mapper.ArticleMapper;
import com.ssafy.ssafying.model.mapper.CommentMapper;
import com.ssafy.ssafying.model.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

      @Autowired
      CommentMapper commentMapper;

      @Autowired
      UserMapper userMapper;

      @Autowired
      ArticleMapper articleMapper;

      public List<CommentDto> commentSelectAll() {
            return commentMapper.commentSelectAll();
      }

      @Transactional
      public List<CommentDto> commentInsert(CommentDto commentDto) {
            commentMapper.commentInsert(commentDto);
            return commentMapper.commentSelectByArticleId(commentDto.getArticleId());
      }

      @Transactional
      public List<CommentDto> commentDelete(int commentId) {
            CommentDto commentDto = commentMapper.commentSelectByCommentId(commentId);
            commentMapper.commentDelete(commentId);
            return commentMapper.commentSelectByArticleId(commentDto.getArticleId());
      }

      @Transactional
      public List<CommentDto> commentUpdate(CommentDto commentDto) {
            commentMapper.commentUpdate(commentDto);
            return commentMapper.commentSelectByArticleId(commentDto.getArticleId());
      }

      @Transactional
      public List<CommentDto> commentSelectByArticleId(int articleId) throws SQLException {
            List<CommentDto> commentDtos = commentMapper.commentSelectByArticleId(articleId);
            for(CommentDto commentDto: commentDtos){
                  commentDto.setNickname(userMapper.userSelectOne(commentDto.getUserId()).getNickname());
                  if(articleMapper.articleSelectOne(articleId).getSubCategory().equals("anonymous")){
                        commentDto.setNickname("익명");
                  }
            }
            return commentDtos;
      }

      @Transactional
      public List<CommentDto> commentSelectByNickname(String nickname) throws SQLException {
            Map<String,Object> nicknameMap = new HashMap<>();
            nicknameMap.put("key","nickname");
            nicknameMap.put("word",nickname);
            UserDto getUserDto = userMapper.userSelectByKeyWord(nicknameMap);
            return commentMapper.commentSelectByUserId(getUserDto.getUserId());
      }
}
