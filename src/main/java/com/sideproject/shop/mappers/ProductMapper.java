package com.sideproject.shop.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sideproject.shop.product.Product;
import com.sideproject.shop.product.ProductImage;

@Mapper
public interface ProductMapper {
    List<Product> productList(Map<String, Object> parameters);

    Product getProduct(Map<String, Object> parameters);

    List<ProductImage> productImageList(Map<String, Object> parameters);

    ProductImage getProductImage(Map<String, Object> parameters);

    int insert_product(Map<String, Object> parameters);

    int insert_product_image(Map<String, Object> parameters);
}
