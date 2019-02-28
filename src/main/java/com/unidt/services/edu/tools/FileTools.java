package com.unidt.services.edu.tools;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.unidt.api.LoginController;
import com.unidt.helper.common.UserHelper;
import com.unidt.mybatis.dto.DownLoadDto;
import com.unidt.helper.common.Constants;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.dto.UploadDto;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;


@RestController
@EnableAutoConfiguration
@ComponentScan
public class FileTools {
    private static Logger log = LoggerFactory.getLogger(FileTools.class);

    public COSClient client = null;


    /**
     *  作业上传接口
     * @param formFile
     * @return
     */
    @RequestMapping(value="/fileUpload",method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String workUploadFile(@RequestParam("uploadfile") MultipartFile formFile,@RequestParam("flag") int flag){

        log.info("上传资源:" +formFile);
        try{
            if (!formFile.isEmpty()){
                String pathdir = Constants.UPLOAD_PATH;
                String fileBucket = Constants.UPLOAD_FILE_BUCKET;
                String filepath = Constants.UPLOAD_FILESYS_PATH;//查询时拼全路径
                String res_url = Constants.UPLOAD_RESOURCES_PATH;
                String work_url = Constants.UPLOAD_WORK_PATH;
                String file_url = work_url;
                String teacher_url = Constants.UPLOAD_TEACHER_PATH;

                if(flag ==0){
                    file_url  = res_url;
                }else if(flag ==2){
                    file_url = teacher_url;
                }

                File savedir = new File(pathdir);
                if (!savedir.exists()) {
                    savedir.mkdirs();
                }
                String uuid = GetUUID32.getUUID32();
                File iFile = new File(savedir, uuid + "." + formFile.getOriginalFilename()
                        .substring(Objects.requireNonNull(formFile.getOriginalFilename()).lastIndexOf(".") + 1));

                String resource_url = "/" + uuid + "." + formFile.getOriginalFilename()
                        .substring(formFile.getOriginalFilename().lastIndexOf(".") + 1);

                String local_url = file_url+"/" + uuid + "." + formFile.getOriginalFilename()
                        .substring(formFile.getOriginalFilename().lastIndexOf(".") + 1);

                InputStream fis = formFile.getInputStream();
                FileOutputStream fops = new FileOutputStream(iFile);
                byte buffer[] = new byte[Constants.UPLOAD_BUFFER];
                int len = 0;
                while((len=fis.read(buffer))>0){
                    fops.write(buffer, 0, len);
                }
                fops.close();
                // 对象存储开关
                int type =0;
                if(type==0){
                    OSSUtils ossUtils = new OSSUtils();
                    ossUtils.connectOSS();
                    ossUtils.upload(fileBucket,iFile ,local_url);
                    ossUtils.close();
                }

                Document doc = new Document("file_name", formFile.getOriginalFilename()).append("file_uuid_name", uuid)
                        .append("url", resource_url);

                log.debug("上传文件" + formFile.getOriginalFilename());
                return ReturnResult.createResult(Constants.API_CODE_OK,"ok").append("data", doc).toJson();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR,"error").toJson();
    }
    public static long fileDetails(String url){
        File file = new File(url);
        return file.length();
    }

    /**
     * 文件对象下载
     * @param filepath
     * @return
     */
   @RequestMapping(value="/file_download" ,method = RequestMethod.POST)
   @ResponseBody
   public Object fileDownload(@RequestParam("filepath") String filepath){
       log.info("下载对象存储资源:" +filepath);
       OSSUtils ossUtils = new OSSUtils();
       String fileBucket = Constants.UPLOAD_FILE_BUCKET;
       ossUtils.connectOSS();
      Object res = ossUtils.download(fileBucket, filepath);
       ossUtils.close();
       return res;
   }

    /**
     * 删除文件对象
     * @param filepath
     * @return
     */
    @RequestMapping(value="/delete_file" ,method = RequestMethod.POST)
    @ResponseBody
        public String  deleteFile(@RequestParam("filepath") String filepath) {
        Document doc = new Document();
        OSSUtils ossUtils = new OSSUtils();
        String fileBucket = Constants.UPLOAD_FILE_BUCKET;
        ossUtils.connectOSS();
        ossUtils.deleteFile(fileBucket, filepath);
        ossUtils.close();
        return ReturnResult.createResult(Constants.API_CODE_OK,"OK").append("data",doc ).toJson();
    }

    /**
     * 下载接口
     * @param uploadpath
     * @param response
     */
    @RequestMapping(value="/download/{uploadpath}",method = RequestMethod.GET)
    @ResponseBody
    public void download(@PathVariable String uploadpath, HttpServletResponse response) {
        try {
            File file = new File(Constants.UPLOAD_PATH +"/" + uploadpath);
            String filename = file.getName();

            InputStream fis = new BufferedInputStream(new FileInputStream(Constants.UPLOAD_PATH +"/" + uploadpath));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            byte buffer[] = new byte[Constants.DOWNLOAD_BUFFER];

            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("code", "200");
            response.addHeader("msg", "ok");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");

            int len = 0;
            while((len=fis.read(buffer))>0){
                toClient.write(buffer, 0, len);
            }
            fis.close();

            toClient.flush();
            toClient.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
