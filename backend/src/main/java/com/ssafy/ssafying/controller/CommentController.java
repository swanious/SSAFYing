package com.ssafy.ssafying.controller;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.ssafying.model.dto.CommentDto;
import com.ssafy.ssafying.model.dto.CommentLikeDto;
import com.ssafy.ssafying.model.service.CommentLikeService;
import com.ssafy.ssafying.model.service.CommentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.PutMapping;

@Api("CommentController V1")
@RestController
@RequestMapping("/comment")
public class CommentController {
      public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

      @Autowired
      CommentService commentService;

      @Autowired
      CommentLikeService commentLikeService;

      @ApiOperation(value = "모든 댓글 검색", notes = "저장되어 있는 모든 댓글을 검색한다.", response = String.class)
      @GetMapping("/")
      public ResponseEntity<List<CommentDto>> commentSelectAll() {
            logger.info("모든 댓글 검색");
            return new ResponseEntity<>(commentService.commentSelectAll(), HttpStatus.OK);
      }

      @ApiOperation(value = "댓글을 삽입한다.", notes = "현재 게시글에 댓글을 삽입합니다. 이 후 현재 게시글의 댓글을 모두 검색한 값을 반환합니다.", response = String.class)
      @PostMapping("/")
      public ResponseEntity<List<CommentDto>> commentInsert(
                  @ApiParam(value = "댓글 양식", required = true) @RequestBody CommentDto commentDto) {
            logger.info("댓글 삽입");
            return new ResponseEntity<>(commentService.commentInsert(commentDto), HttpStatus.OK);
      }

      @ApiOperation(value = "하나의 게시글에 댓글 검색", notes = "게시글 아이디로 하나의 게시글에 해당되는 댓글들만 검색해서 가져옵니다. 닉네임 포함", response = String.class)
      @GetMapping("/{articleId}")
      public ResponseEntity<List<CommentDto>> commentSelectByArticleId(
                  @ApiParam(value = "게시글 아이디", required = true) @PathVariable int articleId) throws SQLException {
            logger.info("글의 댓글 가져오기");
            return new ResponseEntity<>(commentService.commentSelectByArticleId(articleId), HttpStatus.OK);
      }

      @ApiOperation(value = "댓글 삭제", notes = "댓글 아이디로 그 댓글을 삭제합니다. 이 후 그 댓글이 있던 게시물의 댓글들을 검색하여 반환합니다.", response = String.class)
      @DeleteMapping("/{commentId}")
      public ResponseEntity<List<CommentDto>> commentDelete(
                  @ApiParam(value = "댓글 아이디", required = true) @PathVariable int commentId) {
            logger.info("댓글 삭제");
            return new ResponseEntity<>(commentService.commentDelete(commentId), HttpStatus.OK);
      }

      @ApiOperation(value = "댓글 수정", notes = "바꾸고자 하는 댓글로 수정합니다. 이 후 수정된 댓글이 있는 게시글의 댓글을 검색하여 반환합니다.", response = String.class)
      @PutMapping(value = "/")
      public ResponseEntity<List<CommentDto>> commentUpdate(
                  @ApiParam(value = "댓글 양식", required = true) @RequestBody CommentDto commentDto) {
            logger.info("댓글 수정");
            return new ResponseEntity<>(commentService.commentUpdate(commentDto), HttpStatus.OK);
      }

      @ApiOperation(value = "댓글 닉네임으로 검색", notes = "닉네임으로 일치하는 모든 댓글들을 검색한다.", response = String.class)
      @GetMapping("/nick/{nickname}")
      public ResponseEntity<List<CommentDto>> commentSelectByNickname(@ApiParam(value = "유저 닉네임", required = true) @PathVariable String nickname)
                  throws SQLException {
            logger.info("Nickname으로 article 검색");
            return new ResponseEntity<>(commentService.commentSelectByNickname(nickname), HttpStatus.OK);
      }

      @ApiOperation(value = "모든 댓글 좋아요 검색", notes = "현재 댓글에 좋아요 정보를 검색합니다.", response = String.class)
      @GetMapping("/like")
      public ResponseEntity<List<CommentLikeDto>> commentLikeSelectAll(){
            logger.info("모든 댓글 좋아요");
            return new ResponseEntity<>(commentLikeService.commentLikeSelectAll(),HttpStatus.OK);
      }

      @ApiOperation(value = "댓글 좋아요 등록", notes = "현재 게시글에 좋아요를 등록합니다. 이 후 댓글의 좋아요 개수를 반환합니다.", response = String.class)
      @PostMapping("/like")
      public ResponseEntity<String> commentLikeInsert(@ApiParam(value = "댓글 좋아요 양식", required = true) @RequestBody CommentLikeDto commentLikeDto){
            logger.info("댓글 좋아요 등록");
            return new ResponseEntity<>(String.valueOf(commentLikeService.commentLikeInsert(commentLikeDto)),HttpStatus.OK);
      }

      @ApiOperation(value = "댓글 좋아요 취소", notes = "게시글 번호와 유저 아이디로 좋아요를 취소합니다. 이 후 댓글의 좋아요 개수를 반환합니다.", response = String.class)
      @DeleteMapping("/like/{commentId}/{userId}")
      public ResponseEntity<String> commentLikeDelete(@ApiParam(value = "댓글 아이디", required = true) @PathVariable int commentId, @ApiParam(value = "유저 아이디", required = true) @PathVariable String userId){
            logger.info("댓글 좋아요 취소");
            return new ResponseEntity<>(String.valueOf(commentLikeService.commentLikeDelete(commentId, userId)),HttpStatus.OK);
      }

      @ApiOperation(value = "댓글 좋아요 여부 확인", notes = "게시글 번호와 유저 아이디로 좋아요 여부를 확인하고 좋아요 상태라면 1 아니라면 0을 반환한다.", response = String.class)
      @GetMapping("/like/{commentId}/{userId}")
      public ResponseEntity<Object> commentLikeOnAndOff(@ApiParam(value = "댓글 아이디", required = true) @PathVariable int commentId, @ApiParam(value = "유저 아이디", required = true) @PathVariable String userId){
            logger.info("댓글 좋아요 여부 확인");
            return new ResponseEntity<>(commentLikeService.commentLikeOnAndOff(commentId, userId) ? 1 : 0, HttpStatus.OK);
      }

      @ApiOperation(value = "게시글에 댓글 좋아요의 개수를 확인한다. ", notes = "게시글 Id로 댓글의 좋아요 개수를 검색한다.", response = String.class)
      @GetMapping("/like/{commentId}")
      public ResponseEntity<String> commentLikeCountByCommentId(@ApiParam(value = "댓글 아이디", required = true) @PathVariable int commentId){
            logger.info("게시글에 댓글 좋아요 개수확인");
            return new ResponseEntity<>(String.valueOf(commentLikeService.commentLikeCountByCommentId(commentId)),HttpStatus.OK);
      }
}
