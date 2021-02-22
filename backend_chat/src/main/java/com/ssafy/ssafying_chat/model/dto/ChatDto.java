package com.ssafy.ssafying_chat.model.dto;

import lombok.Data;

@Data
public class ChatDto {
    private String chatId;
    private String content;
    private String createdAt;
    private String chatRoomId;
    private String userId;
    private String nickname;
}
