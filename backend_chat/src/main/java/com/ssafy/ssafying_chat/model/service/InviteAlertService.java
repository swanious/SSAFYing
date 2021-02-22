package com.ssafy.ssafying_chat.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.EnrollDto;
import com.ssafy.ssafying_chat.model.dto.InviteAlertDto;
import com.ssafy.ssafying_chat.model.mapper.EnrollMapper;
import com.ssafy.ssafying_chat.model.mapper.InviteAlertMapper;
import com.ssafy.ssafying_chat.model.mapper.Name2idMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InviteAlertService {
    
    @Autowired
    private InviteAlertMapper inviteAlertMapper;

    @Autowired
    private EnrollMapper enrollMapper;

    @Autowired
    private Name2idMapper name2idMapper;

    public void inviteAlertInsert(InviteAlertDto inviteAlertDto) throws Exception{
        inviteAlertMapper.inviteAlertInsert(inviteAlertDto);
    }

    public List<InviteAlertDto> inviteAlertSelectOne(String userId) throws Exception{
        return inviteAlertMapper.inviteAlertSelectOne(userId);
    }

    public void inviteAlertUpdate(int inviteAlertId) throws Exception{
        inviteAlertMapper.inviteAlertUpdate(inviteAlertId);
    }

    @Transactional
    public boolean inviteAlertDelete(Map<String, Object> map) throws Exception{
        boolean isAccept = false;
        // 승인 : 가입신청 생성
        if((int)map.get("isAccept") == 1){

            EnrollDto enrollDto = new EnrollDto();
            enrollDto.setChatRoomId((String)name2idMapper.roomIdSelectByName((String)map.get("name")));
            enrollDto.setUserId((String)map.get("userId"));

            if(enrollMapper.enrollSelectLogin(enrollDto) == null){
                isAccept = enrollMapper.enrollInsert(enrollDto) == 1;
            }
            
        }
        
        // 가입신청알림 삭제
        return inviteAlertMapper.inviteAlertDelete((int)map.get("inviteAlertId")) == 1 && isAccept;
    }
}
