package com.unidt.mybatis.mapper;

import com.unidt.mybatis.bean.SSO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SSOMapper {

    @Select("select o.user_id, o.role_no, o.token, o.login_date,o.expire_date from tab_sso o where o.id = #{id}")
    public SSO selectSSOByID(String id);

    /**
     * 只查询token是否存在
     * @param token
     * @return
     */
    @Select("select o.id, o.user_id, o.role_no, o.token, o.login_date,o.expire_date from tab_sso  o where o.token = #{token}")
    public SSO getSSOByToken(String token);

    /**
     * 根据当前时间以及token，获取有效的用户信息
     * @param token
     * @param curtime 时间戳，数值形式
     * @return
     */
    @Select("select o.id, o.user_id, o.role_no, o.token, o.login_date,o.expire_date from tab_sso where token = #{token} and expire_date > #{curtime}")
    public SSO getEffectSSOByToken(String token, String curtime);

    @Insert("insert into tab_sso values(#{id},#{user_id},#{role_no},#{token},#{login_date}," +
    "#{expire_date})")
    public void insertSSO(SSO info);

    @Update("update tab_sso set user_id = #{user_id}, role_no = #{role_no}, token = #{token}," +
            "login_date = #{login_date}, delexpire_date_flag = #{delexpire_date_flag} where id = #{id}}")
    public void updateSSO(SSO info);


    @Update("update tab_sso set expire_date = #{expire_date} where user_id = #{user_id}")
    public void updateDeadline(SSO info);


    @Delete("delete from tab_sso where id=#{id}")
    public void deleteSSO(String id);

}

