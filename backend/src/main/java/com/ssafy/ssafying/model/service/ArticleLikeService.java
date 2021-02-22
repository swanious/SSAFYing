package com.ssafy.ssafying.model.service;

import java.util.List;

import com.ssafy.ssafying.model.dto.ArticleLikeDto;
import com.ssafy.ssafying.model.mapper.ArticleLikeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {
      
      @Autowired
      ArticleLikeMapper articleLikeMapper;

      public int articleLikeInsert(ArticleLikeDto articleLikeDto){
            return articleLikeMapper.articleLikeInsert(articleLikeDto);
      }
      public List<ArticleLikeDto> articleLikeSelectAll(){
            return articleLikeMapper.articleLikeSelectAll();
      }
      public int articleLikeDelete(int articleId, String userId){
            return articleLikeMapper.articleLikeDelete(articleId, userId);
      }
      public List<ArticleLikeDto> articleLikeSelectByArticleId(int articleId){
            return articleLikeMapper.articleLikeSelectByArticleId(articleId);
      }
      public int articleLikeCountByArticleId(int articleId){
            return articleLikeMapper.articleLikeSelectByArticleId(articleId).size();
      }
      public boolean articleLikeOnAndOff(int articleId,String userId){
            return articleLikeMapper.articleLikeOnAndOff(articleId, userId) > 0 ? true : false;
      }
}
