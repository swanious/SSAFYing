package com.ssafy.ssafying.model.mapper;

import java.util.List;

import com.ssafy.ssafying.model.dto.ArticleDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
      public int articleInsert(ArticleDto articleDto);
      public List<ArticleDto> articleSelectAll();
      public int articleDelete(int articleId);
      public int articleUpdate(ArticleDto articleDto);
      public ArticleDto articleSelectOne(int articleId);
      public List<ArticleDto> articleSelectByMainCategory(String mainCategory);
      public List<ArticleDto> articleSelectBySubCategory(String subCategory);
      public List<ArticleDto> articleSelectByUserId(String nickname);
      public List<ArticleDto> articleSelectByKeyValue(String key,String value,String order, String limit);
      public List<ArticleDto> articleSearch(String title,String content);
}
