package com.sideproject.shop.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sideproject.shop.user.User;

@Mapper
public interface UserMapper {
    User login(Map<String, Object> parameters);
}
