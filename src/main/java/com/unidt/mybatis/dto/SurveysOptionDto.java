package com.unidt.mybatis.dto;

/**
 * 问卷选项
 */
public class SurveysOptionDto {
    //选项id
    public String option_id;
    //选项类型
    public String option_type;
    //选项内容
    public String option_content;
    //选项url
    public String option_url;
    //跳转问题
    public String next_question;
    //选项答案
    public String option_answer;
    //选项答案url
    public String option_answer_url;
    //问卷选项id
    public String surveys_option_id;
    //问卷章节id
    public String surveys_chapter_id;

    public String question_option_id;

    public String option_no;
    //下一chapter的id
    public String next_chapter_id;
    //下一chapter序号
    public String next_chapter_seq;
    //问卷选项顺序
    public int option_seq;
    //下一问卷
    public String  option_next_surveys;

}
