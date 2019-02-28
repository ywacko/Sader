package com.unidt.services.push;

public interface IPush {

    /**
     * 给指定的手机号发送短信
     * @param phone
     * @param msg
     * @return
     */
    int sendSMS(String phone, String msg);

    /**
     * 给指定号码发送验证码
     * @param phone
     * @return
     */
    int sendCode(String phone);
}
