package com.unidt.mybatis.bean.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OptionAnswer {
    //答案选项
    public String option_answer_id;
    //响应章节
    public String chapter_answer_id;
    //选项id
    public String  option_id;
    //选项答案
    public String option_answer;
    //选项url
    public String option_answer_url;
    //选项显示位置
    public Integer option_answer_seq;
    //结束时间
    public String end_time;
    //开始时间
    public String start_time;

}
