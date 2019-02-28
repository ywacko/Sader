package com.unidt.mybatis.bean.surveys;

import lombok.Getter;
import lombok.Setter;

/**
 * 问卷章节
 */
@Getter
@Setter
public class SurveysChapter {
    /**
     * 问卷章节id
     */
    public  String surveys_chapter_id;
    /**
     * 问卷问题执行顺序
     */
    public int surveys_chapter_seq;
    /**
     * 问卷id
     */
    public String surveys_id;
    /**
     * 问卷编号
     */
    public String surverys_no;
    /**
     * 问题id
     */
    public String question_id;
    /**
     * 是否是最后一题目
     */
    public int is_end_chapter = 0;
    
}
