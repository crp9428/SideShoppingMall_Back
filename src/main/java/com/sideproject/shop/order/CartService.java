package com.sideproject.shop.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sideproject.shop.mappers.CartMapper;

@Service
public class CartService {
    
    @Autowired
    CartMapper mapper;

    List<Cart> getCarts(Map<String, Object> parameters) {
        return mapper.getCarts(parameters);
    }
}
