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
import org.springframework.web.bind.annotation.RequestBody;
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

            User user = service.getUser(parameters);
            if(user == null || user.getId() == null || user.getId().isEmpty()) {
                result.put("result", false);
                result.put("message", "해당되는 회원이 없습니다.");

                return result;
            } else if(user.getIsWithdraw() == 1) {
                result.put("result", false);
                result.put("message", "탈퇴한 회원입니다.");

                return result;
            }

            byte[] getByte = request.getParameter("password").getBytes("UTF-8");
            parameters.put("P_PASSWORD", HexUtils.toHexString(getByte));

            user = service.getUser(parameters);
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

            User user = service.getUser(parameters);
            if(user == null || user.getId() == null || user.getId().isEmpty()) {
                result.put("result", true);
                result.put("message", "사용 가능한 ID 입니다.");
            } else if(user.getIsWithdraw() == 1) {
                result.put("result", false);
                result.put("message", "사용 불가능한 ID 입니다.");
            } else if(user.getId() != null && !user.getId().isEmpty()) {
                result.put("result", false);
                result.put("message", "이미 사용중인 ID 입니다.");
            }
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/join_loginId")
    public Map<String, Object> join (@RequestBody User user) throws Exception {
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

            service.insert_user(parameters);
            
            service.insert_userId(parameters);
            
            service.insert_userInfo(parameters);

        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());

            throw e;
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/getUserInfo")
    public Map<String, Object> getUserInfo (@RequestParam String id) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_ID", id);

            User user = service.getUserInfo(parameters);
            result.put("userInfo", user);
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/updateUserInfo")
    public Map<String, Object> updateUserInfo (@RequestBody User user) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_ID", user.getId());
            parameters.put("P_EMAIL", user.getEmail());
            parameters.put("P_PHONE", user.getPhone());
            parameters.put("P_ADDRESS1", user.getAddress1());
            parameters.put("P_ADDRESS2", user.getAddress2());
            parameters.put("P_ADDRESS3", user.getAddress3());

            service.updateUserInfo(parameters);
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/withdraw")
    public Map<String, Object> withdraw (@RequestParam String id) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_ID", id);

            int sqlResult = service.withdraw(parameters);
            if(sqlResult != 1) {
                result.put("result", false);
                result.put("message", "탈퇴 처리에 실패하였습니다.");
            }
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @GetMapping("/findLoginId")
    public Map<String, Object> findLoginId(@RequestParam String name, @RequestParam int findType, @RequestParam String findData) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_FIND", 1);
            parameters.put("P_NAME", name);
            parameters.put("P_FINDTYPE", findType);
            parameters.put("P_FINDDATA", findData);

            String loginId = service.findLoginInfo(parameters);

            if(loginId == null || loginId.isEmpty()) {
                result.put("result", false);
                result.put("message", "해당되는 회원이 없습니다.");
            } else if (loginId.equals("1")) {
                result.put("result", false);
                result.put("message", "탈퇴한 회원입니다.");
            } else if (loginId.equals("2")) {
                result.put("result", false);
                result.put("message", "소셜 가입 회원입니다.");
            } else {
                result.put("loginId", loginId);
            }
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @GetMapping("findLoginPassword")
    public Map<String, Object> findLoginPassword(@RequestParam String loginId, @RequestParam int findType, @RequestParam String findData) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_FIND", 2);
            parameters.put("P_LOGIN_ID", loginId);
            parameters.put("P_FINDTYPE", findType);
            parameters.put("P_FINDDATA", findData);

            String id = service.findLoginInfo(parameters);
            if(id == null || id.isEmpty()) {
                result.put("result", false);
                result.put("message", "해당되는 회원이 없습니다.");
            } else if (loginId.equals("1")) {
                result.put("result", false);
                result.put("message", "탈퇴한 회원입니다.");
            } else {
                parameters.put("P_ID", id);

                String tempPassword = getTempPassword();
                boolean successSend = sendPassword(tempPassword);

                if(successSend) {
                    byte[] getByte = tempPassword.getBytes("UTF-8");
                    parameters.put("P_PASSWORD", HexUtils.toHexString(getByte));

                    service.updatePassword(parameters);
                } else {
                    result.put("result", false);
                    result.put("message", "초기 비밀번호 전송 실패");
                }
            }

        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/updatePassword")
    public Map<String, Object> updatePassword (@RequestParam String id, @RequestParam String passwordNow, @RequestParam String passwordNew) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("message", "success");

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("P_ID", id);
            parameters.put("P_PASSWORD", HexUtils.toHexString(passwordNow.getBytes("UTF-8")));
            
            int checkPassword = service.checkPassword(parameters);
            if(checkPassword == 1) {
                parameters.put("P_PASSWORD", HexUtils.toHexString(passwordNew.getBytes("UTF-8")));

                service.updatePassword(parameters);
            } else {
                result.put("result", false);
                result.put("message", "비밀번호가 틀렸습니다.");
            }
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    public boolean sendPassword(String password) {
        // 실제로는 임시값 생성해서 이메일/핸드폰번호로 보내줘야 하지만 나중에 작업할 예정
        return true;
    }

    public String getTempPassword() {
        // 실제로는 임시값 생성해서 이메일/핸드폰번호로 보내줘야 하지만 나중에 작업할 예정
        return "1111";
    }
}
