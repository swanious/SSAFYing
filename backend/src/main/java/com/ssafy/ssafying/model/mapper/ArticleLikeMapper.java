package com.ssafy.ssafying.model.mapper;

import java.util.List;

import com.ssafy.ssafying.model.dto.ArticleLikeDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeMapper {
      public int articleLikeInsert(ArticleLikeDto articleLikeDto);
      public List<ArticleLikeDto> articleLikeSelectAll();
      public int articleLikeDelete(int articleId,String userId);
      public List<ArticleLikeDto> articleLikeSelectByArticleId(int articleId);
      public int articleLikeOnAndOff(int articleId,String userId);
      public List<ArticleLikeDto> articleLikeSelectLimit(String limit);
}
