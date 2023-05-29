package com.sideproject.shop.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sideproject.shop.mappers.UserMapper;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;

    public User getUser(Map<String, Object> parameters) {
        return mapper.login(parameters);
    }

    public int checkid(Map<String, Object> parameters) {
        return mapper.checkid(parameters);
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
}
