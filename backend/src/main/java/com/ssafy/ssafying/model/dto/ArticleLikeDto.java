package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class ArticleLikeDto{
    private int articleLikeId;
    private int articleId;
    private String userId;
}