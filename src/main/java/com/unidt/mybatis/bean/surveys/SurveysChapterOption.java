package com.unidt.mybatis.bean.surveys;

import lombok.Getter;
import lombok.Setter;

/**
 * 问卷章节选项
 */
@Getter
@Setter
public class SurveysChapterOption {

    //问卷问题主键
    public String surveys_option_id;
    //问卷问题id
    public String  surveys_chapter_id;
    //问题选项
    public String  question_option_id;
    //跳转下一问题
    public String  next_question_id;

   }
