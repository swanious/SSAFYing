// package com.ssafy.ssafying_chat.config.handler;

// import com.ssafy.ssafying_chat.config.JwtTokenUtil;
// import com.ssafy.ssafying_chat.model.service.CustomUserDetailsService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.messaging.support.ChannelInterceptor;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import org.springframework.messaging.Message;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @RequiredArgsConstructor
// @Component
// public class StompHandler implements ChannelInterceptor {

//     @Autowired
//     private CustomUserDetailsService jwtUserDetailService;

//     @Autowired
//     private final JwtTokenUtil jwtTokenUtil;

//     // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
//     @Override
//     public Message<?> preSend(Message<?> message, MessageChannel channel) {
//         StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        
//         // websocket 연결시 헤더의 jwt token 검증
//         String jwtToken = accessor.getFirstNativeHeader("access-token");
//         String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//         UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(username);
//         jwtTokenUtil.validateToken(jwtToken, userDetails);

//         return message;
//     }
// }