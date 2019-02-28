package com.unidt.mybatis.dto;

import java.util.List;

public class SurveysChapterDto {
    //问卷章节id
    public String surveys_chapter_id;

    //问卷章节id
    public String surveys_chapter_no;
    //章节问题id
    public String question_id;

    public String question_no;
    //章节问题类型
    public String question_type;
    //章节内容
    public String question_content;
    //章节问题url--用于存储页面跳转参数
    public String question_url;
    //文件路径
    public String question_path;
    //问题顺序
    public String surveys_chapter_seq;
    //输入时下一问题顺序
    public String next_chapter_seq;
    //下一问题id
    public String surveys_next_chapter;
    //问卷id
    public String surveys_id;
    //问卷编号
    public String surveys_no;
    //问卷类型
    public String surveys_type;
    /**
     * 问卷名称
     */
    public String surveys_name;
    //是否最后章节
    public int is_end_chapter;
    //问卷章节类型（话术，问题）
    public String surveys_chapter_type;
    /**
     * 下一问卷
     */
    public String next_surveys;

    public List<SurveysOptionDto> surveysOptionList;

    public List<String> statusList;
    /**
     * 该条chapter是否为新增
     */
    public Boolean isNew;


}
