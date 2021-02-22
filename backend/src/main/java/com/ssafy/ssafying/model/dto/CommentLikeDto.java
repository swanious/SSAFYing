package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class CommentLikeDto{
    private int commentLikeId;
    private int commentId;
    private String userId;
}