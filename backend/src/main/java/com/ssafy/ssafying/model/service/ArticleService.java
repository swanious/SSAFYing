package com.ssafy.ssafying.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.controller.ArticleController;
import com.ssafy.ssafying.model.dto.ArticleDto;
import com.ssafy.ssafying.model.dto.ArticleSearchDto;
import com.ssafy.ssafying.model.dto.UserDto;
import com.ssafy.ssafying.model.mapper.ArticleLikeMapper;
import com.ssafy.ssafying.model.mapper.ArticleMapper;
import com.ssafy.ssafying.model.mapper.CommentMapper;
import com.ssafy.ssafying.model.mapper.UserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

      public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

      @Autowired
      private ArticleMapper articleMapper;

      @Autowired
      private UserMapper userMapper;

      @Autowired
      private ArticleLikeMapper articleLikeMapper;

      @Autowired
      private CommentMapper commentMapper;

      @Autowired
      private S3Service s3Service;

      @Transactional
      public List<ArticleDto> articleInsert(ArticleDto articleDto) throws Exception {
            if( articleDto.getArticleImage() != null && !articleDto.getArticleImage().equals("")){
                  articleDto.setArticleImage(s3Service.upload(articleDto.getArticleImage(), Long.toString(new Date().getTime())));
            }
            articleMapper.articleInsert(articleDto);
            return articleMapper.articleSelectAll();
      }

      public List<ArticleDto> articleSelectAll() {
            return articleMapper.articleSelectAll();
      }

      public List<ArticleDto> articleDelete(int articleId) {
            articleMapper.articleDelete(articleId);
            return articleMapper.articleSelectAll();
      }

      public int articleUpdate(ArticleDto articleDto) throws Exception {
            if( articleDto.getArticleImage() != null && !articleDto.getArticleImage().equals("")){
                  articleDto.setArticleImage(s3Service.upload(articleDto.getArticleImage(), Long.toString(new Date().getTime())));
            }
            return articleMapper.articleUpdate(articleDto);
      }

      public ArticleDto articleSelectOne(int articleId) {
            return articleMapper.articleSelectOne(articleId);
      }

      public Map<String,Object> articleSelectOneSpecial(int articleId) throws Exception {
            Map<String, Object> resultMap = new HashMap<>();
            ArticleDto thisArticle = articleMapper.articleSelectOne(articleId);
            resultMap.put("data", thisArticle);
            resultMap.put("articleLikeCount",(articleLikeMapper.articleLikeSelectByArticleId(articleId)).size());
            resultMap.put("commentCount",(commentMapper.commentSelectByArticleId(articleId).size()));
            resultMap.put("nickname",userMapper.userSelectOne(thisArticle.getUserId()).getNickname());
            if(thisArticle.getSubCategory().equals("anonymous")){
                  resultMap.put("nickname","익명");
            }
            return resultMap;
      }

      public List<ArticleSearchDto> articleSelectByMainCategory(String mainCategory) throws SQLException {
            List<ArticleDto> articleDtos = articleMapper.articleSelectByMainCategory(mainCategory);
            List<ArticleSearchDto> articleSearchDtos = new ArrayList<>();
            for(ArticleDto articleDto : articleDtos){
                  ArticleSearchDto articleSearchDto = new ArticleSearchDto();
                  articleSearchDto.setArticleDto(articleDto);
                  articleSearchDto.setArticleCommentCount(commentMapper.commentSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setArticleLike(articleLikeMapper.articleLikeSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setNickname(userMapper.userSelectOne(articleDto.getUserId()).getNickname());
                  if(articleDto.getSubCategory().equals("anonymous")){
                        articleSearchDto.setNickname("익명");
                  }
                  articleSearchDtos.add(articleSearchDto);
            }
            return articleSearchDtos;
      }

      public List<ArticleSearchDto> articleSelectBySubCategory(String subCategory) throws SQLException {
            List<ArticleDto> articleDtos = articleMapper.articleSelectBySubCategory(subCategory);
            List<ArticleSearchDto> articleSearchDtos = new ArrayList<>();
            for(ArticleDto articleDto : articleDtos){
                  ArticleSearchDto articleSearchDto = new ArticleSearchDto();
                  articleSearchDto.setArticleDto(articleDto);
                  articleSearchDto.setArticleCommentCount(commentMapper.commentSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setArticleLike(articleLikeMapper.articleLikeSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setNickname(userMapper.userSelectOne(articleDto.getUserId()).getNickname());
                  if(articleDto.getSubCategory().equals("anonymous")){
                        articleSearchDto.setNickname("익명");
                  }
                  articleSearchDtos.add(articleSearchDto);
            }
            return articleSearchDtos;
      }

      public List<ArticleDto> articleSelectByNickname(String nickname) throws SQLException {
            Map<String,Object> nicknameMap = new HashMap<>();
            nicknameMap.put("key","nickname");
            nicknameMap.put("word",nickname);
            UserDto getUserDto = userMapper.userSelectByKeyWord(nicknameMap);
            return articleMapper.articleSelectByUserId(getUserDto.getUserId());
      }

      public List<ArticleSearchDto> articleSearch(String tag) throws SQLException {
            List<ArticleDto> articleDtos = articleMapper.articleSearch(tag, tag);
            List<ArticleSearchDto> articleSearchDtos = new ArrayList<>();
            for(ArticleDto articleDto : articleDtos){
                  ArticleSearchDto articleSearchDto = new ArticleSearchDto();
                  articleSearchDto.setArticleDto(articleDto);
                  articleSearchDto.setArticleCommentCount(commentMapper.commentSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setArticleLike(articleLikeMapper.articleLikeSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setNickname(userMapper.userSelectOne(articleDto.getUserId()).getNickname());
                  if(articleDto.getSubCategory().equals("anonymous")){
                        articleSearchDto.setNickname("익명");
                  }
                  articleSearchDtos.add(articleSearchDto);
            }
            return articleSearchDtos;
      }

      public List<ArticleSearchDto> articleSearchAccuracy(String tag) throws SQLException {
            List<ArticleDto> getArticleDtos = articleMapper.articleSelectAll();
            List<ArticleDto> articleDtos = new ArrayList<>();
            for(ArticleDto article : getArticleDtos){
                  List<Integer> titleCountAndTerm = KMPalgorithm(article.getTitle(), tag);
                  List<Integer> contentCountAndTerm = KMPalgorithm(article.getContent(), tag);
                  int score = titleCountAndTerm.get(0) * 4 + contentCountAndTerm.get(0);
                  int term = titleCountAndTerm.get(1) * 2 + contentCountAndTerm.get(1);
                  if(score == 0){
                        continue;
                  }
                  article.setScore(score);
                  article.setComment(term);
                  articleDtos.add(article);
            }
            Collections.sort(articleDtos, new Comparator<ArticleDto>(){ //전체 스코어로 정렬
                  @Override
                  public int compare(ArticleDto article1, ArticleDto article2){
                        if(article1.getScore() == article2.getScore()){
                              return article1.getComment() > article2.getComment() ? -1 : 1;
                        }
                        return article1.getScore() > article2.getScore() ? -1 : 1;
                  }
            });
            List<ArticleSearchDto> articleSearchDtos = new ArrayList<>();
            for(ArticleDto articleDto : articleDtos){
                  ArticleSearchDto articleSearchDto = new ArticleSearchDto();
                  articleSearchDto.setArticleDto(articleDto);
                  articleSearchDto.setArticleCommentCount(commentMapper.commentSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setArticleLike(articleLikeMapper.articleLikeSelectByArticleId(articleDto.getArticleId()).size());
                  articleSearchDto.setNickname(userMapper.userSelectOne(articleDto.getUserId()).getNickname());
                  if(articleDto.getSubCategory().equals("anonymous")){
                        articleSearchDto.setNickname("익명");
                  }
                  articleSearchDtos.add(articleSearchDto);
            }
            return articleSearchDtos;
      }

      public static ArrayList<Integer> KMPalgorithm(String obj, String query) { // obj에 query가 몇 개 있는지, 간격이 얼마나 되는지를 연산하여 반환
		
		ArrayList<Integer> resultList = new ArrayList<>();
		int [] mc = getMatchCount(query);
		int objLength = obj.length();
		int queryLength = query.length();
		int j = 0;
		int startInteger = -1;
            int endInteger = -1;
            int count = 0;
		for(int i=0; i < objLength; i++) {                 
			while(j > 0 && obj.charAt(i)!= query.charAt(j)) {// important
				j = mc[j-1];
			}if(obj.charAt(i)== query.charAt(j)) {
				if(j == queryLength-1) {
                              if(startInteger == -1){
                                    startInteger = i-queryLength+1;
                              }
                              endInteger = i-queryLength+1;
                              count++;
					j = mc[j];
				}else {
					j++;
				}
			}
		}
            resultList.add(count);
            resultList.add(endInteger-startInteger);
		return resultList;
	}

      public static int[] getMatchCount(String query){
            int queryLength = query.length();
            int [] matchcount = new int[queryLength];
            int j = 0;
            for(int i = 1 ; i < queryLength ; i++){
                  while(j > 0 && query.charAt(i) != query.charAt(j)){
                        j = matchcount[j-1];
                  }if(query.charAt(i) == query.charAt(j)){
                        matchcount[i] = ++j;
                  }
            }
            return matchcount;
      }
}
