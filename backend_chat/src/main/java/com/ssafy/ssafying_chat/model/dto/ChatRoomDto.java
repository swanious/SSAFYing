package com.ssafy.ssafying_chat.model.dto;

import lombok.Data;

@Data
public class ChatRoomDto {
    private String chatRoomId;
    private String createdAt;
    private String type;
    private String name;
    private String content;
    private int isLock;
    private String hostId;
}