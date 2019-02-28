package com.unidt.mybatis.mapper.sader;

import com.unidt.mybatis.bean.question.QuestionInfo;
import com.unidt.mybatis.bean.question.QuestionMessage;
import com.unidt.mybatis.bean.response.ChapterAnswer;
import com.unidt.mybatis.bean.response.MessageAnswer;
import com.unidt.mybatis.bean.response.OptionAnswer;
import com.unidt.mybatis.bean.response.ResponseInfo;
import com.unidt.mybatis.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResponseMapper {
    @Select("select r.* from tab_response_info r where r.response_id=#{response_id}")
    public ResponseInfo  getResponsesById(String response_id);

    @Select("select r.response_id,o.surveys_name,o.surveys_id,o.surveys_no,o.surveys_type,r.user_id,r.is_end,r.start_time,r.end_time from " +
            "tab_response_info r LEFT JOIN tab_surveys_info o on o.surveys_id= r.surveys_id where o.del_flag='0' and r.surveys_id =#{surveys_id}" +
            "and r.user_id = #{user_id} order by r.create_time  desc limit 1")
    public ResponseSurveysDto getResponsesByInfo(ResponseSurveysDto info);

    @Select("select count(id) from TBL_USER where id=#{user_id} ")
    public int checkUserId(String user_id);


    @Select("select r.response_id,o.surveys_id,o.surveys_no,o.surveys_name,o.surveys_type,r.user_id,r.is_end,r.start_time,r.end_time from " +
            "tab_response_info r  LEFT JOIN tab_surveys_info o on o.surveys_id= r.surveys_id where o.del_flag='0' and r.user_id = #{user_id} " +
            "order by r.create_time desc limit 1")
    public ResponseSurveysDto getResponsesByUser(ResponseSurveysDto info);

    @Select("select r.response_id,o.surveys_id,o.surveys_no,o.surveys_name,o.surveys_type,r.user_id,r.is_end,r.start_time,r.end_time from " +
            "tab_response_info r LEFT JOIN tab_surveys_info o on o.surveys_id= r.surveys_id where o.del_flag='0' and r.user_id = #{user_id} order by r.create_time desc limit 10")
    public List<ResponseSurveysDto> getResponseListByUser(ResponseSurveysDto info);

    @Insert("INSERT INTO tab_response_info(response_id,surveys_id,surveys_no,user_id,start_time,end_time,create_time)" +
            "VALUES (#{response_id},#{surveys_id},#{surveys_no},#{user_id},#{start_time},#{end_time},#{create_time})")
    public void  setResponseInfo(ResponseInfo info);

    @Update("UPDATE tab_response_info set is_end=#{is_end}," +
            "end_time=#{end_time},update_time=#{update_time} " +
            " where response_id=#{response_id}")
    public void  updateResponseInfo(ResponseInfo info);

    @Select(" SELECT " +
            "c.chapter_answer_id," +
            "q.question_no," +
            "q.question_type," +
            "c.surveys_chapter_id," +
            "  q.question_content," +
            "q.question_url," +
            "q.question_path," +
            "  s.surveys_chapter_seq," +
            "s.surveys_next_chapter,"+
            "c.chapter_type" +
            " FROM " +
            "tab_chapter_answer c" +
            " LEFT JOIN tab_surveys_chapter s ON s.surveys_chapter_id = c.surveys_chapter_id " +
            " LEFT JOIN tab_question_info q ON q.question_id = s.question_id " +
            " where c.response_id = #{response_id} order by s.surveys_chapter_seq")
    public List<ChapterAnswerDto> getChapterAnswerDtos(String response_id);

    @Select("select t.chapter_answer_id," +
            "t.response_id," +
            "t.surveys_chapter_id," +
            "t.end_time," +
            "t.start_time," +
            "t.chapter_type from tab_chapter_answer t where t.chapter_answer_id =#{chapter_answer_id}")
    public ChapterAnswer getChapterAnswer(String chapter_answer_id);

    @Select("select t.chapter_answer_id," +
            "t.response_id," +
            "t.surveys_chapter_id," +
            "t.end_time," +
            "t.start_time," +
            "t.chapter_type from tab_chapter_answer t where t.chapter_answer_id =#{chapter_answer_id} " +
            "and t.response_id=#{response_id}")
    public ChapterAnswer getChapterAnswerByInfo(ChapterAnswerDto info);




    @Delete("delete from tab_option_answer where chapter_answer_id =#{chapter_answer_id}")
    public void delOptionAnswers(String chapter_answer_id);

    @Insert("insert into tab_option_answer (option_answer_id,chapter_answer_id,option_id,option_answer,option_answer_url,option_answer_seq,end_time,start_time) values(" +
            " #{option_answer_id},#{chapter_answer_id},#{option_id},#{option_answer},#{option_answer_url},#{option_answer_seq},#{end_time},#{start_time})")
    public void setOptionAnswer(OptionAnswer info);

    @Delete("delete from tab_message_answer where chapter_answer_id =#{chapter_answer_id}")
    public void delMessageAnswers(String chapter_answer_id);

    @Insert("INSERT INTO tab_message_answer (answer_message_id,chapter_answer_id,message_no,message_id,message_url,message_type,message_content,message_local,message_seq)" +
            "VALUES(#{answer_message_id},#{chapter_answer_id},#{message_no},#{message_id},#{message_url},#{message_type},#{message_content},#{message_local},#{message_seq})")
    public void setMessageAnswer(MessageAnswer messageAnswer);

    @Select("select " +
            "m.answer_message_id," +
            "m.chapter_answer_id," +
            "m.message_no," +
            "m.message_id," +
            "m.message_url," +
            "m.message_type," +
            "m.message_content," +
            "m.message_local," +
            "m.message_seq from tab_message_answer m  where m.chapter_answer_id=#{chapter_answer_id} and m.message_local =#{message_local}")
    public List<MessageAnswerDto> getMessageAnswerListById(MessageAnswerDto mesAnsDto);

    @Select("SELECT" +
            " o.option_answer_id," +
            " o.option_id,"+
            " o.chapter_answer_id," +
            " o.option_answer," +
            " o.option_answer_url," +
            " o.option_answer_seq," +
            " o.start_time," +
            " o.end_time " +
            " FROM " +
            " tab_option_answer o " +
            " where o.chapter_answer_id = #{chapter_answer_id}")
    public List<OptionAnswerDto> getOptionAnswers(String chapter_answer_id);

    @Select(" SELECT " +
            " o.option_seq" +
            " FROM " +
            " tab_option_answer c " +
            " LEFT JOIN tab_chapter_answer a ON c.chapter_answer_id = a.chapter_answer_id " +
            " LEFT JOIN tab_question_option o ON o.option_id = c.option_id  " +
            " WHERE " +
            " a.surveys_chapter_id = #{surveys_chapter_id} " +
            " AND a.response_id = #{response_id} limit 1")
    public Integer getOptionResponse(@Param("surveys_chapter_id") String surveys_chapter_id,@Param("response_id") String response_id);

    @Insert("insert into tab_chapter_answer(chapter_answer_id,response_id,surveys_chapter_id,end_time,start_time,chapter_type)" +
            "values(#{chapter_answer_id},#{response_id},#{surveys_chapter_id},#{end_time},#{start_time},#{chapter_type})")
    public void insertChapter(ChapterAnswer chapterAnswer);

    @Update("update tab_question_info set chapter_name = #{chapter_name}, chapter_title = #{chapter_title}," +
            "chapter_content = #{chapter_content}, chapter_level = #{chapter_level}, course_id = #{course_id}," +
            "chapter_parentid = #{chapter_parentid}, del_flag = #{del_flag}, create_time = #{create_time}, update_time = #{update_time} where chapter_id = #{chapter_id}")
    public void updateChapter(QuestionInfo info);

    @Delete("delete from tab_question_info where chapter_id=#{id}")
    public void deleteChapter(String id);

}
