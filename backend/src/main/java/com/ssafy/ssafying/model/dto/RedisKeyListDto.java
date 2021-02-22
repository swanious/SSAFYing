package com.ssafy.ssafying.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class RedisKeyListDto {
      private String key;
      private List<String> valueList;
}
