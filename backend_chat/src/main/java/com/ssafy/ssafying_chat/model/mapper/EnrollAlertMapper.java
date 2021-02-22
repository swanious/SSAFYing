package com.ssafy.ssafying_chat.model.mapper;

import java.util.List;

import com.ssafy.ssafying_chat.model.dto.EnrollAlertDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnrollAlertMapper {
    public int enrollAlertInsert(EnrollAlertDto enrollAlertDto) throws Exception;
    public List<EnrollAlertDto> enrollAlertSelectOne(String hostId) throws Exception;
    public int enrollAlertUpdate(int enrollAlertId) throws Exception;
    public int enrollAlertDelete(int enrollAlertId) throws Exception;
}
