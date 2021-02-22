package com.ssafy.ssafying.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.UserDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper{
    public int userInsert(UserDto userDto) throws SQLException;
    public List<UserDto> userSelectAll() throws SQLException;
    public UserDto userSelectOne(String userId) throws SQLException;
    public UserDto userSelectByKeyWord(Map<String, Object> map) throws SQLException;
    public int userUpdate(UserDto userDto) throws SQLException;
    public int userDelete(String userId) throws SQLException;

    public UserDto userLogin(Map<String, Object> map) throws SQLException;
}