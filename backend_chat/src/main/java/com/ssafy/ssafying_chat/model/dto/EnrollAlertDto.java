package com.ssafy.ssafying_chat.model.dto;

import lombok.Data;

@Data
public class EnrollAlertDto {
    private int enrollAlertId;
    private String createdAt;
    private int isRead;
    private String userId;
    private String nickname;
    private String chatRoomId;
    private String name;
    private String hostId;
}
