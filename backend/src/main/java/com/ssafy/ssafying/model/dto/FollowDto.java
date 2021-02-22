package com.ssafy.ssafying.model.dto;

import lombok.Data;

@Data
public class FollowDto {
     int followId;
     String followerId; // 팔로우 하는 사람
     String followingId; // 팔로우 받는 사람
}
