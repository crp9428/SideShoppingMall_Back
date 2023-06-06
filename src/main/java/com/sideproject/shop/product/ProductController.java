package com.sideproject.shop.product;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    
    @Autowired
    ProductService service;

    @GetMapping("/productList")
    public Map<String, Object> getProductList(@RequestParam String category, @RequestParam int page, @RequestParam String order) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_CATEGORY", category);
            parameters.put("P_START", (page - 1) * 8);
            parameters.put("P_VIEW_NUM", 8);
            parameters.put("P_ORDER", order);

            List<Product> list = service.productList(parameters);
            int count = service.productCount(parameters);
            
            result.put("items", list);
            result.put("counts", count);

        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    @Transactional
    @GetMapping("/getProduct")
    public Product getProduct(@RequestParam int id) throws Exception {
        Product product = new Product();
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_PRODUCT_ID", id);

            service.updateProductHit(parameters);

            product = service.getProduct(parameters);

            product.setImages(service.productImageList(parameters));

            parameters.put("P_GET_NAME", true);
            product.setSizes(service.productSizeList(parameters));
        } catch (Exception e) {
            throw e;
        }

        return product;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/addProduct")
    public Map<String, Object> addProduct(@RequestBody Product product) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_NAME", product.getName());
            parameters.put("P_PRICE", product.getPrice());
            parameters.put("P_CATEGORY_CODE", product.getCategoryCode());
            parameters.put("P_SIZEINFO", product.getSizeInfo());
            parameters.put("P_CONTENT", product.getContent());
            parameters.put("P_STOCK", product.getStock());

            service.insertProduct(parameters);

            parameters.put("P_ID", parameters.get("ID"));
            for(ProductImage image : product.getImages()) {
                parameters.put("P_IMAGE", image.getImage());

                service.insertProductImage(parameters);
            }

            for(String size : product.getSizes()) {
                parameters.put("P_SIZE", size);

                service.insertProductSize(parameters);
            }

        }  catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());

            throw e;
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/updateProduct")
    public Map<String, Object> updateProduct(@RequestBody Product product) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_ID", product.getId());
            parameters.put("P_NAME", product.getName());
            parameters.put("P_PRICE", product.getPrice());
            parameters.put("P_SIZEINFO", product.getSizeInfo());
            parameters.put("P_CONTENT", product.getContent());
            parameters.put("P_STOCK", product.getStock());

            service.updateProduct(parameters);

            List<ProductImage> images = (product.getImages() == null || product.getImages().size() == 0) ? new ArrayList<ProductImage>() : product.getImages();
            for(ProductImage image : images.stream().filter(e -> e.getImage() != null).collect(Collectors.toList())) {
                parameters.put("P_IMAGE", image.getImage());
                parameters.put("P_SEQ", null);

                service.insertProductImage(parameters);
            }
            
            for(ProductImage image : images.stream().filter(e -> e.getImage() == null).collect(Collectors.toList())) {
                parameters.put("P_IMAGE", null);
                parameters.put("P_SEQ", image.getSeq());

                service.deleteProductImage(parameters);
            }
            
            parameters.put("P_PRODUCT_ID", product.getId());
            parameters.put("P_CATEGORY_CODE", product.getCategoryCode());
            parameters.put("P_GET_NAME", false);

            Collection<String> inputList = product.getSizes();
            Collection<String> dbList = service.productSizeList(parameters);

            List<String> addSizeList = new ArrayList<String>(inputList);
            List<String> deleteSizeList = new ArrayList<String>(dbList);

            addSizeList.removeAll(dbList);
            deleteSizeList.removeAll(inputList);

            for(String size : addSizeList) {
                parameters.put("P_SIZE", size);

                service.insertProductSize(parameters);
            }

            for(String size : deleteSizeList) {
                parameters.put("P_SIZE", size);

                service.deleteProductSize(parameters);
            }
        }  catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());

            throw e;
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/deleteProduct")
    public Map<String, Object> deleteProduct(@RequestParam String id) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_ID", id);

            // service.deleteProductSize(parameters);
            // service.deleteProductImage(parameters);

            service.deleteProduct(parameters);
        }  catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());

            throw e;
        }

        return result;
    }
}
