package com.sideproject.shop.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    
    @Autowired
    CommonService service;

    @GetMapping("/getCommonCode")
    public Map<String, Object> getCommonCode(
        @RequestParam(value = "group1", required = false, defaultValue = "") String group1
        , @RequestParam(value = "group2", required = false, defaultValue = "") String group2
    ) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_GROUP_1", group1);
            parameters.put("P_GROUP_2", group2);

            List<Map<String, Object>> list = service.getCommonCode(parameters);
            result.put("commonCodeList", list);
            
        } catch (Exception e) {
            throw e;
        }

        return result;
    }
}
