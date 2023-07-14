package com.sideproject.shop.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/updateCart")
    public Map<String, Object> updateCart(@RequestBody ObjectNode node) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            ObjectMapper om = new ObjectMapper();
            int method = Integer.parseInt(node.get("method").asText());
            Cart cart = om.treeToValue(node.get("cart"), Cart.class);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_USER_ID", cart.getUserId());
            parameters.put("P_PRODUCT_ID", cart.getProductId());
            parameters.put("P_SIZE", cart.getSizeCode());
            
            if(method == 1 || method == 2) {
                parameters.put("P_COUNT", cart.getCnt());

                service.upsertCart(parameters);
            } else if(method == 3) {
                service.deleteCart(parameters);
            } else {
                result.put("result", false);
                result.put("message", "잘못된 동작입니다.");
            }
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());

            throw e;
        }

        return result;
    }

}
