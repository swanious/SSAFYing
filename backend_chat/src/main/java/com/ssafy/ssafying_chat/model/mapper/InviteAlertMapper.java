package com.ssafy.ssafying_chat.model.mapper;

import java.util.List;

import com.ssafy.ssafying_chat.model.dto.InviteAlertDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InviteAlertMapper {
    public int inviteAlertInsert(InviteAlertDto inviteAlertDto) throws Exception;
    public List<InviteAlertDto> inviteAlertSelectOne(String userId) throws Exception;
    public int inviteAlertUpdate(int inviteAlertId) throws Exception;
    public int inviteAlertDelete(int inviteAlertId) throws Exception;
}
