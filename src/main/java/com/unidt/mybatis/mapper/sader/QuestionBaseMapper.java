package com.unidt.mybatis.mapper.sader;

import com.unidt.mybatis.bean.question.QuestionInfo;
import com.unidt.mybatis.bean.question.QuestionMessage;
import com.unidt.mybatis.bean.question.QuestionOption;
import com.unidt.mybatis.dto.QuestionInfoDto;
import com.unidt.mybatis.dto.QuestionMessageDto;
import com.unidt.mybatis.dto.QuestionOptionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionBaseMapper {

    /**
     *
     * @return
     */
    @Select("SELECT RIGHT(question_no,LENGTH(question_no)-1) " +
            "FROM tab_question_info " +
            "ORDER BY CONVERT(RIGHT(question_no,LENGTH(question_no)-1), SIGNED) DESC " +
            "LIMIT 1")
    String getMaxQuestionNo();

    /**
     *
     * @param question_id
     * @return
     */
    @Select("SELECT MAX(CONVERT(RIGHT(t.number,LENGTH(t.number)-3), SIGNED)) FROM " +
            "(SELECT substr(option_no,instr(option_no,'_')+1,length(option_no)-instr(option_no,'_')) number " +
            "FROM tab_question_option " +
            "WHERE question_id = #{question_id}) t")
    String getMaxOptionNoById(String question_id);

    /**
     *
     * @param question_id
     * @return
     */
    @Select("SELECT * FROM tab_question_option " +
            "WHERE question_id = #{question_id} " +
            "AND del_flag = '0'")
    List<QuestionOption> getOptionById(String question_id);

    /**
     *
     * @param question_id
     */
    @Delete("DELETE FROM tab_question_option " +
            "WHERE question_id = #{question_id}")
    void delOptionById(String question_id);

    /**
     *
     * @param questionOptionDto
     */
    @Update({"<script>",
            "UPDATE tab_question_option " +
                    "SET del_flag = '1', update_time = #{update_time}, update_user = #{update_user} " +
                    "WHERE question_id = #{question_id}",
            "<when test='statusList != null and statusList.size() > 0'>",
            " AND option_id NOT IN" +
                    "<foreach collection = 'statusList' index = 'index' item = 'item' open = '(' separator = ',' close = ')'> " +
                    "#{item} " +
                    "</foreach>",
            "</when>",
            "</script>"})
    void delOptionByLogic(QuestionOptionDto questionOptionDto);

    /**
     *
     * @param questionOption
     */
    @Update({"<script>",
            "UPDATE tab_question_option " +
                    "SET update_time = #{update_time}, update_user = #{update_user}",
            "<when test = 'option_type != null'>",
            ", option_type = #{option_type}",
            "</when>",
            "<when test = 'option_content != null'>",
            ", option_content = #{option_content}",
            "</when>",
            "<when test = 'option_url != null'>",
            ", option_url = #{option_url}",
            "</when>",
            "<when test = 'option_seq != null'>",
            ", option_seq = #{option_seq}",
            "</when>",
            " WHERE option_id = #{option_id}",
            "</script>"})
    void editOptionById(QuestionOptionDto questionOptionDto);
    /**
     *
     * @param questionOptionDto
     */
    @Insert("INSERT INTO tab_question_option " +
            "(option_id, option_no, question_id, option_type, option_content, " +
            "option_url, option_seq, create_time, create_user) " +
            "VALUES (#{option_id}, #{option_no}, #{question_id}, #{option_type}, #{option_content}, " +
            "#{option_url}, #{option_seq}, #{create_time}, #{create_user})")
    void saveOptionInfo(QuestionOptionDto questionOptionDto);

    /**
     *
     * @param question_id
     * @return
     */
    @Select("SELECT q.message_local, q.message_local_seq, q.question_message_id, m.message_content, m.message_id " +
            "FROM tab_question_message q INNER JOIN tab_message_info m " +
            "ON q.message_id = m.message_id " +
            "WHERE q.question_id = #{question_id}")
    List<QuestionMessageDto> getQmListById(String question_id);

    /**
     *
     * @param question_id
     */
    @Delete("DELETE FROM tab_question_message " +
            "WHERE question_id = #{question_id}")
    void delQmListById(String question_id);

    /**
     *
     * @param questionMessage
     */
    @Insert("INSERT INTO tab_question_message " +
            "(question_message_id, question_id, message_id, message_local, message_local_seq) " +
            "VALUES (#{question_message_id}, #{question_id}, #{message_id}, " +
            "#{message_local}, #{message_local_seq})")
    void saveQmInfo(QuestionMessage questionMessage);

    /**
     *
     * @param dto
     * @return
     */
    @Select({"<script>",
            "SELECT * FROM tab_question_info " +
                    "WHERE del_flag = '0'",
            "<when test = 'question_no != null'>",
            " and question_no = #{question_no}",
            "</when>",
            "<when test = 'question_content != null'>",
            " and question_content LIKE CONCAT('%',#{question_content},'%')",
            "</when>",
            "<when test = 'question_type != null'>",
            " and question_type = #{question_type}",
            "</when>",
            "</script>"})
    List<QuestionInfo> getQuestionList(QuestionInfoDto dto);

    /**
     *
     * @param question_id
     * @return
     */
    @Select("SELECT * FROM tab_question_info " +
            "WHERE question_id = #{question_id} " +
            "AND del_flag = '0' " +
            "LIMIT 1")
    QuestionInfo getQuestionInfoById(String question_id);

    /**
     *
     * @param dto
     * @return
     */
    @Update({"<script>",
            "UPDATE tab_question_info " +
                    "SET update_time = #{update_time}, update_user = #{user_id}",
            "<when test = 'question_content != null'>",
            ", question_content = #{question_content}",
            "</when>",
            "<when test = 'question_type != null'>",
            ", question_type = #{question_type}",
            "</when>",
            "<when test = 'question_name != null'>",
            ", question_name = #{question_name}",
            "</when>",
            "<when test = 'question_path != null'>",
            ", question_path = #{question_path}",
            "</when>",
            "<when test = 'question_url != null'>",
            ", question_url = #{question_url}",
            "</when>",
            "<when test = 'question_desc != null'>",
            ", question_desc = #{question_desc}",
            "</when>",
            " WHERE question_id = #{question_id}",
            "</script>"})
    Integer editQuestionInfoById(QuestionInfoDto dto);

    /**
     *
     * @param dto
     * @return
     */
    @Insert("INSERT INTO tab_question_info " +
            "(question_id, question_no, question_type, create_time, question_path, " +
            "question_url, question_content, create_user, question_desc, question_name) " +
            "VALUES (#{question_id}, #{question_no}, #{question_type}, #{create_time}, #{question_path}, " +
            "#{question_url}, #{question_content}, #{user_id}, #{question_desc}, #{question_name})")
    Integer saveQuestionInfo(QuestionInfoDto dto);

    /**
     *
     * @param question_id
     * @return
     */
    @Delete("DELETE FROM tab_question_info " +
            "WHERE question_id = #{question_id}")
    Integer delQuestionInfoById(String question_id);

    /**
     *
     * @param dto
     * @return
     */
    @Update("UPDATE tab_question_info " +
            "SET del_flag = '1', update_time = #{update_time}, update_user = #{user_id} " +
            "WHERE question_id = #{question_id}")
    Integer delQuestionInfoByLogic(QuestionInfoDto dto);
}
