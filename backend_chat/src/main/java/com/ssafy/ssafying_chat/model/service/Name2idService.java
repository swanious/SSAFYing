package com.ssafy.ssafying_chat.model.service;

import com.ssafy.ssafying_chat.model.mapper.Name2idMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Name2idService {
  
  @Autowired
  private Name2idMapper name2idMapper;
  
  public String userIdSelectByNickname(String nickname){
    return name2idMapper.userIdSelectByNickname(nickname);
  }

  public String roomIdSelectByName(String name){
    return name2idMapper.roomIdSelectByName(name);
  }
}
