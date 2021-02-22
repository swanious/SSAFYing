package com.ssafy.ssafying_chat.controller;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.EnrollAlertDto;
import com.ssafy.ssafying_chat.model.dto.InviteAlertDto;
import com.ssafy.ssafying_chat.model.service.EnrollAlertService;
import com.ssafy.ssafying_chat.model.service.InviteAlertService;
import com.ssafy.ssafying_chat.model.service.JwtService;
import com.ssafy.ssafying_chat.model.service.Name2idService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@Controller
@RequestMapping("/alert")
public class AlertController {
    
    @Autowired
    private EnrollAlertService enrollAlertService;

    @Autowired
    private InviteAlertService inviteAlertService;

    @Autowired
    private Name2idService name2idService;

    @Autowired
    private JwtService jwtService;
    
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/invite")  // send -> /pub/invite
    public void inviteAlertInsert(Map<String, Object> map) {
        try {
            String userId = name2idService.userIdSelectByNickname((String)map.get("userNickname"));

            InviteAlertDto inviteAlertDto = new InviteAlertDto();
            inviteAlertDto.setHostId((String)map.get("hostId"));
            inviteAlertDto.setNickname((String)map.get("nickname"));
            inviteAlertDto.setChatRoomId((String)map.get("chatRoomId"));
            inviteAlertDto.setName((String)map.get("name"));
            inviteAlertDto.setUserId(userId);

            inviteAlertService.inviteAlertInsert(inviteAlertDto);

            log.info("초대알림 입력 성공");
            // subcribe -> /sub/enroll/{hostId}
            messagingTemplate.convertAndSend("/sub/invite/" + userId, inviteAlertDto);
        } catch (Exception e) {
            e.printStackTrace();

            log.info("초대알림 입력 실패");
        }
    }

    @GetMapping("/invite/")
    public ResponseEntity<Map<String, Object>> inviteAlertSelectByOne(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("inviteAlertDtoList", inviteAlertService.inviteAlertSelectOne(jwtService.getUserId()));

            log.info("초대알림 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("초대알림 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/invite/")
    public ResponseEntity<Map<String, Object>> inviteAlertDelete(@RequestBody Map<String, Object> map){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            map.put("userId", jwtService.getUserId());
            if(inviteAlertService.inviteAlertDelete(map))
                log.info("초대알림 삭제 성공, 가입신청 성공");
            else
                log.info("초대알림 삭제 성공, 가입신청 실패");

            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("초대알림 승인 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/invite/")
    public ResponseEntity<Map<String, Object>> inviteAlertUpdate(@RequestBody InviteAlertDto inviteAlertDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            inviteAlertService.inviteAlertUpdate(inviteAlertDto.getInviteAlertId());

            log.info("초대알림 업데이트 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("초대알림 업데이트 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @MessageMapping("/enroll")  // send -> /pub/enroll
    public void enrollAlertInsert(EnrollAlertDto enrollAlertDto) {
        
        try {
            String hostId = enrollAlertService.enrollAlertInsert(enrollAlertDto);

            log.info("가입알림 입력 성공");
            // subcribe -> /sub/enroll/{hostId}
            messagingTemplate.convertAndSend("/sub/enroll/" + hostId, enrollAlertDto);
        } catch (Exception e) {
            e.printStackTrace();

            log.info("가입알림 입력 실패");
        }
    }

    @GetMapping("/enroll/")
    public ResponseEntity<Map<String, Object>> enrollAlertSelectByOne(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("enrollAlertDtoList", enrollAlertService.enrollAlertSelectOne(jwtService.getUserId()));

            log.info("가입알림 조회 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("가입알림 조회 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/enroll/")
    public ResponseEntity<Map<String, Object>> enrollAlertUpdate(@RequestBody EnrollAlertDto enrollAlertDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            enrollAlertService.enrollAlertUpdate(enrollAlertDto.getEnrollAlertId());

            log.info("가입알림 변경 성공");
            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("가입알림 변경 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/enroll/")
    public ResponseEntity<Map<String, Object>> enrollAlertDelete(@RequestBody Map<String, Object> map){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            map.put("hostId", jwtService.getUserId());
            if(enrollAlertService.enrollAlertDelete(map))
                log.info("가입알림 삭제 성공, 가입신청 성공");
            else
                log.info("가입알림 삭제 성공");

            resultMap.put("message", "success");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();

            log.info("가입알림 승인 실패");
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
