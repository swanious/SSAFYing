package com.ssafy.ssafying_chat.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.ChatDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {
    public void chatInsert(ChatDto chatDto);
    public List<ChatDto> chatSelectByChatRoomId(String chatRoomId,String limit);
    public List<ChatDto> chatSelectAllByChatRoomId(String chatRoomId);
    public List<ChatDto> chatSelectByKeyWord(Map<String, Object> map);
}
