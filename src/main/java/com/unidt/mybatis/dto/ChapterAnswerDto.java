package com.unidt.mybatis.dto;

import java.util.List;

public class ChapterAnswerDto {
    /**
     * 应答章节id
     */
    public String chapter_answer_id;
    /**
     * 问卷id
     */
    public String surveys_id;
    /**
     * 问卷章节编号
     */
    public String surveys_chapter_id;
    /**
     * 问题id
     */
    public String question_id;
    /**
     * 章节id
     */
    public String chapter_id;
    /**
     * 问题内容
     */
    public String question_content;
    /**
     * 问题链接
     */
    public String question_url;

    /**
     * 问题对应的文件链接
     */
    public String question_path;
    /**
     * 问题类型
     */
    public String question_type;
    /**
     * 问卷章节顺序
     */
    public int surveys_chapter_seq;
    /**
     * 是否最后一个
     */
    public int is_end_chapter;
    /**
     * 应答id
     */
    public String response_id;
    /**
     * 应答章节类型
     */
    public String  chapter_type;
    /**
     * 章节开始时间
     */
    public String start_time;
    /**
     * 章节结束时间
     */
    public String end_time;
    /**
     * 问卷下一章节
     */
    public String surveys_next_chapter;
    /**
     * 选项答案列表
     */
    public List<OptionAnswerDto> optionAnswerList;
    /**
     * 前置话术列表
     */
    public List<MessageAnswerDto>  frontMessages;
    /**
     * 后置话术列表
     */
    public List<MessageAnswerDto>  backMessages;
    /**
     * 用户token
     */
    public String token;

    public String user_id;

}
