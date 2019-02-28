package com.unidt.mybatis.dto;

import java.util.List;

public class SurveysQuestionDto {
    //问卷编号
    private String surveys_no;
    //问卷id
    private String survey_id;
    //token
    private String token;
    //问卷名称
    private String surveys_name;
    //问卷历史选项
    private List<QuestionInfoDto> hitList;

}
