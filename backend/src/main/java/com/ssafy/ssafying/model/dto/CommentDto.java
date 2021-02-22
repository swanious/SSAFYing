package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class CommentDto{
    private int commentId;
    private int articleId;
    private String userId;
    private String content;
    private String createdAt;
    private String updatedAt;
    private int parentId;
    //아래는 response를 위한 속성
    private String nickname;
}