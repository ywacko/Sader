package com.unidt.mybatis.mapper.sader;

import com.unidt.mybatis.bean.upload.AttachmentFile;
import com.unidt.mybatis.dto.AttachmentFileDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface AttachmentFileMapper {
    @Select("select * from tab_attachment_file where file_id = #{id}")
    public AttachmentFile selectcFileByID(String id);

    @Insert("insert into tab_attachment_file(file_id,file_name,file_url,file_type,file_size,file_status,create_user,create_time) values" +
            "(#{file_id},#{file_name},#{file_url},#{file_type},#{file_size},#{file_status},#{create_user},#{create_time})")
    public void insertFileInfo(AttachmentFile info);


    @Update("update  tab_attachment_file set file_desc=#{file_desc},file_size=#{file_size},file_status=#{file_status},file_local=#{file_local}," +
            "chapter_id=#{chapter_id},create_user=#{create_user},update_user=#{update_user}," +
            "update_time=#{update_time} where file_id=#{file_id}")
    public void updateFileInfo(AttachmentFile info);


    @Delete("delete from tab_attachment_file where file_id = #{id}")
    public List<AttachmentFileDto> getQuestionMessageList(AttachmentFile file);

}
