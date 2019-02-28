package com.unidt.services.edu.upload;

import com.unidt.api.LoginController;
import com.unidt.helper.common.Constants;
import com.unidt.mybatis.bean.upload.AttachmentFile;
import com.unidt.mybatis.dto.AttachmentFileDto;
import com.unidt.mybatis.dto.UploadDto;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.GetformatData;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.SSO;
import com.unidt.mybatis.mapper.*;
import com.unidt.mybatis.mapper.sader.AttachmentFileMapper;
import com.unidt.services.edu.tools.FileTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService{
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    SSOMapper ssoMapper;
    @Autowired
    AttachmentFileMapper attachmentFileMapper;

    /**
     * 更新文件资源
     * @param uploadDto
     * @return
     */
    public String updateFile(AttachmentFileDto uploadDto){
        AttachmentFile info = new AttachmentFile();
        info.file_id = uploadDto.file_id;
        info.file_desc = uploadDto.file_desc;
        info.file_type = uploadDto.file_type;
        info.chapter_id = uploadDto.chapter_id;
        info.file_url = uploadDto.file_url;
        info.file_status = uploadDto.file_status;
        info.file_local = uploadDto.file_local;
        info.chapter_id = uploadDto.chapter_id;
        info.create_user = uploadDto.create_user;
        info.file_name = uploadDto.file_name;
        info.file_size = String.valueOf(FileTools.fileDetails(uploadDto.file_url));

        info.update_user = uploadDto.user_id;
        System.out.println("update"+GetformatData.getformatData());
        info.update_time = GetformatData.getformatData();

        attachmentFileMapper.updateFileInfo(info);
        log.debug("保存文件" + info.file_name + "成功");
        return ReturnResult.createResult(Constants.API_CODE_OK,"ok").toJson();
    }


}
