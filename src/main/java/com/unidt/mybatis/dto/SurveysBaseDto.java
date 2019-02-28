package com.unidt.mybatis.dto;

import java.util.List;

public class SurveysBaseDto {

    public String user_id;

    public String surveys_id;

    public String surveys_no;

    public String surveys_name;

    public String surveys_status;

    public String surveys_type;

    public String surveys_desc;

    public String del_flag;

    public String create_user;

    public String update_user;

    public String create_time;

    public String update_time;

    public List<SurveysChapterDto> chapterList;

}
