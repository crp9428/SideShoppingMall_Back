package com.sideproject.shop.product;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
            parameters.put("P_PAGE", page);
            parameters.put("P_VIEW_NUM", 8);
            parameters.put("P_ORDER", order);

            list = service.productList(parameters);
        } catch (Exception e) {
            throw e;
        }

        return list;
    }
}
