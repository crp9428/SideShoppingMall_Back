package com.sideproject.shop.product;

import java.util.ArrayList;
import java.util.Map;
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
    public List<Product> getProductList(@RequestParam String category, @RequestParam int page, @RequestParam String order) throws Exception {
        List<Product> list = new ArrayList<Product>();
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_CATEGORY", category);
            parameters.put("P_START", (page - 1) * 8);
            parameters.put("P_VIEW_NUM", 8);
            parameters.put("P_ORDER", order);

            list = service.productList(parameters);
        } catch (Exception e) {
            throw e;
        }

        return list;
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
            parameters.put("P_SEQ", 1);
            for(String image : product.getImages()) {
                parameters.put("P_IMAGE", image);

                service.insertProductImage(parameters);
                
                parameters.replace("P_SEQ", Integer.parseInt(parameters.get("P_SEQ").toString()) + 1);
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
}
