package com.unidt.mybatis.mapper.sader;

import com.unidt.mybatis.bean.response.ResponseInfo;
import com.unidt.mybatis.bean.surveys.SurveysChapter;
import com.unidt.mybatis.bean.surveys.SurveysInfo;
import com.unidt.mybatis.dto.ChapterAnswerDto;
import com.unidt.mybatis.dto.SurveysChapterDto;
import com.unidt.mybatis.dto.SurveysOptionDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface SurveysMapper {

    @Select("select s.surveys_id, s.surveys_no,s.surveys_name,s.surveys_status,s.surveys_type,s.surveys_desc from tab_surveys_info s where s.surveys_id = #{surveys_id}")
    public SurveysInfo getSurveysById(String surveys_id);

    @Select("SELECT " +
            "s.surveys_chapter_id," +
            "s.question_id," +
            "q.question_url," +
            "q.question_path," +
            "s.surveys_no," +
            "q.question_type," +
            "q.question_no," +
            "q.question_content," +
            "s.surveys_chapter_type," +
            "s.surveys_chapter_seq," +
            "s.surveys_next_chapter," +
            "s.surveys_chapter_no," +
            "s.next_surveys,"+
            "s.surveys_id," +
            "o.surveys_no," +
            "o.surveys_type," +
            "o.surveys_name," +
            "s.is_end_chapter" +
            " FROM " +
            "tab_surveys_chapter s left join tab_surveys_info o on o.surveys_id = s.surveys_id left join " +
            "tab_question_info q on q.question_id=s.question_id " +
            "WHERE" +
            " s.surveys_chapter_id = #{surveys_chapter_id}")
    public SurveysChapterDto getSurveysChapterById(String surveys_chapter_id);
    @Select("SELECT " +
            "s.surveys_chapter_id," +
            "s.question_id," +
            "q.question_url," +
            "q.question_path," +
            "s.surveys_no," +
            "q.question_type," +
            "q.question_no," +
            "q.question_content," +
            "s.surveys_chapter_type," +
            "s.surveys_chapter_seq," +
            "s.surveys_next_chapter," +
            "s.surveys_chapter_no," +
            "s.next_surveys,"+
            "s.surveys_id," +
            "o.surveys_no," +
            "o.surveys_type," +
            "o.surveys_name," +
            "s.is_end_chapter" +
            " FROM " +
            "tab_surveys_chapter s left join tab_surveys_info o on o.surveys_id = s.surveys_id left join " +
            "tab_question_info q on q.question_id=s.question_id " +
            "WHERE" +
            " s.surveys_chapter_id = #{surveys_chapter_id} and s.response_id=#{response_id}")
    public SurveysChapterDto getSurveysChapterByDto(ChapterAnswerDto dto);
    @Select("select s.surveys_id, s.surveys_no,s.surveys_name,s.surveys_status,s.surveys_type,s.surveys_desc from tab_surveys_info s where s.surveys_no = #{surveys_no}")
    public SurveysInfo getSurveysByNo(String surveys_no);

    @Delete("delete  from tab_response_info  where user_id = #{user_id}")
    public int deleteResonseinfo(String user_id);

    @Delete("delete  from tab_chapter_answer  where response_id  in (select r.response_id from tab_response_info r where r.user_id=#{user_id} )")
    public int deleteChapterinfo(String user_id);


    @Delete("delete  from tab_message_answer  where chapter_answer_id  in (select c.chapter_answer_id from tab_chapter_answer c  where c.response_id  in (select r.response_id from tab_response_info r where r.user_id=#{user_id}))")
    public int deleteMessageinfo(String user_id);


    @Delete("delete from   tab_option_answer  where chapter_answer_id  in (select c.chapter_answer_id from tab_chapter_answer c  where c.response_id  in (select r.response_id from tab_response_info r where r.user_id=#{user_id}))")
    public int deleteOptioninfo(String user_id);

    @Select("select c.surveys_chapter_id,c.surveys_chapter_no," +
            "c.next_surveys," +
            "c.question_id," +
            "q.question_no," +
            "c.surveys_id," +
            "c.surveys_chapter_type," +
            "c.surveys_chapter_seq," +
            "c.surveys_next_chapter," +
            "c.is_end_chapter," +
            "q.question_type,q.question_content,q.question_url,q.question_path " +
            " from tab_surveys_chapter c left join tab_question_info q on q.question_id = c.question_id " +
            "where c.surveys_chapter_id = #{surveys_chapter_id} limit 1")
    public SurveysChapterDto getSurveysChaptersById(String surveys_chapter_id);

    @Select("select surveys_type from tab_surveys_info where surveys_id=#{surveys_id} limit 1")
    public String getSurveysTypeById(String surveys_id);

    //根据问卷id和问题id获取
    @Select("select c.surveys_chapter_id, c.surveys_chapter_no," +
            "c.question_id," +
            "c.surveys_chapter_type," +
            "c.surveys_chapter_seq," +
            "c.surveys_next_chapter," +
            "c.next_surveys,"+
            "c.surveys_id," +
            "c.is_end_chapter," +
            "q.question_type,q.question_content ,q.question_url,q.question_no,q.question_path from tab_question_info q LEFT JOIN tab_surveys_chapter c on c.question_id= q.question_id  and  c.surveys_id= #{surveys_id} and c.surveys_chapter_seq=#{surveys_chapter_seq} limit 1")
    public SurveysChapterDto getSurveysFirstChaptersById(@Param("surveys_id") String surveys_id, @Param("surveys_chapter_seq") int surveys_chapter_seq);


    /**
     * 根据问卷章节获取章节下所有选项
     * @param surveys_chapter_id
     * @return
     */
    @Select("SELECT " +
            "t.option_id," +
            "t.option_no," +
            "t.option_type," +
            "t.option_seq," +
            "t.option_content," +
            "t.option_seq," +
            "t.option_url," +
            "'' AS option_answer," +
            "'' AS option_answer_url" +
            " FROM " +
            " tab_question_option t " +
            " left join tab_surveys_chapter s on t.question_id = s.question_id " +
            " WHERE s.surveys_chapter_id =#{surveys_chapter_id}")
    public List<SurveysOptionDto> getSurveysOptionList(String surveys_chapter_id);


    @Select("select "+
            "o.surveys_option_id," +
            "o.surveys_chapter_id," +
            "o.next_chapter_id AS next_question," +
            "o.option_next_surveys from tab_surveys_option o  where o.question_option_id =#{option_id} and o.surveys_chapter_id = #{surveys_chapter_id} limit 1")
    public  SurveysOptionDto  getSurveysOption(@Param("surveys_chapter_id") String surveys_chapter_id, @Param("option_id")  String option_id);


    /**
     *  获取当前用户，最新一次面试官问卷选择第一题，题号
     * @param
     * @param user_id
     * @return
     */
    //and o.create_user =#{user_id}
    @Select("select c.surveys_no from tab_chapter_answer a " +
            "     left join tab_response_info o on o.response_id = a.response_id " +
            " left join tab_surveys_chapter c on c.surveys_chapter_id = a.surveys_chapter_id " +
            "     where o.surveys_no in ('S2','S3','S4') and o.user_id=#{user_id}  order by o.create_time desc LIMIT 1 ")
    public String getSurveysChapter(String user_id);

    /**
     * 根据章节id获取问卷选项
     * @param question_id
     * @return
     */
    @Select("select o.option_id ,o.option_type,o.option_content,s.next_chapter_id from tab_question_option o  LEFT JOIN tab_surveys_option s on s.question_option_id = o.option_id " +
            " and s.surveys_chapter_id = o.question_id and question_id = #{question_id}")
   public  List<SurveysOptionDto> getSurveysChapterList(String question_id);



    @Insert("insert into tab_surveys_info values(#{chapter_id},#{chapter_name},#{chapter_title},#{chapter_content},#{chapter_level},#{course_id}," +
            "#{chapter_parentid}, #{del_flag}, #{create_time},#{update_time})")
    public void insertChapter(SurveysInfo info);

    @Update("update tab_surveys_info set chapter_name = #{chapter_name}, chapter_title = #{chapter_title}," +
            "chapter_content = #{chapter_content}, chapter_level = #{chapter_level}, course_id = #{course_id}," +
            "chapter_parentid = #{chapter_parentid}, del_flag = #{del_flag}, create_time = #{create_time}, update_time = #{update_time} where chapter_id = #{chapter_id}")
    public void updateChapter(SurveysInfo info);

    @Delete("delete from tab_surveys_info where chapter_id=#{id}")
    public void deleteChapter(String id);

}
