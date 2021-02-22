package com.ssafy.ssafying_chat.model.dto;

import lombok.Data;

@Data
public class EnrollDto {
    private int enrollId;
    private String createdAt;
    private String chatRoomId;
    private String userId;
    private int isHost;
}
