package com.ssafy.ssafying_chat.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ssafy.ssafying_chat.model.dto.ChatDto;
import com.ssafy.ssafying_chat.model.service.ChatService;
import com.ssafy.ssafying_chat.model.service.RedisChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private RedisChatService redisChatService;


    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat")  // send -> /pub/chat
    public void chatInsert(ChatDto chatDto) {
        
        try {
            System.out.println(chatDto.getChatRoomId());
            chatDto.setChatId(UUID.randomUUID().toString().replace("-", ""));

            chatService.chatInsert(chatDto);

            log.info("채팅 입력 성공");

            redisChatService.chatInsertToRedis(chatDto);

            log.info("Redis 저장 성공");

            // subcribe -> /sub/chatRoom/{채팅방번호}
            messagingTemplate.convertAndSend("/sub/chatRoom/" + chatDto.getChatRoomId(), chatDto);
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅 입력 실패");
        }
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<Map<String, Object>> ChatSelectByChatRoomId(@PathVariable("chatRoomId") String chatRoomId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("key", "chatRoomId");
            map.put("word", chatRoomId);
            // resultMap.put("chatDtoList", chatService.chatSelectByKeyWord(map));
            resultMap.put("chatDtoList", redisChatService.chatSelectByKeyWordAtRedis(map));

            log.info("채팅 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/all/{chatRoomId}")
    public ResponseEntity<Map<String, Object>> ChatSelectAllByChatRoomId(@PathVariable("chatRoomId") String chatRoomId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("chatDtoList", chatService.chatSelectAllByChatRoomId(chatRoomId));

            log.info("채팅 전체 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅 전체 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}