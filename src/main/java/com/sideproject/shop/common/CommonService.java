package com.sideproject.shop.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sideproject.shop.mappers.CommonMapper;

@Service
public class CommonService {
    
    @Autowired
    CommonMapper mapper;

    public List<Map<String, Object>> getCommonCode(Map<String, Object> parameters) {
        return mapper.getCommonCode(parameters);
    }
}
