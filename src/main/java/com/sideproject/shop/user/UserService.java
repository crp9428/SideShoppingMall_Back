package com.sideproject.shop.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sideproject.shop.mappers.UserMapper;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;

    public User getUser(Map<String, Object> parameters) {
        return mapper.getUser(parameters);
    }

    public List<User> getUserInfo(Map<String, Object> parameters) {
        return mapper.getUserInfo(parameters);
    }

    public int insert_user(Map<String, Object> parameters) {
        return mapper.insert_user(parameters);
    }

    public int insert_userId(Map<String, Object> parameters) {
        return mapper.insert_userId(parameters);
    }

    public int insert_userInfo(Map<String, Object> parameters) {
        return mapper.insert_userInfo(parameters);
    }

    public int updateUserInfo(Map<String, Object> parameters) {
        return mapper.updateUserInfo(parameters);
    }

    public int withdraw(Map<String, Object> parameters) {
        return mapper.withdraw(parameters);
    }

    public String findLoginInfo(Map<String, Object> parameters) {
        return mapper.findLoginInfo(parameters);
    }

    public int checkPassword(Map<String, Object> parameters) {
        return mapper.checkPassword(parameters);
    }
    
    public int updatePassword(Map<String, Object> parameters) {
        return mapper.updatePassword(parameters);
    }

}
