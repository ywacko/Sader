package com.unidt.mybatis.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SSO {
    public String id;
    public String user_id;
    public String role_no;
    public String token;
    public Date login_date;
    public String expire_date;

}
