package com.unidt.api;

import com.unidt.helper.common.Constants;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.upload.AttachmentFile;
import com.unidt.mybatis.dto.AttachmentFileDto;
import com.unidt.mybatis.dto.UploadDto;
import com.unidt.services.edu.tools.OSSUtils;
import com.unidt.services.edu.tools.SSOService;
import com.unidt.services.edu.upload.UploadService;
import com.unidt.services.sader.upload.AttachmentFileService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class UploadFileController {
    private static Logger log = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    AttachmentFileService fileService;


    /**
     * 保存文件
     * @param uploadDto
     * @return
     */
    @RequestMapping(value="/sader/upload/saveFile", method = RequestMethod.POST)
    @ResponseBody
    public String saveUploadFile(@RequestBody AttachmentFileDto uploadDto){
        log.info("保存文件"+ "用户ID："+ uploadDto.user_id);
        return fileService.updateFile(uploadDto);
    }

    /**
     * 上传接口
     * @return url
     */
    @RequestMapping(value="/sader/upload/upFile",method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String uploadfile(@RequestParam("uploadfile") MultipartFile formFile){

        log.info("上传文件"+formFile);
        return fileService.uploadfile(formFile);
    }

}


