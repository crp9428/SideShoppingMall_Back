package com.sideproject.shop.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/checkid")
    public Map<String, Object> checkid (@RequestParam String loginId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_LOGIN_ID", loginId);

            System.out.println(parameters.toString());

            int sqlResult = service.checkid(parameters);
            System.out.println("sqlResult: " + sqlResult);
            if(sqlResult == 1) {
                result.put("result", false);
                result.put("message", "이미 사용중인 ID 입니다.");
            } else {
                result.put("result", true);
                result.put("message", "사용 가능한 ID 입니다.");
            }

        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/join_loginId")
    public Map<String, Object> join (User user) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");
        
        try {
            System.out.println(user.toString());
            if(user.getLoginId() == null || user.getLoginId().isEmpty()) {
                result.put("result", false);
                result.put("message", "로그인 ID 는 필수값입니다.");

                return result;
            } else if(user.getPassword() == null || user.getPassword().isEmpty()) {
                result.put("result", false);
                result.put("message", "패스워드는 필수값입니다.");

                return result;
            }

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_ID", UUID.randomUUID().toString().replaceAll("-", ""));
            parameters.put("P_LOGIN_ID", user.getLoginId());
            byte[] getByte = user.getPassword().getBytes("UTF-8");
            parameters.put("P_PASSWORD", HexUtils.toHexString(getByte));
            parameters.put("P_NAME", user.getName());
            parameters.put("P_EMAIL", user.getEmail());
            parameters.put("P_PHONE", user.getPhone());
            parameters.put("P_BIRTH", user.getBirth());
            parameters.put("P_GENDER", user.getGender());
            parameters.put("P_ADDRESS1", user.getAddress1());
            parameters.put("P_ADDRESS2", user.getAddress2());
            parameters.put("P_ADDRESS3", user.getAddress3());

            System.out.println(parameters.toString());

            int sqlResult = 0;

            sqlResult = service.insert_user(parameters);
            if(sqlResult != 1) {
                result.put("result", false);
                result.put("message", "회원가입에 실패하였습니다.");

                return result;
            }

            sqlResult = service.insert_userId(parameters);
            if(sqlResult != 1) {
                result.put("result", false);
                result.put("message", "회원가입에 실패하였습니다.");

                return result;
            }

            sqlResult = service.insert_userInfo(parameters);
            if(sqlResult != 1) {
                result.put("result", false);
                result.put("message", "회원가입에 실패하였습니다.");

                return result;
            }

        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());

            throw e;
        }

        return result;
    } 
}
