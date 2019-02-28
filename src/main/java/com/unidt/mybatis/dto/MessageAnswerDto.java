package com.unidt.mybatis.dto;

public class MessageAnswerDto {
    /**
     * 影响章节id
     */
   public String chapter_answer_id;
    /**
     * 应答话术id
     */
    public String answer_message_id;
    /**
     * 话术编号
     */
    public String message_no;

    /**
     * 话术id
     */
    public String message_id;
    /**
     * 应答话术内容
     */
    public String message_content;
    /**
     * 应答话术位置（1前置话术，2后置话术）
     */
    public int message_local;
    /**
     * 应答话术顺序
     */
    public int message_seq;
    /**
     * 应答话术url
     */
    public String message_url;
    /**
     * 应答话术类型
     */
    public String message_type;


}
