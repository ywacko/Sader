package com.unidt.mybatis.mapper.sader;


import com.unidt.mybatis.bean.question.QuestionInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface QuestionMapper {

    @Select("select * from tab_question_info where chapter_id = #{id}")
    public QuestionInfo selectChapterByID(String id);

    @Insert("insert into tab_question_info values(#{chapter_id},#{chapter_name},#{chapter_title},#{chapter_content},#{chapter_level},#{course_id}," +
    "#{chapter_parentid}, #{del_flag}, #{create_time},#{update_time})")
    public void insertChapter(QuestionInfo info);

    @Update("update tab_question_info set chapter_name = #{chapter_name}, chapter_title = #{chapter_title}," +
            "chapter_content = #{chapter_content}, chapter_level = #{chapter_level}, course_id = #{course_id}," +
            "chapter_parentid = #{chapter_parentid}, del_flag = #{del_flag}, create_time = #{create_time}, update_time = #{update_time} where chapter_id = #{chapter_id}")
    public void updateChapter(QuestionInfo info);

    @Delete("delete from tab_question_info where chapter_id=#{id}")
    public void deleteChapter(String id);

}

