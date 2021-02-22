package com.ssafy.ssafying.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.AlertDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlertMapper {
    public void alertInsert(AlertDto alertDto);
    public List<AlertDto> alertSelectAll();
    public List<AlertDto> alertSelectByKeyWord(Map<String, Object> map);
    public void alertDelete(int alertId);
}
