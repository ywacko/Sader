package com.unidt.mybatis.dto;

import com.unidt.mybatis.bean.question.QuestionMessage;
import com.unidt.mybatis.bean.question.QuestionOption;

import java.util.List;

/**
 * 问题选项
 */

public class QuestionInfoDto {
    public String question_id;
    //'问题编号'
    public String question_no;
    //'问题名称'
    public String question_name;
    //'(单选，多选，图片，混合，文本，音频，图文，填空)'
    public String question_type;
    //'问题内容'
    public String question_content;
    //'问题url(题目需要图片或视频url)'
    public String question_url;

    public String user_id;

    public String question_path;
    //'备注'
    public String question_desc;
    //'问题状态（0草稿1发布）'
    public String question_status;

    public String create_user;
    //'创建时间'
    public String create_time;
    //最后修改人
    public String update_user;
    //'修改时间'
    public String update_time;
    //选项list
    public List<QuestionOptionDto> optionList;
    //后置话术
    public List<QuestionMessage> backMessages;
    //前置话术
    public List<QuestionMessage> frontMessages;
}
