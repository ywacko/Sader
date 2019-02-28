package com.unidt.mybatis.mapper.sader;

import com.unidt.mybatis.bean.surveys.SurveysInfo;
import com.unidt.mybatis.dto.SurveysBaseDto;
import com.unidt.mybatis.dto.SurveysChapterDto;
import com.unidt.mybatis.dto.SurveysOptionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SurveysBaseMapper {

    /**
     *
     * @param surveys_chapter_id
     * @return
     */
    @Select("SELECT s.surveys_option_id, q.option_no, q.option_content, q.option_type, " +
            "q.option_seq, s.next_chapter_id, s.option_next_surveys, q.option_id " +
            "FROM tab_surveys_option s INNER JOIN tab_question_option q " +
            "ON s.question_option_id = q.option_id " +
            "WHERE s.surveys_chapter_id = #{surveys_chapter_id}")
    List<SurveysOptionDto> getSurvOptionList(String surveys_chapter_id);

    /**
     *
     * @param sOD
     */
    @Insert("INSERT INTO tab_surveys_option " +
            "(surveys_option_id, surveys_chapter_id, question_option_id, " +
            "next_chapter_id, option_next_surveys) " +
            "VALUES (#{surveys_option_id}, #{surveys_chapter_id}, #{question_option_id}, " +
            "#{next_chapter_id}, #{option_next_surveys})")
    void saveSurvOption(SurveysOptionDto sOD);

    /**
     *
     * @param surveys_chapter_id
     */
    @Delete("DELETE FROM tab_surveys_option " +
            "WHERE surveys_chapter_id = #{surveys_chapter_id}")
    void delSurvOption(String surveys_chapter_id);

    /**
     *
     * @param surveys_id
     * @return
     */
    @Select("SELECT s.surveys_chapter_id, q.question_no, q.question_content, q.question_id, " +
            "q.question_type, s.surveys_chapter_seq, s.next_surveys, s.surveys_next_chapter " +
            "FROM tab_surveys_chapter s INNER JOIN tab_question_info q " +
            "ON s.question_id = q.question_id " +
            "WHERE s.surveys_id = #{surveys_id}")
    List<SurveysChapterDto> getSurvChapterList(String surveys_id);

    /**
     *
     * @param sCD
     */
    @Insert("INSERT INTO tab_surveys_chapter " +
            "(surveys_chapter_id, question_id, surveys_chapter_type, surveys_chapter_seq, " +
            "surveys_chapter_no, surveys_id, surveys_no, surveys_next_chapter, is_end_chapter) " +
            "VALUES (#{surveys_chapter_id}, #{question_id}, #{surveys_chapter_type}, #{surveys_chapter_seq}, " +
            "#{surveys_chapter_no}, #{surveys_id}, #{surveys_no}, #{surveys_next_chapter}, " +
            "#{is_end_chapter})")
    void saveSurvChapter(SurveysChapterDto sCD);

    /**
     *
     * @param sCD
     */
    @Update({"<script>",
             "UPDATE tab_surveys_chapter " +
                     "SET surveys_chapter_no = #{surveys_chapter_no}, surveys_chapter_seq = #{surveys_chapter_seq}, " +
                     "surveys_next_chapter = #{surveys_next_chapter}, is_end_chapter = #{is_end_chapter}",
            "<when test = 'surveys_chapter_type != null'>",
            ", surveys_chapter_type = #{surveys_chapter_type}",
            "</when>",
            "<when test = 'question_id != null'>",
            ", question_id = #{question_id}",
            "</when>",
            " WHERE surveys_chapter_id = #{surveys_chapter_id}",
            "</script>"})
    void editSurvChapter(SurveysChapterDto sCD);

    /**
     *
     * @param surveysChapterDto
     */
    @Delete({"<script>",
            "DELETE FROM tab_surveys_chapter " +
                    "WHERE surveys_id = #{surveys_id}",
            "<when test='statusList != null and statusList.size() > 0'>",
            " AND surveys_chapter_id NOT IN" +
                    "<foreach collection = 'statusList' index = 'index' item = 'item' open = '(' separator = ',' close = ')'> " +
                    "#{item} " +
                    "</foreach>",
            "</when>",
            "</script>"})
    void delSurvChapterById(SurveysChapterDto surveysChapterDto);

    /**
     *
     * @param surveysChapterDto
     */
    @Update({"<script>",
            "UPDATE tab_surveys_chapter " +
                    "SET del_flag = '1' " +
                    "WHERE surveys_id = #{surveys_id}",
            "<when test='statusList != null and statusList.size() > 0'>",
            " AND surveys_chapter_id NOT IN" +
                    "<foreach collection = 'statusList' index = 'index' item = 'item' open = '(' separator = ',' close = ')'> " +
                    "#{item} " +
                    "</foreach>",
            "</when>",
            "</script>"})
    void delSurvChapterByLogic(SurveysChapterDto surveysChapterDto);

    /**
     *
     * @return
     */
    @Select("SELECT RIGHT(surveys_no,LENGTH(surveys_no)-1) " +
            "FROM tab_surveys_info " +
            "ORDER BY CONVERT(RIGHT(surveys_no,LENGTH(surveys_no)-1), SIGNED) DESC " +
            "LIMIT 1")
    String getMaxSurvNo();

    /**
     *
     * @param dto
     * @return
     */
    @Select({"<script>",
             "SELECT * FROM tab_surveys_info " +
                     "WHERE del_flag = '0'",
            "<when test = 'surveys_no != null'>",
            " and surveys_no = #{surveys_no}",
            "</when>",
            "<when test = 'surveys_name != null'>",
            " and surveys_name LIKE CONCAT('%',#{surveys_name},'%')",
            "</when>",
            "<when test = 'surveys_desc != null'>",
            " and surveys_desc LIKE CONCAT('%',#{surveys_desc},'%')",
            "</when>",
            "</script>"})
    List<SurveysInfo> getSurvBaseList(SurveysBaseDto dto);

    /**
     *
     * @param surveys_id
     * @return
     */
    @Select("SELECT * FROM tab_surveys_info " +
            "WHERE surveys_id = #{surveys_id} " +
            "AND del_flag = '0' " +
            "LIMIT 1")
    SurveysInfo getSurvBaseInfo(String surveys_id);

    /**
     *
     * @param dto
     * @return
     */
    @Update({"<script>",
             "UPDATE tab_surveys_info " +
                     "SET update_time = #{update_time}, update_user = #{user_id}",
            "<when test = 'surveys_name != null'>",
            ", surveys_name = #{surveys_name}",
            "</when>",
            "<when test = 'surveys_type != null'>",
            ", surveys_type = #{surveys_type}",
            "</when>",
            "<when test = 'surveys_desc != null'>",
            ", surveys_desc = #{surveys_desc}",
            "</when>",
            " WHERE surveys_id = #{surveys_id}",
            "</script>"})
    Integer editSurvBaseInfoById(SurveysBaseDto dto);

    /**
     *
     * @param dto
     * @return
     */
    @Insert("INSERT INTO tab_surveys_info " +
            "(surveys_no, surveys_id, surveys_name, surveys_type, surveys_desc, create_time, create_user) " +
            "VALUES (#{surveys_no}, #{surveys_id}, #{surveys_name}, #{surveys_type}, " +
            "#{surveys_desc}, #{create_time}, #{user_id})")
    Integer saveSurvBaseInfo(SurveysBaseDto dto);

    /**
     *
     * @param surveys_id
     * @return
     */
    @Delete("DELETE FROM tab_surveys_info " +
            "WHERE surveys_id = #{surveys_id}")
    Integer delSurvBaseInfoById(String surveys_id);

    /**
     *
     * @param dto
     * @return
     */
    @Update("UPDATE tab_surveys_info " +
            "SET del_flag = '1', update_time = #{update_time}, update_user = #{user_id} " +
            "WHERE surveys_id = #{surveys_id}")
    Integer delSurvBaseInfoByLogic(SurveysBaseDto dto);

}
