package com.sideproject.shop.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sideproject.shop.product.Product;

@Mapper
public interface ProductMapper {
    List<Product> productList(Map<String, Object> parameters);

    void updateProductHit(Map<String, Object> parameters);

    Product getProduct(Map<String, Object> parameters);

    List<String> productImageList(Map<String, Object> parameters);

    int insertProduct(Map<String, Object> parameters);

    int insertProductImage(Map<String, Object> parameters);
}
