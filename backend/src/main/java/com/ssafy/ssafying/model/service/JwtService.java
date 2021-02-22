package com.ssafy.ssafying.model.service;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import com.ssafy.common.error.UnauthorizedException;
import com.ssafy.ssafying.config.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.*;


// @Service
// public class JwtService{

//     public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

// 	private static final String TK = "jwtpassword";
//     private static final int EXPIRE_MINUTES = 60;
    
//     public <T> String create(String key, T data, String subject){
// 		String jwt = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis())
//         .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES))
//         .setSubject(subject).claim(key, data).signWith(SignatureAlgorithm.HS256, this.generateKey()).compact();
		
// 		return jwt;
//     }

//     private byte[] generateKey() {
// 		byte[] key = null;
// 		try {
// 			key = TK.getBytes("UTF-8");
// 		} catch (UnsupportedEncodingException e) {
// 			if (logger.isInfoEnabled()) {
// 				e.printStackTrace();
// 			} else {
// 				logger.error("Making JWT Key Error ::: {}", e.getMessage());
// 			}
// 		}

// 		return key;
// 	}

// 	//	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
// 	public boolean isUsable(String jwt) {
// 		try {
// 			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
// 			if(claims.getBody().getExpiration().after(new Date())){
// 				return true;
// 			} else {
// 				return false;
// 			}

// 		} catch (Exception e) {
// 			if (logger.isInfoEnabled()) {
// 				e.printStackTrace();
// 			} else {
// 				logger.error(e.getMessage());
// 			}
// 			throw new UnauthorizedException();
// 		}
// 	}

// 	public Map<String, Object> get() throws Exception {
// 		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
// 				.getRequest();
// 		String jwt = request.getHeader("Authorization").substring(7);
// 		Jws<Claims> claims = null;
// 		try {
// 			claims = Jwts.parser().setSigningKey(TK).parseClaimsJws(jwt);
// 		} catch (Exception e) {
// 			logger.error(e.getMessage());
// 			throw new UnauthorizedException();
// 		}
// 		Map<String, Object> value = claims.getBody();
// 		logger.info("value : {}", value);
// 		return value;
// 	}

// 	public String getUserId() throws Exception {
// 		String userId = (String)this.get().get("userId");
// 		return userId;
// 	}
// }
@Service
public class JwtService{

    public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	private static final String TK = "jwtpassword";
    private static final int EXPIRE_MINUTES = 60;
    
    public <T> String create(String key, T data, String subject){
		String jwt = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES))
        .setSubject(subject).claim(key, data).signWith(SignatureAlgorithm.HS256, this.generateKey()).compact();
		
		return jwt;
    }

    private byte[] generateKey() {
		byte[] key = null;
		try {
			key = TK.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}

	//	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
	public boolean isUsable(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
			if(claims.getBody().getExpiration().after(new Date())){
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error(e.getMessage());
			}
			throw new UnauthorizedException();
		}
	}

	public Map<String, Object> get() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String jwt = request.getHeader("access-token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(TK).parseClaimsJws(jwt);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new UnauthorizedException();
		}
		Map<String, Object> value = claims.getBody();
		logger.info("value : {}", value);
		return value;
	}

	public String getUserId() throws Exception {
		String userId = (String)this.get().get("userId");
		return userId;
	}
}