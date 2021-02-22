package com.ssafy.ssafying.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.UserDto;
import com.ssafy.ssafying.model.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    
    @Autowired
    private UserMapper userMapper;

    public boolean userInsert(UserDto userDto) throws Exception{
        return userMapper.userInsert(userDto) == 1;
    }

    public List<UserDto> userSelectAll() throws Exception{
        return userMapper.userSelectAll();
    }

    public UserDto userSelectOne(String userId) throws Exception{
        return userMapper.userSelectOne(userId);
    }

    public UserDto userSelectByKeyWord(String key, String word) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("word", word);
        
        return userMapper.userSelectByKeyWord(map);
    }

    public boolean userUpdate(UserDto userDto) throws Exception{
        userDto.setIsActive(1);

        return userMapper.userUpdate(userDto) == 1;
    }

    public boolean userUpdatePassword(String userId, String oldPassword, String newPassword) throws Exception{
        UserDto userDto = userMapper.userSelectOne(userId);
        if(!userDto.getPassword().equals(oldPassword)) {
            throw new Exception();
        }
        userDto.setPassword(newPassword);

        return userMapper.userUpdate(userDto) == 1;
    }

    public boolean userDelete(String userId) throws Exception{
        return userMapper.userDelete(userId) == 1;
    }

    public UserDto userLogin(Map<String, Object> map) throws Exception{
        UserDto userDto = userMapper.userLogin(map);
        userDto.setIsActive(1);
        userMapper.userUpdate(userDto);

        return userDto;
    }

    public boolean userLogout(String userId) throws Exception{
        UserDto userDto = new UserDto();
        userDto.setIsActive(0); 
        userDto.setUserId(userId);

        return userMapper.userUpdate(userDto) == 1;
    }
}