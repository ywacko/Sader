package com.unidt.mybatis.mapper;

import com.unidt.mybatis.bean.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoMapper {

    @Select("select * from sys_user_info where user_id = #{id}")
    public UserInfo selectUserByID(String id);

    @Insert("insert into sys_user_info values(#{user_id},#{account},#{password},#{user_name},#{phone},#{role_id}," +
    "#{role_no},#{delflag})")
    public void insertUser(UserInfo info);

    @Update("update sys_user_info set account = #{account}, password = #{password}, user_name = #{user_name}," +
            "phone = #{phone}, role_id = #{role_id}, role_no = #{role_no}, delflag = #{delflag} where user_id = #{user_id}")
    public void updateUser(UserInfo info);

    @Delete("delete from sys_user_info where user_id=#{id}")
    public void deleteUser(String id);

}

