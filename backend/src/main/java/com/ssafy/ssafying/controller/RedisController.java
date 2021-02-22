package com.ssafy.ssafying.controller;

import java.util.Map;

import com.ssafy.ssafying.model.service.RedisArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/redis")
public class RedisController {
      

      @Autowired
      RedisArticleService redisArticleService;

      @ApiOperation(value = "메인 페이지 구성 요소", notes = "메인페이지에 구성될 요소들을 redis에서 가져온다. free / anonymous / grade / it / job / life / g_free / g_anonymous / g_study ...... g_는 그룹 나머지는 게시글형식(Article)이다.", response = String.class)
      @GetMapping("/main")
      public ResponseEntity<Map<String,Object>> getData(){
            return new ResponseEntity<Map<String,Object>>(redisArticleService.articleMain(),HttpStatus.OK);
      }


}
