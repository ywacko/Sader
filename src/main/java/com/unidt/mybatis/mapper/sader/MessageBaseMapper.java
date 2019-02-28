package com.unidt.mybatis.mapper.sader;

import com.unidt.mybatis.bean.message.MessageInfo;
import com.unidt.mybatis.bean.message.MessageParam;
import com.unidt.mybatis.dto.MessageBaseDto;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * 话术管理
 */
@Mapper
public interface MessageBaseMapper {

    /**
     * 获取当前最大话术编号
     * @return
     */
    @Select("SELECT RIGHT(message_no,LENGTH(message_no)-1) " +
            "FROM tab_message_info " +
            "ORDER BY CONVERT(RIGHT(message_no,LENGTH(message_no)-1), SIGNED) DESC " +
            "LIMIT 1")
    String getMaxMessageNo();

    /**
     *
     * @param message_id
     * @return
     */
    @Select("SELECT * FROM tab_message_param " +
            "WHERE message_id = #{message_id}")
    List<MessageParam> getParamById(String message_id);

    /**
     *
     * @param message_id
     */
    @Delete("DELETE FROM tab_message_param " +
            "WHERE message_id = #{message_id}")
    void delParamById(String message_id);

    /**
     *
     * @param param
     */
    @Insert("INSERT INTO tab_message_param " +
            "(param_id, param_no, param_type, message_id, param_name, question_no) " +
            "VALUES (#{param_id}, #{param_no}, #{param_type}, #{message_id}, " +
            "#{param_name}, #{question_no})")
    void saveParamById(MessageParam param);

    /**
     * 查table
     * @param dto
     * @return
     */
    @Select({"<script>",
            "SELECT * FROM tab_message_info " +
                    "WHERE del_flag = '0'",
            "<when test = 'message_no != null'>",
            " AND message_no = #{message_no}",
            "</when>",
            "<when test = 'message_content != null'>",
            " AND message_content LIKE CONCAT('%',#{message_content},'%')",
            "</when>",
            "<when test = 'message_type != null'>",
            " AND message_type = #{message_type}",
            "</when>",
            "</script>"})
    List<MessageInfo> getMessageList(MessageBaseDto dto);

    /**
     * 查
     * @param message_id
     * @return
     */
    @Select("SELECT * FROM tab_message_info " +
            "WHERE message_id = #{message_id} " +
            "AND del_flag = '0' " +
            "LIMIT 1")
    MessageInfo getMessageInfoById(String message_id);

    /**
     * 改
     * @return
     */
    @Update({"<script>",
            "UPDATE tab_message_info " +
                    "SET update_time = #{update_time}, update_user = #{user_id}",
            "<when test = 'message_content != null'>",
            ", message_content = #{message_content}",
            "</when>",
            "<when test = 'message_type != null'>",
            ", message_type = #{message_type}",
            "</when>",
            "<when test = 'message_url != null'>",
            ", message_url = #{message_url}",
            "</when>",
            "<when test = 'message_desc != null'>",
            ", message_desc = #{message_desc}",
            "</when>",
            " WHERE message_id = #{message_id}",
            "</script>"})
    Integer editMesBaseById(MessageBaseDto dto);

    /**
     * 增
     * @param dto
     * @return
     */
    @Insert("INSERT INTO tab_message_info " +
            "(message_id, message_no, message_type, create_time, " +
            "message_url, message_content, create_user, message_desc) " +
            "VALUES (#{message_id}, #{message_no}, #{message_type}, #{create_time}, " +
            "#{message_url}, #{message_content}, #{user_id}, #{message_desc})")
    Integer saveMesBaseInfo(MessageBaseDto dto);

    /**
     * 物理删
     * @param message_id
     * @return
     */
    @Delete("DELETE FROM tab_message_info " +
            "WHERE message_id = #{message_id}")
    Integer delMesBaseById(String message_id);

    /**
     * 逻辑删
     * @param dto
     * @return
     */
    @Update("UPDATE tab_message_info " +
            "SET del_flag = '1', update_time = #{update_time}, update_user = #{user_id} " +
            "WHERE message_id = #{message_id}")
    Integer delMesBaseByLogic(MessageBaseDto dto);

}
