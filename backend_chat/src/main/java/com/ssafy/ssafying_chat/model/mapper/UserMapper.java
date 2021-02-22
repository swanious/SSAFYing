package com.ssafy.ssafying_chat.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.UserDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper{
    public UserDto userSelectByKeyWord(Map<String, Object> map) throws SQLException;

    public UserDto userLogin(Map<String, Object> map) throws SQLException;
}