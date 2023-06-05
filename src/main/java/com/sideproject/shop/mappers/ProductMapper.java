package com.sideproject.shop.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sideproject.shop.product.Product;
import com.sideproject.shop.product.ProductImage;

@Mapper
public interface ProductMapper {
    int productCount(Map<String, Object> parameters);
    
    List<Product> productList(Map<String, Object> parameters);

    void updateProductHit(Map<String, Object> parameters);

    Product getProduct(Map<String, Object> parameters);

    List<ProductImage> productImageList(Map<String, Object> parameters);

    List<String> productSizeList(Map<String, Object> parameters);

    int insertProduct(Map<String, Object> parameters);

    int insertProductImage(Map<String, Object> parameters);

    int insertProductSize(Map<String, Object> parameters);

    int updateProduct(Map<String, Object> parameters);
}
