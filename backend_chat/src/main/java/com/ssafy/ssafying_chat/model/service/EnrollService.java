package com.ssafy.ssafying_chat.model.service;


import java.util.HashMap;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.ChatRoomDto;
import com.ssafy.ssafying_chat.model.dto.EnrollDto;
import com.ssafy.ssafying_chat.model.mapper.ChatRoomMapper;
import com.ssafy.ssafying_chat.model.mapper.EnrollMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollService {

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private EnrollMapper enrollMapper;

    @Autowired
    private JwtService jwtService;

    public void enrollInsert(EnrollDto enrollDto) throws Exception{
        enrollMapper.enrollInsert(enrollDto);
    }

    public void enrollSelectLogin(Map<String, Object> map) throws Exception{
        map.put("key", "name");
        map.put("word", map.get("name"));

        for(ChatRoomDto chatRoomDto : chatRoomMapper.chatRoomSelectByKeyWord(map)){
            
            if(chatRoomDto.getIsLock() == 1){
                EnrollDto enrollDto = new EnrollDto();
                enrollDto.setUserId(jwtService.getUserId());
                enrollDto.setChatRoomId(chatRoomDto.getChatRoomId());
                
                if(enrollMapper.enrollSelectLogin(enrollDto) == null){
                    throw new Exception();
                }
            }
        }

    }

    @Transactional
    public void enrollDelete(String chatRoomId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("chatRoomId", chatRoomId);
        map.put("userId", jwtService.getUserId());
        enrollMapper.enrollDelete(map);

        map.put("key", "chatRoomId");
        map.put("word", chatRoomId);
        if(enrollMapper.enrollSelectByKeyWord(map).size() == 0){
            chatRoomMapper.chatRoomDelete(chatRoomId);
        }
    }
}
