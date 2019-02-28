package com.unidt.mybatis.bean.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChapterAnswer {
    //章节应答id
    public String chapter_answer_id;
    //响应id
    public String response_id;
    //问题id(问题或话术)
    public String surveys_chapter_id;
    //结束时间
    public String end_time;
    //开始时间
    public String start_time;
    //
    public String create_time;

    public String chapter_type;

}

