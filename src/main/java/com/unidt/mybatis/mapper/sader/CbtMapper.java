package com.unidt.mybatis.mapper.sader;

import com.unidt.mybatis.bean.question.CbtUser;
import com.unidt.mybatis.bean.question.QuestionInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *CBT mapper
 */

@Mapper
public interface CbtMapper {
    @Select("select c.* from tab_cbt_user c where c.create_user=#{user_id} and c.del_flag='0'")
    public List<CbtUser> getCbtUserList(String user_id);

    @Select("select c.* from tab_cbt_user c where c.cbt_id=#{cbt_id} and c.del_flag='0'")
    public CbtUser getCbtUserById(String cbt_id);

    @Select("select c.* from tab_cbt_user c where c.response_id=#{response_id} and c.del_flag='0' limit 1")
    public CbtUser getCbtUserByResponseId(String response_id);

    @Insert("insert into tab_cbt_user(response_id,cbt_id,cbt_name,cbt_name_select, cbt_think,cbt_action,cbt_mood,cbt_phy,create_user,del_flag,create_time,update_time) values(#{response_id},#{cbt_id},#{cbt_name},#{cbt_name_select},#{cbt_think},#{cbt_action},#{cbt_mood}," +
            "#{cbt_phy}, #{create_user}, #{del_flag},#{create_time},#{update_time})")
    public void insertCbtUser(CbtUser info);

    @Update("update tab_cbt_user set cbt_name = #{cbt_name}, cbt_name_select=#{cbt_name_select},cbt_think = #{cbt_think}," +
            "cbt_action = #{cbt_action}, cbt_mood = #{cbt_mood}, cbt_phy = #{cbt_phy},create_user=#{create_user}," +
            " update_time = #{update_time}, update_num = #{update_num} where cbt_id = #{cbt_id}")
    public void updateCbtUser(CbtUser info);


    @Select("select c.update_num  from tab_cbt_user c where c.cbt_id = #{cbt_id}")
    public Integer getUpdateNum(String cbt_id);

    @Update("update tab_cbt_user set del_flag='1' where cbt_id=#{cbt_id}")
    public void delCbtUserById(String cbt_id);
}

