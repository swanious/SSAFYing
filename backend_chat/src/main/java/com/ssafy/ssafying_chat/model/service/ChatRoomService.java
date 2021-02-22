package com.ssafy.ssafying_chat.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.ChatRoomDto;
import com.ssafy.ssafying_chat.model.dto.EnrollDto;
import com.ssafy.ssafying_chat.model.mapper.ChatRoomMapper;
import com.ssafy.ssafying_chat.model.mapper.EnrollMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private EnrollMapper enrollMapper;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public void chatRoomInsert(ChatRoomDto chatRoomDto) throws Exception{
        String userId = jwtService.getUserId();
        chatRoomDto.setHostId(userId);
        chatRoomMapper.chatRoomInsert(chatRoomDto);

        if(chatRoomDto.getIsLock() == 1){
            EnrollDto enrollDto = new EnrollDto();
            enrollDto.setUserId(userId);
            enrollDto.setChatRoomId(chatRoomDto.getChatRoomId());
            enrollDto.setIsHost(1);
            enrollMapper.enrollInsert(enrollDto);
        }
    }

    public List<ChatRoomDto> chatRoomSelectAll() throws Exception{
        return chatRoomMapper.chatRoomSelectAll();
    }

    public ChatRoomDto chatRoomSelectOneByKeyWord(String key, String word) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("word", word);
        
        return chatRoomMapper.chatRoomSelectOneByKeyWord(map);
    }

    public List<ChatRoomDto> chatRoomSelectByKeyWord(String key, String word) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("word", word);
        
        return chatRoomMapper.chatRoomSelectByKeyWord(map);
    }

    public ChatRoomDto chatRoomSelectOne(String chatRoomId) throws Exception{
        return chatRoomMapper.chatRoomSelectOne(chatRoomId);
    }

    public void chatRoomDelete(String chatRoomId) throws Exception{
        chatRoomMapper.chatRoomDelete(chatRoomId);
    }
}