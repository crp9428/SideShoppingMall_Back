package com.sideproject.shop.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sideproject.shop.user.User;

@Mapper
public interface UserMapper {
    User getUser(Map<String, Object> parameters);

    User getUserInfo(Map<String, Object> parameters);

    int insert_user(Map<String, Object> parameters);

    int insert_userId(Map<String, Object> parameters);

    int insert_userInfo(Map<String, Object> parameters);

    int updatePassword(Map<String, Object> parameters);

    int updateUserInfo(Map<String, Object> parameters);

    int withdraw(Map<String, Object> parameters);

    String findLoginId(Map<String, Object> parameters);
}
