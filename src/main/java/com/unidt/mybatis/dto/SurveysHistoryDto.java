package com.unidt.mybatis.dto;

import com.unidt.mybatis.bean.response.ChapterAnswer;

import java.util.List;

/**
 * 获取问卷历史信息
 */
public class SurveysHistoryDto {
    //应答id
    public String response_id;
    //问卷id
    public String surveys_id;
    //用户id
    public String user_id;
    //问卷内容描述
    public String surveys_content;
    //问卷应答类型
    public String surveys_type;
    //问卷应答章节
    public List<ChapterAnswerDto> chapterAnswerList;

}
