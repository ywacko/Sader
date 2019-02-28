package com.unidt.mybatis.bean.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionOption {
    /**
     * 选项id
     */
    public String option_id;
    /**
     * 选项编号
     */
    public String option_no;
    /**
     * 问题编号
     */
    public String question_id;
    /**
     * 选项名称
     */
    public String option_name;
    /**
     * 单选，多选，图片，混合，文本，音频，图文，填空
     */
    public String option_type;
    /**
     * 选项内容
     */
    public String option_content;
    /**
     * 选项url(题目需要图片或视频url)
     */
    public String option_url;
    /**
     * 选项顺序
     */
    public String option_seq;
    /**
     * 创建人
     */
    public String create_user;
    /**
     * 创建时间
     */
    public String create_time;
    /**
     * 更新人
     */
    public String update_user;
    /**
     * 删除标识
     */
    public String del_flag;
    /**
     * 最后更新时间
     */
    public String update_time;

    }
