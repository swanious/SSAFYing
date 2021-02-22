package com.ssafy.ssafying.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.FollowDto;
import com.ssafy.ssafying.model.mapper.FollowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
      
      @Autowired
      FollowMapper followMapper;

      public boolean followInsert(String followerId, String followingId) throws Exception{

            FollowDto followDto = new FollowDto();
            followDto.setFollowerId(followerId);
            followDto.setFollowingId(followingId);

            return followMapper.followInsert(followDto) == 1;
      }

      public boolean followDelete(String followerId, String followingId) throws Exception{

            FollowDto followDto = new FollowDto();
            followDto.setFollowerId(followerId);
            followDto.setFollowingId(followingId);

            return followMapper.followDelete(followDto) == 1;
      }

      public FollowDto followSelectOne(String followerId, String followingId) throws Exception{
            
            FollowDto followDto = new FollowDto();
            followDto.setFollowerId(followerId);
            followDto.setFollowingId(followingId);

            return followMapper.followSelectOne(followDto);
      }

      public List<FollowDto> followSelectByKeyWord(String key, String word) throws Exception{

            Map<String, Object> map = new HashMap<>();
            map.put("key", key);
            map.put("word", word);

            return followMapper.followSelectByKeyWord(map);
      }

      public List<FollowDto> followSelectAll() throws Exception{
            return followMapper.followSelectAll();
      }
      
}
