package com.unidt.mybatis.bean.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseInfo {
    //应答id
    public String response_id;

    //问卷编号
    public String surveys_no;
    /**
     * 获取问卷id
     */
    public String surveys_id;
    //问卷名称
    public String surveys_name;
    //问卷类型
    public String surveys_type;
    //问卷描述
    public String surveys_content;
    //用户id
    public String user_id;
    //是否完成应答
    public Integer is_end;
    public String  create_user;
    public String  start_time;
    public String end_time;
    public String create_time;
    public String update_time;

}
