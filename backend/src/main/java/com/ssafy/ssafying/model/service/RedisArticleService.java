package com.ssafy.ssafying.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisArticleService {
      public static final Logger logger = LoggerFactory.getLogger(RedisArticleService.class);

      @Autowired
      RedisTemplate<String,Object> redisTemplate;

      public Map<String,Object> articleMain() {
            Map<String, Object> resultMap = new HashMap<>();
            
            ListOperations<String,Object> solop = redisTemplate.opsForList();
            SetOperations<String, Object> sosop = redisTemplate.opsForSet();
            HashOperations<String,Object,Object> soohop = redisTemplate.opsForHash();

            Set<Object> mainNameSet = sosop.members("mainList");
            for(Object categorySet : mainNameSet){
                  Set<Object> categoryDetail = sosop.members((String)categorySet);
                  Map<String,Object> articleMap = new HashMap<>();
                  for(Object article : categoryDetail){
                        Map<Object, Object> articleDetailMap = soohop.entries((String)article);
                        articleMap.put((String)article, articleDetailMap);
                  }
                  resultMap.put((String)categorySet, articleMap);
            }
            List<Object> recommend = solop.range("recommend", 0, solop.size("recommend"));
            Map<String,Object> inMap = new HashMap<>();
            for(Object list : recommend){
                  Map<Object,Object> mapValue = soohop.entries((String)list);
                  inMap.put((String)list, mapValue);
            }
            resultMap.put("recommend",inMap);
            return resultMap;
      }
}
