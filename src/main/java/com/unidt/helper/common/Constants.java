package com.unidt.helper.common;

import org.springframework.util.StringUtils;

public class Constants {

    //
    // 操作成功
    public static final int FRA_SUCCESS = 0;

    // 记录重复
    public static final int FRA_RECORD_REPEAT = 1;


    //API OK 200
    public static final int API_CODE_OK = 200;
    //
    // 未授权
    public static final int API_CODE_FORBIDDEN = 400;

    // 保存错误
    public static final int API_TOKEN_DEADLINE = 401;
    //
    // 未找到
    public static final int API_CODE_NOT_FOUND = 404;
    //
    // 冲突
    public static final int API_CODE_CORRUPT = 409;
    //
    // 已删除
    public static final int API_CODE_DELETED = 410;
    //
    // 服务器内部错误
    public static final int API_CODE_INNER_ERROR = 500;
    //
    //  上传文件缓冲区大小
    public static final int UPLOAD_BUFFER = 1024 * 8;

    //  下载文件缓冲区大小
    public static final int DOWNLOAD_BUFFER = 1024 * 8;
	
    // 上传服务器文件的路径
    public static final String UPLOAD_PATH = "./sader_file";

    //文件服务器全路径
    public static final String UPLOAD_FILESYS_PATH = "http://shekong-1255423687.file.myqcloud.com";

    //教师资源上传地址
    public static final String UPLOAD_RESOURCES_PATH = "/sader_file";

    //作业上传地址
    public static final String UPLOAD_TEACHER_PATH = "/k12teacher";

    //作业上传地址
    public static final String UPLOAD_WORK_PATH = "/k12work";

    //文件对象存储bucket
    public static final String UPLOAD_FILE_BUCKET ="shekong-1255423687";

    //选择面试官问卷编号
    public static final String SURVEYS_NO ="S2";
    //面试官1
    public static final String SURVEYS_PLAN_NO_1="S2";
    //面试官2
    public static final String SURVEYS_PLAN_NO_2="S3";
    //面试官3
    public static final String SURVEYS_PLAN_NO_3 ="S4";

    //未删除 0 已删除 1
    public static final String  DEL_FLAG_0 = "0";

    public static final String DEL_FLAG_1 = "1";

    //问候问卷
    public static final String  SURVEYS_01 = "01";
    //选择面试官
    public static final String SURVEYS_02 = "02";
    //选择方案
    public static final String SURVEYS_03 = "03";

    //前置话术
    public static final int Message_local_01 = 1;
    //后置话术
    public static final int Message_local_02 = 2;
    //是否最后章节 1 是
    public static  final  int IS_END_CHAPTER_1=1;
    //是否最后章节  0 否
    public static  final  int IS_END_CHAPTER_0=0;
    // 是否结束  1 已结束
    public static  final  int IS_END_SURVEYS_1=1;
    //是否结束  0 未结束
    public static  final  int IS_END_SURVEYS_0=0;
    //特殊问卷
    public static final String SELECT_SURVEYS="S8";
    //S8  12  d13a340b043511e9bb596c92bf5b8b6f
    public static final String CHAPTER_ID="d13a340b043511e9bb596c92bf5b8b6f";
    //S8  3   d0f71290043511e9bb596c92bf5b8b6f
    public static final String SELECT_CHAPTER="d0f71290043511e9bb596c92bf5b8b6f";

    public static final String AUTO_QUESTION_Q5="Q5";

    public static final String AUTO_QUESTION_Q74="Q74";

    public static final String AUTO_QUESTION_Q75="Q75";

    public static final String AUTO_QUESTION_Q19="Q19";

    //http://115.159.194.51:8080/interview?msg=我不是很高兴   Q5 Q74
    public static final String AUTO_MESSAGE_INTERVIEW ="http://115.159.194.51:8080/interview?msg=";

    //http://115.159.194.51:8080/bodyact?msg=我声音有点抖   Q75
    public static final String AUTO_MESSAGE_BODYACT ="http://115.159.194.51:8080/bodyact?msg=";

    //http://115.159.194.51:8080/authority?msg=领导老是说我傻  Q19
    public static final String AUTO_MESSAGE_AUTHORITY ="http://115.159.194.51:8080/authority?msg=";
}
