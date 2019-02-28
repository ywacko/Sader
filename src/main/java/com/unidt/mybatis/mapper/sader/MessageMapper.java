package com.unidt.mybatis.mapper.sader;


import com.unidt.mybatis.bean.question.QuestionMessage;
import com.unidt.mybatis.dto.QuestionMessageDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 话术消息
 */
@Mapper
public interface MessageMapper {
    /*@Select("select * from tab_message_info where class_id = #{id}")
    public MessageInfo selectClassByID(String id);

    @Insert("insert into tab_message_info values(#{question_message_id},#{question_id},#{grade_name},#{class_name},#{class_desc},#{class_status}," +
            "#{school_id}, #{del_flag})")
    public void insertClass(MessageInfo info);*/

    /*@Select("select s.question_message_id,s.message_id,s.message_local,s.message_local_seq,s.question_id,s.question_message_id,m.message_no,m.message_type,m.message_url,m.message_content from tab_question_message s " +
            "LEFT JOIN tab_message_info m on m.message_id = s.message_id where s.question_id=#{question_id} and s.message_local=#{message_local}")
    public List<QuestionMessageDto> getQuestionMessageList(QuestionMessage message);*/

    @Select("select s.question_message_id,s.message_id,s.message_local,s.message_local_seq,s.question_id,s.question_message_id,m.message_no,m.message_type,m.message_url,m.message_content from tab_question_message s " +
            " LEFT JOIN tab_message_info m on m.message_id = s.message_id where s.question_id = #{question_id} and s.message_local = #{message_local}")
    public List<QuestionMessageDto> getQuestionMessageList(QuestionMessage message);
}
