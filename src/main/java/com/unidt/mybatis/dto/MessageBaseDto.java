package com.unidt.mybatis.dto;

import com.unidt.mybatis.bean.message.MessageParam;

import java.util.List;

/**
 * 话术保存
 */
public class MessageBaseDto {
    //话术id
    public String message_id;
    //话术编号
    public String message_no;
    //话术类型(文本，图片，含参文本，音频，视频)
    public String message_type;
    //话术内容url
    public String message_url;
    //话术内容
    public String message_content;
    //0草稿，1发布
    public String message_status;
    //混合，1前置，2后置
    public String message_local;
    //删除标识（0未删除，1已删除）
    public String del_flag;
    //创建用户
    public String create_user;
    //创建时间
    public String create_time;
    //更新用户
    public String update_user;
    //更新时间
    public String update_time;
    //备注
    public String message_desc;
    //参数列表
    public List<MessageParam> paramList;

    public String user_id;

    public String token;
}
