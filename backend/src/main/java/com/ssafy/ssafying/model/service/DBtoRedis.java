package com.ssafy.ssafying.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ssafying.model.dto.ArticleDto;
import com.ssafy.ssafying.model.dto.ArticleLikeDto;
import com.ssafy.ssafying.model.dto.CommentDto;
import com.ssafy.ssafying.model.mapper.ArticleLikeMapper;
import com.ssafy.ssafying.model.mapper.ArticleMapper;
import com.ssafy.ssafying.model.mapper.CommentMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class DBtoRedis {
      public static final Logger logger = LoggerFactory.getLogger(DBtoRedis.class);

      @Autowired
      ArticleMapper articleMapper;

      @Autowired
      CommentMapper CommentMapper;

      @Autowired
      ArticleLikeMapper articleLikeMapper;

      @Autowired
      RedisTemplate<String, Object> redisTemplate;

      @Scheduled(fixedDelay = 1000 * 60 * 5, initialDelay = 1000) // 5분마다 작동
      public void setRedisFromDB() throws ParseException {
            logger.info("DB에서 Data를 Road합니다.");
            // 게시판
            registerArticleHash("SubCategory", "free", "createdAt", "4");
            registerArticleHash("SubCategory", "anonymous", "createdAt", "4");
            registerArticleHash("SubCategory", "grade", "createdAt", "4");
            // 정보
            registerArticleHash("SubCategory", "it", "createdAt", "4");
            registerArticleHash("SubCategory", "job", "createdAt", "4");
            registerArticleHash("SubCategory", "life", "createdAt", "4");
            // 싸피장터
            registerArticleHash("MainCategory", "market", "createdAt", "4");
            // 인기글
            registerRecommendHash();

            SetOperations<String, Object> sosop = redisTemplate.opsForSet();
            List<String> mainList = new ArrayList<String>();
            mainList.add("free");
            mainList.add("anonymous");
            mainList.add("grade");
            mainList.add("it");
            mainList.add("job");
            mainList.add("life");
            mainList.add("market");
            // 위 리스트로 반복문을 돌려도 될 것 같지만, Main Sub 구분이 애매해서 보류
            for (String mainName : mainList) {
                  sosop.add("mainList", mainName);
            }

      }

      public void registerArticleHash(String key, String value, String order, String limit) {
            List<ArticleDto> articleList = getArticleByKeyValue(key, value, order, limit);

            HashOperations<String, Object, Object> soohop = redisTemplate.opsForHash();
            SetOperations<String, Object> sosop = redisTemplate.opsForSet();

            int t = 1;
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
            };
            for (ArticleDto article : articleList) {
                  String nameOfMain = value + t;
                  Map<String, Object> articleMap = objectMapper.convertValue(article, mapType);
                  soohop.putAll(nameOfMain, articleMap);
                  sosop.add(value, nameOfMain);
                  t++;
            }

      }

      public void registerRecommendHash() throws ParseException {
            List<ArticleDto> articleDtos = getArticleList();
            Collections.sort(articleDtos,new Comparator<ArticleDto>(){ // 리스트에서 조회수 순으로 정렬
                  @Override
                  public int compare(ArticleDto article1, ArticleDto article2){
                       return article1.getWatchCount() > article2.getWatchCount() ? -1 : 1;
                  }
            });
            int weight = 10; // 조회수 가중치
            for(ArticleDto article : articleDtos){
                  article.setScore(article.getScore()+weight);
                  weight -= 1;
                  if(weight <= 0 ){
                        break;
                  }
            }

            weight = 20; // 댓글 가중치
            List<CommentDto> commentDtos = getCommentList();
            Map<String,Integer> commentScore = new HashMap<>();
            for(CommentDto commentDto : commentDtos){ // 댓글을 긁어와서 아티클에 점수를 매김
                  if(commentDto.getContent().length()>=15){
                        int articleId = commentDto.getArticleId();
                        int score = commentScore.get((Object)Integer.valueOf(articleId)) == null ? 0 : commentScore.get((Object)Integer.valueOf(articleId)).intValue();
                        commentScore.put(String.valueOf(articleId), Integer.valueOf(score+1));
                  }
            }
            for(ArticleDto article : articleDtos){
                  Integer o = commentScore.get((Object)String.valueOf(article.getArticleId()));
                  if(o == null){
                        continue;
                  }
                  article.setComment(o.intValue());
            }
            Collections.sort(articleDtos,new Comparator<ArticleDto>(){ // 리스트에서 댓글 수 순으로 정렬
                  @Override
                  public int compare(ArticleDto article1, ArticleDto article2){
                       return article1.getComment() > article2.getComment() ? -1 : 1;
                  }
            });
            for(ArticleDto article : articleDtos){
                  article.setScore(article.getScore()+weight);
                  weight -= 2;
                  if(weight <= 0 ){
                        break;
                  }
            }
            weight = 70; // 좋아요 가중치
            List<ArticleLikeDto> articleLikeDtos = getArticleLikeList();
            Map<String,Integer> articleLikeScore = new HashMap<>();
            for(ArticleLikeDto articleLikeDto : articleLikeDtos){ // 댓글을 긁어와서 아티클에 점수를 매김                  
                  int articleId = articleLikeDto.getArticleId();
                  int score = articleLikeScore.get((Object)Integer.valueOf(articleId)) == null ? 0 : articleLikeScore.get((Object)Integer.valueOf(articleId)).intValue();
                  articleLikeScore.put(String.valueOf(articleId), Integer.valueOf(score+1));
            }
            for(ArticleDto article : articleDtos){
                  article.setComment(0); // 댓글 개수 초기화
            }
            for(ArticleDto article : articleDtos){
                  Integer o = articleLikeScore.get((Object)String.valueOf(article.getArticleId()));
            if(o == null){
                        continue;
                  }
                  article.setComment(o.intValue()); // 메모리 편의상 좋아요 개수를 comment에 저장
            }
            Collections.sort(articleDtos, new Comparator<ArticleDto>(){ // 리스트에서 좋아요 순으로 정렬
                  @Override
                  public int compare(ArticleDto article1, ArticleDto article2){
                        return article1.getComment() > article2.getComment() ? -1 : 1;
                  }
            });
            for(ArticleDto article : articleDtos){
                  article.setScore(article.getScore()+weight);
                  if(weight >= 7){
                        weight -= 7;
                  }
                  Date date=java.util.Calendar.getInstance().getTime();
                  SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  Date articleTime = transFormat.parse(article.getCreatedAt());
                  long timeSub = date.getTime() - articleTime.getTime();
                  long diffDays = (timeSub / (1000 * 60 * 60 * 24));
                  article.setScore((int)(article.getScore() * Math.pow(0.9, diffDays)));
            }
            Collections.sort(articleDtos, new Comparator<ArticleDto>(){ //전체 스코어로 정렬
                  @Override
                  public int compare(ArticleDto article1, ArticleDto article2){
                        return article1.getScore() > article2.getScore() ? -1 : 1;
                  }
            });
            ListOperations<String,Object> solop = redisTemplate.opsForList();
            HashOperations<String,Object,Object> soohop = redisTemplate.opsForHash(); 
            while(solop.size("recommend")!=0){
                  solop.leftPop("recommend");
            }
            int t = 1;
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
            };
            for(ArticleDto article : articleDtos){
                  String nameOfMain = "recommend" + t;
                  Map<String, Object> articleMap = objectMapper.convertValue(article, mapType);
                  soohop.putAll(nameOfMain, articleMap);
                  solop.rightPush("recommend", (Object)nameOfMain);
                  t++;
                  if(t>10){
                        break;
                  }
            }


      }

      public List<ArticleDto> getArticleByKeyValue(String key,String value, String order,String limit){
            return articleMapper.articleSelectByKeyValue(key, value, order, limit);
      }

      public List<ArticleDto> getArticleList(){
            List<ArticleDto> articleDtos = new ArrayList<>();
            articleDtos.addAll(articleMapper.articleSelectByKeyValue("MainCategory", "community", null, "1000"));
            articleDtos.addAll(articleMapper.articleSelectByKeyValue("MainCategory", "information", null, "1000"));
            return articleDtos;
      }

      public List<CommentDto> getCommentList(){
            List<CommentDto> commentDtos = CommentMapper.commentSelectLimit("1000");
            return commentDtos;
      }

      public List<ArticleLikeDto> getArticleLikeList(){
            List<ArticleLikeDto> articleLikeDtos = articleLikeMapper.articleLikeSelectLimit("1000");
            return articleLikeDtos;
      }

}
