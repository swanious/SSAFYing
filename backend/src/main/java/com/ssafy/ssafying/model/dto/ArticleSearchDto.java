package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class ArticleSearchDto {
     ArticleDto articleDto; // 글 정보
     String nickname; // 닉네임
     int articleLike; // 좋아요 수
     int articleCommentCount; // 댓글 수 
}
