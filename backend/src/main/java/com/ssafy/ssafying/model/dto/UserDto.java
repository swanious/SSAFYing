package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class UserDto{
    private String userId;
    private int report;
    private int isActive;
    private String role;
    private String createdAt;
    private String profileImage;
    private String email;
    private String password;
    private String nickname;
    private String username;
    private String birthday;
    private String gender;
    private int grade;
}