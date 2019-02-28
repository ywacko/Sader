package com.unidt.bean.login;

import com.unidt.bean.constraints.NotNull;
import com.unidt.helper.interf.IToken;

public class LogOutBean implements IToken{
    @NotNull
    public String user_id;
    @NotNull
    public String token;
    public String product_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
