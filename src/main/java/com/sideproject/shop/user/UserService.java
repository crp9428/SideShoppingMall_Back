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
}
