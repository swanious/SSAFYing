package com.ssafy.ssafying.controller;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.ssafying.config.JwtTokenUtil;
import com.ssafy.ssafying.model.dto.UserDto;
import com.ssafy.ssafying.model.service.CustomUserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @CrossOrigin(origins = { "http://localhost:8080" })
@RequestMapping("/jwt")
public class JwtAtuthenticationController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;        

        try {
            final UserDto userAuth = userDetailService.authenticateByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userAuth.getUserId());
            
            resultMap.put("access-token", jwtTokenUtil.generateToken(userAuth.getEmail(), map));

            resultMap.put("message", "success");
            status = HttpStatus.CREATED;
            logger.info("토큰 발급 성공");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", e.getMessage());
            // resultMap.put("message", "회원가입 실패 : 500에러");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("토큰 발급 : 에러");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
