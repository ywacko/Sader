package com.unidt.bean.login;

import com.unidt.bean.constraints.NotNull;
import com.unidt.helper.interf.IToken;

public class LoginBean  implements IToken{
    @NotNull
    public String user_id = null;
    @NotNull
    public String pwd = null;
    @NotNull
    public String product_id = null;
    public String company_id = null;
    

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String getToken() {
        return null;
    }
}
