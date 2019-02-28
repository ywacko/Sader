package com.unidt.mybatis.bean.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionInfo {

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

    public String question_path;
    //'备注'
    public String question_desc;
    //'问题状态（0草稿1发布）'
    public String question_status;
    //'删除标识0未删除，1已删除'
    public String del_flag;
    //创建人
    public String create_user;
    //'创建时间'
    public String create_time;
    //最后修改人
    public String update_user;
    //'修改时间'
    public String update_time;

    }
