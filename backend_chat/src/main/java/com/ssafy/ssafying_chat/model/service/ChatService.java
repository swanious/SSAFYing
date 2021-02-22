package com.ssafy.ssafying_chat.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.ChatDto;
import com.ssafy.ssafying_chat.model.mapper.ChatMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    @Autowired
    private ChatMapper chatMapper;
    
    public void chatInsert(ChatDto chatDto) {
        chatMapper.chatInsert(chatDto);
    }

    public List<ChatDto> chatSelectByChatRoomId(String chatRoomId){
        return chatMapper.chatSelectByChatRoomId(chatRoomId,null);
    }

    public List<ChatDto> chatSelectAllByChatRoomId(String chatRoomId){
        return chatMapper.chatSelectAllByChatRoomId(chatRoomId);
    }

    public List<ChatDto> chatSelectByKeyWord(Map<String, Object> map){
        return chatMapper.chatSelectByKeyWord(map);
    }
}
