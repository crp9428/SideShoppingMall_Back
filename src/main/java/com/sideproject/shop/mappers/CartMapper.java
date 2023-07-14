package com.sideproject.shop.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sideproject.shop.order.Cart;

@Mapper 
public interface CartMapper {
    public List<Cart> getCarts(Map<String, Object> parameters);

    public void upsertCart(Map<String, Object> parameters);

    public void deleteCart(Map<String, Object> parameters);
}
