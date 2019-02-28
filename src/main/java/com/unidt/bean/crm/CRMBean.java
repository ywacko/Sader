package com.unidt.bean.crm;

import com.unidt.bean.constraints.NotNull;
import com.unidt.helper.interf.IToken;

public class CRMBean implements IToken{
    //
    // 公司编码(由用户指定，可以是英文等)
    @NotNull
    public String company_id = "";

    //
    // 公司中文名
    @NotNull
    public String company_name = "";

    //
    // 公司地址
    public String address = "";

    //
    // 联系人姓名
    public String contactor = "";

    //
    // 联系人手机号
    public String contact_phone = "";

    //
    // 联系人email
    public String contact_email = "";

    //
    // 行业类别
    public String industry_type = "";

    //
    // 渠道: 直营 / 代理,
    // channel为空是直营； channel不为空，取母公司名
    public String channel = "";

    //
    // 公司信息状态  0 删除； 1 正常；
    public int status = 0;

    //
    // token, 添加公司必须验证token
    public String token = "";

    @Override
    public String getToken() {
        return token;
    }
}
