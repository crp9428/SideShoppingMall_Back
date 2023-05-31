package com.sideproject.shop.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sideproject.shop.mappers.ProductMapper;

@Service
public class ProductService {
    
    @Autowired
    ProductMapper mapper;

    public List<Product> productList(Map<String, Object> parameters) {
        return mapper.productList(parameters);
    }

    public void updateProductHit(Map<String, Object> parameters) {
        mapper.updateProductHit(parameters);
    }

    public Product getProduct(Map<String, Object> parameters) {
        return mapper.getProduct(parameters);
    }

    public List<String> productImageList(Map<String, Object> parameters) {
        return mapper.productImageList(parameters);
    }

    public int insertProduct(Map<String, Object> parameters) {
        return mapper.insertProduct(parameters);
    }

    public int insertProductImage(Map<String, Object> parameters) {
        return mapper.insertProductImage(parameters);
    }
}
