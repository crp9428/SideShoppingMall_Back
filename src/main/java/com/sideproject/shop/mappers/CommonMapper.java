package com.sideproject.shop.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
    
    List<Map<String, Object>> getCommonCode(Map<String, Object> parameters);

}
