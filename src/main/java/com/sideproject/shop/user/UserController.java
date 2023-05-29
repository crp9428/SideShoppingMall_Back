package com.sideproject.shop.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService service;
    
    @PostMapping("/login")
    public Map<String, Object> login(HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("P_LOGIN_ID", request.getParameter("loginId"));
            
            byte[] getByte = request.getParameter("password").getBytes("UTF-8");
            parameters.put("P_PASSWORD", HexUtils.toHexString(getByte));

            System.out.println(parameters.toString());

            User user = service.getUser(parameters);

            if(user == null || user.getId() == null || user.getId().isEmpty()) {
                result.put("result", false);
                result.put("message", "해당되는 회원이 없습니다.");
            } else {
                result.put("userInfo", user);
            }
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
}
