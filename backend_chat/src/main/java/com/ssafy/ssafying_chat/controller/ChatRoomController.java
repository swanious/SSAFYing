package com.ssafy.ssafying_chat.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ssafy.ssafying_chat.model.dto.ChatRoomDto;
import com.ssafy.ssafying_chat.model.service.ChatRoomService;
import com.ssafy.ssafying_chat.model.service.EnrollService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private EnrollService enrollService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> charRoomInsert(@RequestBody ChatRoomDto chatRoomDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            chatRoomDto.setChatRoomId(UUID.randomUUID().toString().replace("-", ""));
            chatRoomService.chatRoomInsert(chatRoomDto);

            log.info("채팅방 생성 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅방 생성 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> chatRoomSelectAll() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("chatRoomDtoList", chatRoomService.chatRoomSelectAll());

            log.info("채팅방 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅방 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/{type}")
    public ResponseEntity<Map<String, Object>> chatRoomSelect(@PathVariable("type") String type) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("chatRoomDtoList", chatRoomService.chatRoomSelectByKeyWord("type", type));

            log.info("채팅방 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅방 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Map<String, Object>> charRoomDelete(@PathVariable("chatRoomId") String chatRoomId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            chatRoomService.chatRoomDelete(chatRoomId);
            
            log.info("채팅방 타입별 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            
            log.info("채팅방 타입별 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    @GetMapping("/info/{chatRoomId}")
    public ResponseEntity<Map<String, Object>> chatRoomSelectOne(@PathVariable("chatRoomId") String chatRoomId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("chatRoomDto", chatRoomService.chatRoomSelectByKeyWord("chatRoomId", chatRoomId).get(0));

            log.info("채팅방 정보 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅방 정보 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/enroll/login")
    public ResponseEntity<Map<String, Object>> charRoomLogin(@RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            enrollService.enrollSelectLogin(map);

            log.info("채팅방 로그인 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("채팅방 로그인 실패"); 
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/enroll/{chatRoomId}")
    public ResponseEntity<Map<String, Object>> charRoomSelectAll(@PathVariable("chatRoomId") String chatRoomId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            enrollService.enrollDelete(chatRoomId);

            log.info("채팅방 탈퇴 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            
            log.info("채팅방 탈퇴 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // @PostMapping("/")
    // public ResponseEntity<Map<String, Object>> charRoomSelectAll() {
    //     Map<String, Object> resultMap = new HashMap<>();
    //     HttpStatus status = null;

    //     try {

    //         resultMap.put("message", "success");
    //         status = HttpStatus.OK;
    //     } catch (Exception e) {
    //         e.printStackTrace();

    //         resultMap.put("message", "fail");
    //         status = HttpStatus.INTERNAL_SERVER_ERROR;
    //     }

    //     return new ResponseEntity<Map<String, Object>>(resultMap, status);
    // }
}