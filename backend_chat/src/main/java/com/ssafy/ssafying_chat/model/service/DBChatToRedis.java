package com.ssafy.ssafying_chat.model.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ssafying_chat.model.dto.ChatDto;
import com.ssafy.ssafying_chat.model.dto.ChatRoomDto;
import com.ssafy.ssafying_chat.model.mapper.ChatMapper;
import com.ssafy.ssafying_chat.model.mapper.ChatRoomMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class DBChatToRedis {
      public static final Logger logger = LoggerFactory.getLogger(DBChatToRedis.class);
      
      @Autowired
      RedisTemplate<String,Object> redisTemplate;
      
      @Autowired
      ChatMapper chatMapper;
      
      @Autowired
      ChatRoomMapper chatRoomMapper;
      
      public static ListOperations<String,Object> lop;
      HashOperations<String,Object,Object> hop;
      // DB에서 Redis로 챗을 가져와서 구조를 완성하는 함수
      @Scheduled(fixedDelay = 1000 * 60 * 5, initialDelay = 1000) // 5분마다 작동
      public void chatToRedis() throws Exception {
            logger.info("DB에서 채팅을 가져와서 Redis에 저장을 시도합니다.");
            
            List<ChatRoomDto> chatRoomDtos = getChatRooms();
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
            };
            
            lop = redisTemplate.opsForList();
            hop = redisTemplate.opsForHash();
            
            for(ChatRoomDto chatRoom : chatRoomDtos){
                  Long chatCountOfRoom = getChatCount(chatRoom.getChatRoomId());
                  if(chatCountOfRoom!=null && chatCountOfRoom > 100){ // 이미 채팅방이 있고, 개수가 100개가 넘어가면...
                        long times = chatCountOfRoom-100;
                        for(int i = 0 ; i < times ; i++){ // 그 횟수만큼 pop
                              String chatName = (String)leftPop(chatRoom.getChatRoomId());
                              deleteChatDto(chatName);
                        }
                  }
                  else if(chatCountOfRoom > 0){
                        // 채팅방에 글이 있는데, 100개를 안 넘는다면
                        // 아무 행위도 하지 않음
                  }
                  else{
                        // 채팅방은 있는데 챗이 redis에 없다면?
                        // 채팅방의 챗을 등록한다.
                        List<ChatDto> chatlist = getChatByRoomId(chatRoom.getChatRoomId());
                        for(ChatDto chat : chatlist){
                              Map<String, Object> chatMap = objectMapper.convertValue(chat, mapType);
                              String chatName = "room/"+chatRoom.getChatRoomId()+"/"+chat.getChatId();
                              hop.putAll(chatName,chatMap);
                              lop.leftPush("room/"+chatRoom.getChatRoomId(), chatName);
                        }
                  }
            }
            logger.info("정상적으로 Chat이 Redis에 갱신되었습니다.");
      }

      // 모든 특정 채팅방의 채팅을 100개까지 가져오는 함수
      public List<ChatDto> getChatByRoomId(String chatRoomId){
            return chatMapper.chatSelectByChatRoomId(chatRoomId,"100");
      }

      //모든 채팅방을 가져오는 함수
      public List<ChatRoomDto> getChatRooms() throws Exception {
            return chatRoomMapper.chatRoomSelectAll();
      }

      // redis에 특정 채팅방에 존재하는 채팅 개수
      public Long getChatCount(String chatRoomId) {
            return lop.size("room/"+chatRoomId);
      }

      //redis에 특정 채팅방에 넘친 채팅 1개를 가져온다.
      public Object leftPop(String chatRoomId){
            return lop.leftPop("room/"+chatRoomId);

      }

      //redis에 존재하던 채팅Dto를 지운다.
      public void deleteChatDto(String redisChatkey){
            Set<Object> obj = hop.keys(redisChatkey);
            hop.delete(redisChatkey, obj);
      }

}
