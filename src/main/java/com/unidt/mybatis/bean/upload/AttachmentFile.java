package com.unidt.mybatis.bean.upload;

import lombok.Getter;
import lombok.Setter;

/**
 * 附件上传
 */
@Getter
@Setter
public class AttachmentFile {
    /**
     *文件id
     */
    public String file_id;
    /**
     *  文件名称
     */
    public String file_name;
    /**
     * 文件说明
     */
    public String file_desc;
    /**
     *  文件类型
     */
    public String file_type;
    /**
     * 文件url
     */
    public String file_url;
    /**
     * 问卷大小
     */
    public String file_size;
    /**
     * 文件状态
     */
    public String file_status;
    /**
     * 文件位置  （问卷，问题，选项）
     */
    public String file_local;
    /**
     * 章节id
     */
    public String chapter_id;
    public String create_user;
    public String create_time;
    public String update_user;
    public String update_time;
    public String del_flag;


}
