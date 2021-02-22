package com.ssafy.ssafying_chat.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ssafying_chat.model.dto.ChatDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisChatService {
      public static final Logger logger = LoggerFactory.getLogger(RedisChatService.class);

      @Autowired
      RedisTemplate<String,Object> redisTemplate;

      // Redis에 있는 채팅 정보를 바탕으로 가져온다.
      public List<ChatDto> chatSelectByKeyWordAtRedis(Map<String, Object> map){
            logger.info("Redis에 있는 정보를 바탕으로 채팅방을 검색했습니다.");
            List<ChatDto> resultList = new ArrayList<>();
            ListOperations<String,Object> lop = redisTemplate.opsForList();
            HashOperations<String,Object,Object> hop = redisTemplate.opsForHash();
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<ChatDto> mapType = new TypeReference<ChatDto>() {
            };
            List<Object> chatList = lop.range("room/"+map.get("word"), 0, 100);
            for(Object chatRedisId : chatList){
                  Map<Object,Object> chatMap = hop.entries((String)chatRedisId);
                  ChatDto chat = objectMapper.convertValue(chatMap, mapType);
                  resultList.add(chat);
            }

            return resultList;
      }

      // DB에 넣는 것과 별개로 Redis에 chat을 저장한다.
      public void chatInsertToRedis(ChatDto chatDto){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
            };
            ListOperations<String,Object> lop = redisTemplate.opsForList();
            HashOperations<String,Object,Object> hop = redisTemplate.opsForHash();

            Map<String, Object> chatMap = objectMapper.convertValue(chatDto, mapType);
            String chatName = "room/"+chatDto.getChatRoomId()+"/"+chatDto.getChatId();

            hop.putAll(chatName,chatMap);
            lop.rightPush("room/"+chatDto.getChatRoomId(), chatName);
      }

}
