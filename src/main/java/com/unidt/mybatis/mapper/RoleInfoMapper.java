package com.unidt.mybatis.mapper;

import com.unidt.mybatis.bean.RoleInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RoleInfoMapper {

    @Select("select * from sys_role_info where role_id = #{id}")
    public RoleInfo selectRoleByID(String id);

    @Insert("insert into sys_role_info values(#{role_id},#{role_no},#{role_name})" )
    public void insertRole(RoleInfo info);

    @Update("update sys_role_info set role_no = #{role_no}," +
            "role_name=#{role_name} where role_id = #{role_id}")
    public void updateRole(RoleInfo info);

    @Delete("delete from sys_role_info where role_id=#{id}")
    public void deleteRole(String id);

}

