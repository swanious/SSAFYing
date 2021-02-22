package com.ssafy.ssafying_chat.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafying_chat.model.dto.EnrollDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnrollMapper {
    public int enrollInsert(EnrollDto enrollDto) throws Exception;
    public EnrollDto enrollSelectLogin(EnrollDto enrollDto) throws Exception;
    public List<EnrollDto> enrollSelectByKeyWord(Map<String, Object> map) throws Exception;
    public int enrollDelete(Map<String, Object> map) throws Exception;
}
