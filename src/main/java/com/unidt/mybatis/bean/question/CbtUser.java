package com.unidt.mybatis.bean.question;

import lombok.Getter;
import lombok.Setter;

/**
 * CBT主表
 */
@Getter
@Setter
public class CbtUser {
    //CBT主键
    public String cbt_id;
    // 'cbt名称'
    public String cbt_name;
    //'cbt思维'
    public String cbt_think;
    //'cbt行为'
    public String cbt_action;
    //'cbt情绪'
    public String cbt_mood;
    //'cbt生理反应'
    public String cbt_phy;
    //'创建用户'
    public String create_user;
    //'创建时间'
    public String create_time;

    public String del_flag;

    public String update_time;

    public Integer  update_num;
    public String response_id;
    //选择名称选项
    public String cbt_name_select;


 }
