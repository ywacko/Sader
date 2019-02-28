package com.unidt.mybatis.mapper;

import com.unidt.mybatis.bean.DictInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DictInfoMapper {

    @Select("select * from sys_dict_info where dict_id = #{id}")
    public DictInfo selectDictByID(String id);

    @Insert("insert into sys_dict_info values(#{dict_id},#{dict_code},#{dict_name}," +
            "#{dict_parent_code},#{dict_parent_name})")
    public void insertDict(DictInfo info);

    @Update("update sys_dict_info set dict_code = #{dict_code}," +
            "dict_name=#{dict_name}, dict_parent_code= #{dict_parent_code}, dict_parent_name= #{dict_parent_name} where dict_id = #{dict_id}")
    public void updateDict(DictInfo info);

    @Delete("delete from sys_dict_info where dict_id=#{id}")
    public void deleteDict(String id);

}

