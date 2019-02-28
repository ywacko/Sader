package com.unidt.services.sader.surveys;

import com.unidt.helper.common.*;
import com.unidt.mybatis.bean.surveys.SurveysChapter;
import com.unidt.mybatis.bean.surveys.SurveysInfo;
import com.unidt.mybatis.dto.QuestionOptionDto;
import com.unidt.mybatis.dto.SurveysBaseDto;
import com.unidt.mybatis.dto.SurveysChapterDto;
import com.unidt.mybatis.dto.SurveysOptionDto;
import com.unidt.mybatis.mapper.sader.SurveysBaseMapper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SurveysBaseService {

    private static Logger log = LoggerFactory.getLogger(com.unidt.services.sader.
            surveys.SurveysBaseService.class);

    @Autowired
    SurveysBaseMapper surveysBaseMapper;

    /**
     * 获取组卷列表，包含精确查询，模糊查询和类型查询
     * @param dto
     * @return
     */
    public String getSurvBaseList(SurveysBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        List<Document> resList = new ArrayList<>();
        log.info("开始查询组卷列表:user_id：" + dto.user_id);
        try {

            if (StringUtils.isEmpty(dto.surveys_no)) dto.surveys_no = null;
            if (StringUtils.isEmpty(dto.surveys_name)) dto.surveys_name = null;
            if (StringUtils.isEmpty(dto.surveys_desc)) dto.surveys_desc = null;

            // 获取组卷详情
            List<SurveysInfo> surveysList = surveysBaseMapper.getSurvBaseList(dto);
            for (SurveysInfo info : surveysList) {
                Document doc = new Document("surveys_id", info.surveys_id);
                doc.append("surveys_no", info.surveys_no);
                doc.append("surveys_name", info.surveys_name);
                doc.append("surveys_desc", info.surveys_desc);
                doc.append("create_time", info.create_time);
                doc.append("surveys_status", info.surveys_status);
                resList.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("组卷列表查询成功:" + ret.append("data", resList).toJson());
        return ret.append("data", resList).toJson();
    }

    /**
     * 获取单个组卷详情
     * @param dto
     * @return
     */
    public String getSurvBaseInfo(SurveysBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.surveys_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        Document res = new Document();
        log.info("开始查询单个组卷详情:user_id：" + dto.user_id);
        try {

            // 通过ID精确查找组卷
            SurveysInfo surveysInfo = surveysBaseMapper.getSurvBaseInfo(dto.surveys_id);
            if (surveysInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            res.append("surveys_id", surveysInfo.surveys_id);
            res.append("surveys_no", surveysInfo.surveys_no);
            res.append("surveys_name", surveysInfo.surveys_name);
            res.append("surveys_status", surveysInfo.surveys_status);
            res.append("surveys_type", surveysInfo.surveys_type);
            res.append("surveys_desc", surveysInfo.surveys_desc);
            res.append("del_flag", surveysInfo.del_flag);
            res.append("create_user", surveysInfo.create_user);
            res.append("create_time", surveysInfo.create_time);
            res.append("update_user", surveysInfo.update_user);
            res.append("update_time", surveysInfo.update_time);

            // 查找该组卷中的chapter
            List<SurveysChapterDto> surveysChapterList = surveysBaseMapper.getSurvChapterList(dto.surveys_id);
            List<Document> chapterResList = new ArrayList<>();
            for (SurveysChapterDto sCD : surveysChapterList) {
                Document chapterDoc = new Document("surveys_chapter_id", sCD.surveys_chapter_id);
                chapterDoc.append("question_id", sCD.question_id);
                chapterDoc.append("question_no", sCD.question_no);
                chapterDoc.append("question_type", sCD.question_type);
                chapterDoc.append("question_content", sCD.question_content);
                chapterDoc.append("surveys_chapter_seq", sCD.surveys_chapter_seq);
                chapterDoc.append("next_surveys", sCD.next_surveys);
                chapterDoc.append("surveys_next_chapter", sCD.surveys_next_chapter);

                // 查找该chapter中的option
                List<SurveysOptionDto> surveysOptionDtoList = surveysBaseMapper.getSurvOptionList(sCD.surveys_chapter_id);
                List<Document> optionResList = new ArrayList<>();
                for (SurveysOptionDto sOD : surveysOptionDtoList) {
                    Document optionDoc = new Document("surveys_option_id", sOD.surveys_option_id);
                    optionDoc.append("option_id", sOD.option_id);
                    optionDoc.append("option_no", sOD.option_no);
                    optionDoc.append("option_type", sOD.option_type);
                    optionDoc.append("option_content", sOD.option_content);
                    optionDoc.append("option_seq", sOD.option_seq);
                    optionDoc.append("option_next_surveys", sOD.option_next_surveys);
                    optionDoc.append("next_chapter_id", sOD.next_chapter_id);
                    optionResList.add(optionDoc);
                }
                chapterDoc.append("surveysOptionList", optionResList);
                chapterResList.add(chapterDoc);
            }
            res.append("chapterList", chapterResList);
        } catch (Exception e) {
            e.printStackTrace();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("单个组卷详情查询成功:" + ret.append("data", res).toJson());
        return ret.append("data", res).toJson();
    }

    /**
     * 根据ID修改单个组卷
     * @param dto
     * @return
     */
    @Transactional
    public String editSurvBaseInfo(SurveysBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.surveys_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始修改单个组卷:user_id：" + dto.user_id);
        try {

            // 先得到该组卷的详情
            SurveysInfo surveysInfo = surveysBaseMapper.getSurvBaseInfo(dto.surveys_id);
            if (surveysInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            dto.update_time = GetformatData.getformatData();

            // 更新组卷
            Integer res = surveysBaseMapper.editSurvBaseInfoById(dto);
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"修改失败！").toJson();
            }

            // 新增一个chapter的Dto，用作删除多余的surveys_chapter。
            // 此时的删除列表（statusList）还没有new，只当chapterList不为空才会new
            SurveysChapterDto surveysChapterDto = new SurveysChapterDto();
            surveysChapterDto.surveys_id = dto.surveys_id;

            // surveys_option不存在del_flag，可以直接全删，之后再插入
            List<SurveysChapterDto> surveysChapterList = surveysBaseMapper.getSurvChapterList(dto.surveys_id);
            for (SurveysChapterDto sCD : surveysChapterList)
                surveysBaseMapper.delSurvOption(sCD.surveys_chapter_id);

            if (dto.chapterList != null) {

                // 按照chapter_seq排序，chapter_seq必须存，唯一且与chapter_next_seq对应
                Collections.sort(dto.chapterList, new SortBySeq());

                // chapter第一次循环先把id以及基本信息写入
                for (SurveysChapterDto sCD : dto.chapterList) {
                    sCD.isNew = false;
                    if (StringUtils.isEmpty(sCD.surveys_chapter_id)) {
                        sCD.surveys_chapter_id = GetUUID32.getUUID32();
                        sCD.surveys_id = dto.surveys_id;
                        sCD.surveys_no = surveysInfo.surveys_no;
                        if (sCD.surveys_chapter_type == null ) sCD.surveys_chapter_type = "01";
                        sCD.isNew = true;
                    }
                    sCD.surveys_chapter_no = surveysInfo.surveys_no + "_CPT" + sCD.surveys_chapter_seq;
                    if (sCD.surveysOptionList != null) {
                        for (SurveysOptionDto sOD : sCD.surveysOptionList) {
                            sOD.surveys_option_id = GetUUID32.getUUID32();
                            sOD.surveys_chapter_id = sCD.surveys_chapter_id;
                        }
                    }
                }

                // 此时new出statusList
                surveysChapterDto.statusList = new ArrayList<>();

                // 第二次循环根据id和seq写入next_chapter信息，
                for (SurveysChapterDto sCD : dto.chapterList) {
                    if (sCD.next_chapter_seq.equals("0")) {
                        sCD.is_end_chapter = 1;
                        sCD.surveys_next_chapter = "";
                    } else {
                        sCD.is_end_chapter = 0;
                        sCD.surveys_next_chapter =
                                dto.chapterList.get(Integer.valueOf(sCD.next_chapter_seq) - 1).surveys_chapter_id;
                    }

                    // 开始更新surveys_chapter
                    // 如果是新增的chapter就使用insert，否则使用update
                    if (sCD.isNew) surveysBaseMapper.saveSurvChapter(sCD);
                    else surveysBaseMapper.editSurvChapter(sCD);

                    if (sCD.surveysOptionList != null) {
                        for (SurveysOptionDto sOD : sCD.surveysOptionList) {
                            if (sOD.next_chapter_seq == null || sOD.next_chapter_seq.equals("0"))
                                sOD.next_chapter_id = "";
                            else
                                sOD.next_chapter_id = dto.chapterList.
                                        get(Integer.valueOf(sCD.next_chapter_seq) - 1).surveys_chapter_id;
                            if (sOD.option_next_surveys == null) sOD.option_next_surveys = "";

                            // 插入新的surveys_option
                            surveysBaseMapper.saveSurvOption(sOD);
                        }
                    }

                    // 把所有已存在的chapter_id存在数组里，最后删除的时候只删除不在数组里的surveys_chapter
                    surveysChapterDto.statusList.add(sCD.surveys_chapter_id);
                }
            }

            // 草稿状态用物理删，否则逻辑删
            if (surveysInfo.surveys_status.equals("0"))
                surveysBaseMapper.delSurvChapterById(surveysChapterDto);
            else
                surveysBaseMapper.delSurvChapterByLogic(surveysChapterDto);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单个组卷修改成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }

    /**
     * 新增组卷
     * @param dto
     * @return
     */
    @Transactional
    public String saveSurvBaseInfo(SurveysBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.user_id) || StringUtils.isEmpty(dto.surveys_type)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始新增单个组卷:user_id：" + dto.user_id);
        try {

            // 查找最大no，用于新增
            String maxSurvNo = surveysBaseMapper.getMaxSurvNo();
            if (StringUtils.isEmpty(maxSurvNo)) maxSurvNo = "0";
            dto.surveys_no = "S" + (Integer.valueOf(maxSurvNo) + 1);
            dto.surveys_id = GetUUID32.getUUID32();
            dto.create_time = GetformatData.getformatData();
            if (dto.surveys_name == null) dto.surveys_name = "";
            if (dto.surveys_desc == null) dto.surveys_desc = "";

            // 设置好dto之后直接插入
            Integer res = surveysBaseMapper.saveSurvBaseInfo(dto);
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"插入失败！").toJson();
            }
            if (dto.chapterList != null) {
                Collections.sort(dto.chapterList, new SortBySeq());

                // chapter第一次循环先把id以及基本信息写入
                for (SurveysChapterDto sCD: dto.chapterList) {
                    sCD.surveys_chapter_no = dto.surveys_no + "_CPT" + sCD.surveys_chapter_seq;
                    sCD.surveys_chapter_id = GetUUID32.getUUID32();
                    sCD.surveys_id = dto.surveys_id;
                    sCD.surveys_no = dto.surveys_no;
                    if (sCD.surveys_chapter_type == null ) sCD.surveys_chapter_type = "01";
                    if (sCD.surveysOptionList != null) {
                        for (SurveysOptionDto sOD : sCD.surveysOptionList) {
                            sOD.surveys_option_id = GetUUID32.getUUID32();
                            sOD.surveys_chapter_id = sCD.surveys_chapter_id;
                        }
                    }
                }

                // 第二次循环根据id和seq写入next_chapter信息，
                for (SurveysChapterDto sCD: dto.chapterList) {
                    if (sCD.next_chapter_seq.equals("0")) {
                        sCD.is_end_chapter = 1;
                        sCD.surveys_next_chapter = "";
                    } else {
                        sCD.is_end_chapter = 0;
                        sCD.surveys_next_chapter =
                                dto.chapterList.get(Integer.valueOf(sCD.next_chapter_seq) - 1).surveys_chapter_id;
                    }

                    // 开始更新surveys_chapter
                    surveysBaseMapper.saveSurvChapter(sCD);
                    if (sCD.surveysOptionList != null) {
                        for (SurveysOptionDto sOD : sCD.surveysOptionList) {
                            if (sOD.next_chapter_id == null || sOD.next_chapter_seq.equals("0"))
                                sOD.next_chapter_id = "";
                            else
                                sOD.next_chapter_id = dto.chapterList.
                                        get(Integer.valueOf(sCD.next_chapter_seq) - 1).surveys_chapter_id;
                            if (sOD.option_next_surveys == null) sOD.option_next_surveys = "";

                            // 插入新的surveys_option
                            surveysBaseMapper.saveSurvOption(sOD);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单个组卷插入成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }

    /**
     * 删除单个组卷，分为物理删除和逻辑删除
     * @param dto
     * @return
     */
    @Transactional
    public String delSurvBaseInfo(SurveysBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.surveys_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始删除单个组卷:user_id：" + dto.user_id);
        try {

            // 先得到该组卷的详情
            SurveysInfo surveysInfo = surveysBaseMapper.getSurvBaseInfo(dto.surveys_id);
            if (surveysInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            Integer res;

            // surveys_option不存在del_flag，可以直接全删
            List<SurveysChapterDto> surveysChapterList = surveysBaseMapper.getSurvChapterList(dto.surveys_id);
            for (SurveysChapterDto sCD : surveysChapterList)
                surveysBaseMapper.delSurvOption(sCD.surveys_chapter_id);
            SurveysChapterDto surveysChapterDto = new SurveysChapterDto();
            surveysChapterDto.surveys_id = dto.surveys_id;

            // 草稿状态物理删，否则逻辑删
            if (surveysInfo.surveys_status.equals("0")) {
                surveysBaseMapper.delSurvChapterById(surveysChapterDto);
                res = surveysBaseMapper.delSurvBaseInfoById(dto.surveys_id);
            } else {
                surveysBaseMapper.delSurvChapterByLogic(surveysChapterDto);
                dto.update_time = GetformatData.getformatData();
                res = surveysBaseMapper.delSurvBaseInfoByLogic(dto);
            }
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"删除失败！").toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单个组卷删除成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }
}