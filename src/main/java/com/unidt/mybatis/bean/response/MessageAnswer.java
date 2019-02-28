package com.unidt.mybatis.bean.response;

/**
 * 应答话术表
 */
public class MessageAnswer {
    //应答话术id
    public String answer_message_id;
    //应答章节id
    public String chapter_answer_id;
    // 话术编号
    public String message_no;
    //话术id
    public String message_id;
    //应答话术url
    public String message_url;
    //应答话术类型
    public String message_type;
    //应答话术内容
    public String message_content;
    //应答话术位置（1前置，2后置）
    public int  message_local;
    //应答话术顺序（序号，从1开始）
    public int message_seq;


}
