package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class AlertDto{
    private int alertId;
    private String userId;
    private int articleId;
    private String title;
    private int commentId;
    private String content;
    private int isRead;
    private String createdAt;
}