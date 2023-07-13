package com.sideproject.shop.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    
    @Autowired
    CartService service;

    @GetMapping("/getCarts")
    public Map<String, Object> getCarts(@RequestParam String userId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_USER_ID", userId);

            List<Cart> list = service.getCarts(parameters);
            
            result.put("items", list);
            result.put("counts", list.size());

        } catch (Exception e) {
            throw e;
        }

        return result;
    }

}
