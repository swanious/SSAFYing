package com.ssafy.ssafying.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.model.dto.FollowDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {
      public int followInsert(FollowDto followDto) throws SQLException;
      public int followDelete(FollowDto followDto) throws SQLException;
      public FollowDto followSelectOne(FollowDto followDto) throws SQLException;
      public List<FollowDto> followSelectByKeyWord(Map<String, Object> map) throws SQLException;
      public List<FollowDto> followSelectAll() throws SQLException;
}
