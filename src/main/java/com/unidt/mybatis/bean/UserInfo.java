package com.unidt.mybatis.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    public String user_id;
    public String account;
    public String password;
    public String user_name;
    public String phone;
    public String role_id;
    public String role_no;
    public char delflag;
}
