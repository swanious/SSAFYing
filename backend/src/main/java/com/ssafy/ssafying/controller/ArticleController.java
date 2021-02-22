package com.ssafy.ssafying.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.ArticleDto;
import com.ssafy.ssafying.model.dto.ArticleLikeDto;
import com.ssafy.ssafying.model.dto.ArticleSearchDto;
import com.ssafy.ssafying.model.service.ArticleLikeService;
import com.ssafy.ssafying.model.service.ArticleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("CommentController V1")
@CrossOrigin("*")
@RestController
@RequestMapping("/article")
public class ArticleController {
      public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

      @Autowired
      private ArticleService articleService;

      @Autowired
      private ArticleLikeService articleLikeService;

      @ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. ", response = String.class)
      @PostMapping(value = "/", headers = { "Content-type=application/json" })
      public ResponseEntity<List<ArticleDto>> articleInsert(
                  @ApiParam(value = "게시글 양식", required = true) @RequestBody ArticleDto articleDto) throws Exception {
            logger.info("article 생성");
            return new ResponseEntity<>(articleService.articleInsert(articleDto), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 전체글검색", notes = "모든 게시글의 정보를 받아온다. 최대 10000개", response = String.class)
      @GetMapping("/")
      public ResponseEntity<List<ArticleDto>> articleSelectAll() {
            logger.info("article 검색");
            return new ResponseEntity<>(articleService.articleSelectAll(), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 글삭제", notes = "게시글 아이디로 글을 삭제한다.", response = String.class)
      @DeleteMapping("/{articleId}")
      public ResponseEntity<List<ArticleDto>> DeleteMapping(
                  @ApiParam(value = "게시글 아이디", required = true) @PathVariable int articleId) {
            logger.info("article 삭제");
            return new ResponseEntity<List<ArticleDto>>(articleService.articleDelete(articleId), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 글수정", notes = "게시글의 내용을 바꾸고자 하는 정보로 변경한다.", response = String.class)
      @PutMapping("/")
      public ResponseEntity<List<ArticleDto>> UpdateMapping( 
                  @ApiParam(value = "게시글 양식", required = true) @RequestBody ArticleDto articleDto) throws Exception {
            logger.info("article 수정");
            articleService.articleUpdate(articleDto);
            return new ResponseEntity<List<ArticleDto>>(articleService.articleSelectAll(), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 글 상세내용", notes = "게시판 아이디로 글 상세 내용을 검색한다.", response = String.class)
      @GetMapping("/{articleId}")
      public ResponseEntity<ArticleDto> articleSelectOne(
                  @ApiParam(value = "게시글 아이디", required = true) @PathVariable int articleId) {
            logger.info("article 1개 검색");
            return new ResponseEntity<>(articleService.articleSelectOne(articleId), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 대분류로 검색", notes = "게시판 대분류이름이 일치하는 모든 글들을 검색한다. 좋아요 수, 댓글 수, 유저 닉네임도 반환한다.", response = String.class)
      @GetMapping("/main/{mainCategory}")
      public ResponseEntity<List<ArticleSearchDto>> articleSelectByMainCategory(
                  @ApiParam(value = "대분류 이름", required = true) @PathVariable String mainCategory) throws SQLException {
            logger.info("MainCategory로 article 검색");
            return new ResponseEntity<>(articleService.articleSelectByMainCategory(mainCategory), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 소분류로 검색", notes = "게시판 소분류이름이 일치하는 모든 글들을 검색한다. 좋아요 수, 댓글 수, 유저 닉네임도 반환한다.", response = String.class)
      @GetMapping("/sub/{subCategory}")
      public ResponseEntity<List<ArticleSearchDto>> articleSelectBySubCategory(
                  @ApiParam(value = "소분류 이름", required = true) @PathVariable String subCategory) throws SQLException {
            logger.info("SubCategory로 article 검색");
            return new ResponseEntity<>(articleService.articleSelectBySubCategory(subCategory), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 닉네임으로 검색", notes = "닉네임으로 일치하는 모든 글들을 검색한다.", response = String.class)
      @GetMapping("/nick/{nickname}")
      public ResponseEntity<List<ArticleDto>> articleSelectByNickname(@PathVariable String nickname)
                  throws SQLException {
            logger.info("Nickname으로 article 검색");
            return new ResponseEntity<>(articleService.articleSelectByNickname(nickname), HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 좋아요 검색", notes = "현재 게시글에 좋아요를 등록한 모든 정보를 가져온다.", response = String.class)
      @GetMapping("/like/")
      public ResponseEntity<List<ArticleLikeDto>> articleLikeSelectAll(){
            logger.info("articleLike 검색");
            return new ResponseEntity<>(articleLikeService.articleLikeSelectAll(),HttpStatus.OK);
      }

    
      @ApiOperation(value = "게시판 좋아요 추가", notes = "현재 게시글에 좋아요를 등록한다.", response = String.class)  
      @PostMapping("/like/")
      public ResponseEntity<List<ArticleLikeDto>> articleLikeInsert(@ApiParam(value = "게시판좋아요 양식", required = true) @RequestBody ArticleLikeDto articleLikeDto){
            logger.info("articleLike 추가");
            articleLikeService.articleLikeInsert(articleLikeDto);
            logger.info("인서트 성공");
            return new ResponseEntity<>(articleLikeService.articleLikeSelectByArticleId(articleLikeDto.getArticleId()),HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 좋아요 삭제", notes = "현재 게시글 아이디와 유저아이디로 좋아요 삭제를 수행한다.", response = String.class)
      @DeleteMapping("/like/{articleId}/{userId}")
      public ResponseEntity<String> articleLikeDelete(@ApiParam(value = "게시글 아이디", required = true) @PathVariable int articleId,@ApiParam(value = "유저 아이디", required = true) @PathVariable String userId){
            logger.info("articleLike 삭제");
            articleLikeService.articleLikeDelete(articleId, userId);
            return new ResponseEntity<>(String.valueOf(articleLikeService.articleLikeCountByArticleId(articleId)),HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 좋아요 개수 검색", notes = "현재 게시글의 아이디로 그 게시글의 좋아요 개수를 출력한다.", response = String.class)
      @GetMapping("/like/{articleId}")
      public ResponseEntity<String> articleLikeCountByArticleId(@ApiParam(value = "게시글 아이디", required = true) @PathVariable int articleId){
            logger.info("articleLike 개수검색");
            return new ResponseEntity<>(String.valueOf(articleLikeService.articleLikeCountByArticleId(articleId)),HttpStatus.OK);
      }

      @ApiOperation(value = "게시판 좋아요 여부 확인", notes = "현재 게시글의 아이디와 유저 아이디로 이 게시글에 좋아요를 눌렀는 지 확인하여 반환한다. 눌렀다면 1, 누르지 않았다면 0을 반환한다.", response = String.class)
      @GetMapping("/like/{articleId}/{userId}")
      public ResponseEntity<Object> articleLikeOnAndOff(@ApiParam(value = "게시글 아이디", required = true) @PathVariable int articleId,@ApiParam(value = "유저 아이디", required = true) @PathVariable String userId){
            logger.info("articleLike 여부 확인");
            return new ResponseEntity<>(articleLikeService.articleLikeOnAndOff(articleId, userId) ? 1 : 0 , HttpStatus.OK);
      }

      @ApiOperation(value = "게시글 불러오기 (게시글 내용 / 좋아요 수 / 댓글 수 / 좋아요 여부)", notes = "현재 게시글의 아이디로 게시글 내용(data)과 좋아요 수(articleLikeCount), 댓글 수(commentCount)와 좋아요를 눌렀는 지 확인(isLike)하여 반환한다. 좋아요 여부는 눌렀다면 1, 누르지 않았다면 0을 반환한다.", response = String.class)
      @GetMapping("/info/{articleId}")
      public ResponseEntity<Map<String,Object>> articleInformation(@ApiParam(value = "게시글 아이디", required = true) @PathVariable int articleId)
                  throws Exception {
            logger.info("articleLike 상세 설명");
            Map<String,Object> resultMap = null;
            resultMap = articleService.articleSelectOneSpecial(articleId);
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(resultMap, status);
      }

      @ApiOperation(value = "게시글 최신순 검색", notes = "최신순 tag로 게시글을 검색합니다.", response = String.class)
      @GetMapping("/search/{tag}")
      public ResponseEntity<List<ArticleSearchDto>> articleSearch(@ApiParam(value = "검색 쿼리", required = true) @PathVariable String tag)
                  throws SQLException {
            logger.info("article 검색 최신순");
            return new ResponseEntity<>(articleService.articleSearch(tag), HttpStatus.OK);
      }

      @ApiOperation(value = "게시글 정확도순 검색", notes = "정확도순 tag로 게시글을 검색합니다.", response = String.class)
      @GetMapping("/search2/{tag}")
      public ResponseEntity<List<ArticleSearchDto>> articleSearchAccuracy(@ApiParam(value = "검색 쿼리", required = true) @PathVariable String tag)
                  throws SQLException {
            logger.info("article 검색 정확도순");
            return new ResponseEntity<>(articleService.articleSearchAccuracy(tag), HttpStatus.OK);
      }
}
