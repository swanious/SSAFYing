package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class ArticleDto{
    private int articleId;
    private String userId;
    private String title;
    private String content;
    private String mainCategory;
    private String subCategory;
    private int price;
    private String location;
    private String articleImage;
    private int watchCount;
    private String createdAt;
    private String updatedAt;
    // Ranking을 위한 속성
    private int score;
    private int comment;
}