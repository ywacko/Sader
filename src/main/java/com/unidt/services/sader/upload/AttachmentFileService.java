package com.unidt.services.sader.upload;

import com.unidt.api.LoginController;
import com.unidt.api.UploadFileController;
import com.unidt.helper.common.Constants;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.GetformatData;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.upload.AttachmentFile;
import com.unidt.mybatis.dto.AttachmentFileDto;
import com.unidt.mybatis.mapper.sader.AttachmentFileMapper;
import com.unidt.services.edu.tools.FileTools;
import com.unidt.services.edu.tools.OSSUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

@Service
public class AttachmentFileService {

    private static Logger log = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    AttachmentFileMapper attachmentFileMapper;

    /**
     * 更新文件资源
     * @param uploadDto
     * @return
     */
    public String saveFile(AttachmentFileDto uploadDto){
        AttachmentFile info = new AttachmentFile();
        log.debug("保存文件" + info.file_name + "开始");
       if(uploadDto==null || StringUtils.isEmpty(uploadDto.file_url)){
           return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN, "参数错误").toJson();
       }
        info.file_id = uploadDto.file_id;
        info.file_desc = uploadDto.file_desc;
        info.file_type = uploadDto.file_type;
        info.chapter_id = uploadDto.chapter_id;
        info.file_url = uploadDto.file_url;
        info.file_status = uploadDto.file_status;
        info.file_local = uploadDto.file_local;
        info.chapter_id = uploadDto.chapter_id;
        info.file_name = uploadDto.file_name;
        info.file_size = String.valueOf(FileTools.fileDetails(uploadDto.file_url));
        info.create_time = GetformatData.getformatData();
        attachmentFileMapper.insertFileInfo(info);
        log.debug("保存文件" + info.file_name + "成功");
        return ReturnResult.createResult(Constants.API_CODE_OK,"ok").toJson();
    }

    /**
     * 更新文件
     * @param uploadDto
     * @return
     */
    public String updateFile(AttachmentFileDto uploadDto){
        AttachmentFile info = new AttachmentFile();
        log.debug("保存文件" + info.file_name + "开始");
        if(uploadDto==null || StringUtils.isEmpty(uploadDto.file_id)){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN, "参数错误").toJson();
        }
        info.file_id = uploadDto.file_id;
        info.file_desc = uploadDto.file_desc;
        info.file_status = "1";
        info.file_local = uploadDto.file_local;
        info.chapter_id = uploadDto.chapter_id;
        info.create_user = uploadDto.user_id;
        info.create_user = uploadDto.user_id;
        info.update_time = GetformatData.getformatData();
        attachmentFileMapper.updateFileInfo(info);
        log.debug("保存文件" + info.file_name + "成功");
        return ReturnResult.createResult(Constants.API_CODE_OK,"ok").toJson();
    }

    /**
     *   上传附件
     * @param formFile
     * @return
     */
   public String uploadfile(MultipartFile formFile){
       int flag  = 0;
       log.info("上传资源:" +formFile);

        if (formFile==null){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN, "参数错误").toJson();
        }
       try{
           String uuid = GetUUID32.getUUID32();
          /* String filepath = "http://shekong-1255423687.file.myqcloud.com";
           String pathdir = "/sader_file";
           String pathurl = "./sader_file";
           String fileBucket = "shekong-1255423687";*/
           String filepath = Constants.UPLOAD_FILESYS_PATH;
           String pathdir = Constants.UPLOAD_RESOURCES_PATH;
           String pathurl = Constants.UPLOAD_PATH;
           String fileBucket =Constants.UPLOAD_FILE_BUCKET;
           // String iFile =formFile.getOriginalFilename();
           if (!formFile.isEmpty()){
               File savedir = new File(pathurl);
               if (!savedir.exists()) {
                   savedir.mkdirs();
               }
               File iFile = new File(savedir, uuid + "." + formFile.getOriginalFilename()
                       .substring(Objects.requireNonNull(formFile.getOriginalFilename()).lastIndexOf(".") + 1));
               String local_url = pathdir+"/" + uuid  + "." + formFile.getOriginalFilename()
                       .substring(formFile.getOriginalFilename().lastIndexOf(".") + 1);

               InputStream fis = formFile.getInputStream();
               FileOutputStream fops = new FileOutputStream(iFile);
               byte buffer[] = new byte[Constants.UPLOAD_BUFFER];
               int len = 0;
               while((len=fis.read(buffer))>0){
                   fops.write(buffer, 0, len);
               }
               OSSUtils ossUtils = new OSSUtils();
               ossUtils.connectOSS();
               ossUtils.upload(fileBucket,iFile ,local_url);
               ossUtils.close();
               AttachmentFileDto info = new AttachmentFileDto();
               info.file_id = uuid;
               info.file_name=formFile.getOriginalFilename();
               info.file_url=filepath+local_url;
               info.file_status = "0";
               info.create_time = GetformatData.getformatData();
               info.file_size = formFile.getSize()+"";
               info.file_type = formFile.getOriginalFilename().substring(formFile.getOriginalFilename().lastIndexOf(".") + 1);
               this.saveFile(info);
               Document doc = new Document("file_name", formFile.getOriginalFilename()).append("file_id", uuid)
                       .append("file_url", filepath+local_url);

               log.debug("上传文件" + formFile.getOriginalFilename());
               return ReturnResult.createResult(Constants.API_CODE_OK,"ok").append("data", doc).toJson();
           }
       }catch (Exception e){
           log.warn("系统错误"+e);
           e.printStackTrace();
       }

       log.warn("上传文件失败");
       return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR,"error").toJson();
   }



}
