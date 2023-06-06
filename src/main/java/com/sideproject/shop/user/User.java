package com.sideproject.shop.user;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String id;
    private int isAdmin;
    private int isWithdraw;
    private Timestamp joinDate;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String birth;
    private String gender;
    private String address1;
    private String address2;
    private String address3;
}
