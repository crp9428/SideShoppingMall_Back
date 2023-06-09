package com.sideproject.shop.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sideproject.shop.user.User;

@Mapper
public interface UserMapper {
    User getUser(Map<String, Object> parameters);

    List<User> getUserInfo(Map<String, Object> parameters);

    int insert_user(Map<String, Object> parameters);

    int insert_userId(Map<String, Object> parameters);

    int insert_userInfo(Map<String, Object> parameters);

    int updateUserInfo(Map<String, Object> parameters);

    int withdraw(Map<String, Object> parameters);

    String findLoginInfo(Map<String, Object> parameters);

    int checkPassword(Map<String, Object> parameters);
    
    int updatePassword(Map<String, Object> parameters);

    List<Map<String, Object>> getUserList(Map<String, Object> parameters);
}
