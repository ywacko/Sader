package com.unidt.mybatis.dto;

/**
 * 获取问卷下话术参数
 *  auth：ysc
 *  date：2018-11-23
 */
public class QuestionMessageDto {
    //问题话术id
    public String question_message_id;
    //问题id
    public String question_id;
    //话术id
    public String message_id;
    //话术位置
    public int message_local;
    //话术顺序
    public int message_local_seq;
    //话术编号
    public String message_no;
    //话术类型(文本，图片，含参文本，音频，视频)
    public String message_type;
    //话术内容url
    public String message_url;
    //话术内容
    public String message_content;

}
