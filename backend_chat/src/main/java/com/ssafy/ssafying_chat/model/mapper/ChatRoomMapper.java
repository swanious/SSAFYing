package com.ssafy.ssafying_chat.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.ChatRoomDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRoomMapper {
    public int chatRoomInsert(ChatRoomDto chatRoomDto) throws Exception;
    public List<ChatRoomDto> chatRoomSelectAll() throws Exception;
    public ChatRoomDto chatRoomSelectOne(String chatRoomId) throws Exception;
    public ChatRoomDto chatRoomSelectOneByKeyWord(Map<String, Object> map) throws Exception;
    public List<ChatRoomDto> chatRoomSelectByKeyWord(Map<String, Object> map) throws Exception;
    public int chatRoomDelete(String chatRoomId) throws Exception;
}
