package com.unidt.bean.user;

import com.unidt.helper.interf.IToken;

public class UserLimitBean implements IToken{
    String user_id;
    String company_id;
    String product_id;
    String code;
    int    max_limit;
    int    cur_times;
    int    total;      // 该公司名下消耗指定产品的次数

    String token = "";

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMax_limit() {
        return max_limit;
    }

    public void setMax_limit(int max_limit) {
        this.max_limit = max_limit;
    }

    public int getCur_times() {
        return cur_times;
    }

    public void setCur_times(int cur_times) {
        this.cur_times = cur_times;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String getToken() {
        return token;
    }
}
