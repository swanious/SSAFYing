package com.ssafy.ssafying_chat.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Name2idMapper {
  public String userIdSelectByNickname(String nickname);
  public String roomIdSelectByName(String nickname);
}
