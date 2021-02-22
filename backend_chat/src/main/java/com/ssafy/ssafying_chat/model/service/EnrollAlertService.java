package com.ssafy.ssafying_chat.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.EnrollAlertDto;
import com.ssafy.ssafying_chat.model.dto.EnrollDto;
import com.ssafy.ssafying_chat.model.mapper.EnrollAlertMapper;
import com.ssafy.ssafying_chat.model.mapper.EnrollMapper;
import com.ssafy.ssafying_chat.model.mapper.Name2idMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollAlertService {
    
    @Autowired
    private EnrollAlertMapper enrollAlertMapper;

    @Autowired
    private EnrollMapper enrollMapper;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private Name2idMapper name2idMapper;

    public String enrollAlertInsert(EnrollAlertDto enrollAlertDto) throws Exception{
        String hostId = chatRoomService.chatRoomSelectOne(enrollAlertDto.getChatRoomId()).getHostId();
        enrollAlertDto.setHostId(hostId);
        enrollAlertMapper.enrollAlertInsert(enrollAlertDto);
        return hostId;
    }

    public List<EnrollAlertDto> enrollAlertSelectOne(String hostId) throws Exception{
        return enrollAlertMapper.enrollAlertSelectOne(hostId);
    }

    public void enrollAlertUpdate(int enrollAlertId) throws Exception{
        enrollAlertMapper.enrollAlertUpdate(enrollAlertId);
    }

    @Transactional
    public boolean enrollAlertDelete(Map<String, Object> map) throws Exception{
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
        return enrollAlertMapper.enrollAlertDelete((int)map.get("enrollAlertId")) == 1 && isAccept;
    }
}
