package com.unidt.bean.user;

import com.unidt.bean.constraints.NotNull;
import com.unidt.helper.interf.IToken;

public class UserBean implements IToken{
    @NotNull
    public String user_id;
    public String pwd;

    @NotNull
    public String company_id;
    public String product_id;
    public String role_id;
    public String email;
    public String phone;
    public int    sex;
    public int    max_limit;
    public int    status;
    public int    account_type;
    public String token = "";
    public String oldpwd;
    public String newPwd;

    //
    // 关联的问卷
    public String code;

    @Override
    public String getToken() {
        return token;
    }
}
