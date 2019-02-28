package com.unidt.mybatis.bean.surveys;

import lombok.Getter;
import lombok.Setter;

/**
 * 问卷主表
 */
@Getter
@Setter
public class SurveysInfo {
    //问卷主键
    public String surveys_id;
    //问卷编号
    public String surveys_no;
    //问卷名称
    public String surveys_name;
    //问卷状态 0 草稿，1发布
    public String surveys_status;
    //问卷类型
    public String surveys_type;
    //问卷备注
    public String surveys_desc;
    //删除标识，0 未删除，1已删除
    public String del_flag;
    //创建时间
    public String create_user;

    public String create_time;
    //修改时间
    public String update_user;
    public String update_time;

   }
