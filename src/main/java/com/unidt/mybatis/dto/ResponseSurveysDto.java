package com.unidt.mybatis.dto;

/**
 * 应答问卷
 */
public class ResponseSurveysDto {
    /**
     * 应答id
     */
    public String response_id;
    /**
     * 问卷名称
     */
    public String surveys_name;
    /**
     * 问卷id
     */
    public String surveys_id;
    /**
     * 问卷编号
     */
    public String surveys_no;
    /**
     * 问卷类型（问候，面试官，方案等）
     */
    public String surveys_type;
    /**
     * 用户编号
     */
    public String user_id;
    /**
     * 是否完成应答（0未完成，1完成）
     */
    public Integer is_end;
    /**
     * 开始时间
     */
    public String start_time;
    /**
     * 结束时间
     */
    public String end_time;

    public String token;

    public int is_end_chapter;

    public String next_surveys;

}
