package com.ssafy.ssafying.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.AlertDto;
import com.ssafy.ssafying.model.mapper.AlertMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    
    @Autowired
    private AlertMapper alertMapper;

    public void alertInsert(AlertDto alertDto){
        alertMapper.alertInsert(alertDto);
    }

    public List<AlertDto> alertSelectAll(){
        return alertMapper.alertSelectAll();
    }

    public List<AlertDto> alertSelectByKeyWord(String key, String word){
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("word", word);
        
        return alertMapper.alertSelectByKeyWord(map);
    }

    public void alertDelete(int alertId){
        alertMapper.alertDelete(alertId);
    }
}
