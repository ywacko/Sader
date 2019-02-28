package com.unidt.services.sader.question;

import com.unidt.helper.common.Constants;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.GetformatData;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.question.QuestionInfo;
import com.unidt.mybatis.bean.question.QuestionMessage;
import com.unidt.mybatis.bean.question.QuestionOption;
import com.unidt.mybatis.dto.QuestionInfoDto;
import com.unidt.mybatis.dto.QuestionMessageDto;
import com.unidt.mybatis.dto.QuestionOptionDto;
import com.unidt.mybatis.mapper.sader.QuestionBaseMapper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionBaseService {

    private static Logger log = LoggerFactory.getLogger(com.unidt.services.sader.
            question.QuestionBaseService.class);

    @Autowired
    QuestionBaseMapper questionBaseMapper;

    /**
     * 获取问题列表，包含精确查询，模糊查询和类型查询
     * @param dto
     * @return
     */
    public String getQuesBaseList(QuestionInfoDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.user_id)){
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        List<Document> resList = new ArrayList<>();
        log.info("开始查询问题列表:user_id：" + dto.user_id);
        try {

            if (StringUtils.isEmpty(dto.question_no)) dto.question_no = null;
            if (StringUtils.isEmpty(dto.question_content)) dto.question_content = null;
            if (StringUtils.isEmpty(dto.question_type)) dto.question_type = null;

            // 获取问题详情
            List<QuestionInfo> questionList = questionBaseMapper.getQuestionList(dto);
            for (QuestionInfo info : questionList) {
                Document doc = new Document("question_id", info.question_id);
                doc.append("question_no", info.question_no);
                doc.append("question_content", info.question_content);
                doc.append("question_type", info.question_type);
                doc.append("question_status", info.question_status);
                doc.append("question_desc", info.question_desc);
                resList.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("问题列表查询成功:" + ret.append("data", resList).toJson());
        return ret.append("data", resList).toJson();
    }

    /**
     * 获取单个问题详情
     * @param dto
     * @return
     */
    public String getQuesBaseInfo(QuestionInfoDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.question_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        Document res = new Document();
        log.info("开始查询单个问题详情:user_id：" + dto.user_id);
        try {

            // 通过ID精确查找问题
            QuestionInfo questionInfo = questionBaseMapper.getQuestionInfoById(dto.question_id);
            if (questionInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            res.append("question_id", questionInfo.question_id);
            res.append("question_no", questionInfo.question_no);
            res.append("question_type", questionInfo.question_type);
            res.append("question_url", questionInfo.question_url);
            res.append("question_content", questionInfo.question_content);
            res.append("question_status", questionInfo.question_status);
            res.append("question_url", questionInfo.question_url);
            res.append("question_path", questionInfo.question_path);
            res.append("create_time", questionInfo.create_time);
            res.append("create_user", questionInfo.create_user);
            res.append("update_time", questionInfo.update_time);
            res.append("update_user", questionInfo.update_user);
            res.append("question_desc", questionInfo.question_desc);

            // 查找该问题中的Option
            List<QuestionOption> optionList = questionBaseMapper.getOptionById(dto.question_id);
            List<Document> resList = new ArrayList<>();
            for (QuestionOption option : optionList) {
                Document doc = new Document("option_id", option.option_id);
                doc.append("option_no", option.option_no);
                doc.append("option_content", option.option_content);
                doc.append("option_type", option.option_type);
                doc.append("option_url", option.option_url);
                resList.add(doc);
            }
            res.append("optionList", resList);

            // 查找该问题中的question message 分为前置话术和后置话术
            List<QuestionMessageDto> qmList = questionBaseMapper.getQmListById(dto.question_id);
            List<Document> frontMessages = new ArrayList<>();
            List<Document> backMessages = new ArrayList<>();
            for (QuestionMessageDto qm : qmList) {
                Document doc = new Document("question_message_id", qm.question_message_id);
                doc.append("message_content", qm.message_content);
                doc.append("message_local", qm.message_local);
                doc.append("message_local_seq", qm.message_local_seq);
                doc.append("message_id", qm.message_id);
                if (qm.message_local == 1) frontMessages.add(doc);
                else backMessages.add(doc);
            }
            res.append("frontMessages", frontMessages);
            res.append("backMessages", backMessages);
        } catch (Exception e) {
            e.printStackTrace();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("单个问题详情查询成功:" + ret.append("data", res).toJson());
        return ret.append("data", res).toJson();
    }

    /**
     * 生成一个用于插入的question option
     * @param qPO
     * @param dto
     * @return
     */
    private void createOption(QuestionOptionDto qPO, QuestionInfoDto dto) {
        qPO.option_id = GetUUID32.getUUID32();
        qPO.question_id = dto.question_id;
        if (qPO.option_type == null) qPO.option_type = "";
        if (qPO.option_content == null) qPO.option_content = "";
        if (qPO.option_url == null) qPO.option_url = "";
        if (qPO.option_seq == null) qPO.option_seq = "0";
        qPO.create_time = GetformatData.getformatData();
        qPO.create_user = dto.user_id;
    }

    /**
     * 生成一个用于插入的question message
     * local为1是前置，为2是后置
     * @param qM
     * @param dto
     * @param local
     * @return
     */
    private void createQuestionMessage(QuestionMessage qM, QuestionInfoDto dto, int local){
        qM.question_message_id = GetUUID32.getUUID32();
        qM.question_id = dto.question_id;
        qM.message_local = local;
        if (qM.message_local_seq == null) qM.message_local_seq = 0;
    }

    /**
     * 根据ID修改单个问题
     * @param dto
     * @return
     */
    @Transactional
    public String editQuesBaseInfo(QuestionInfoDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.question_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始修改单个问题:user_id：" + dto.user_id);
        try {

            // 先得到该问题的详情
            QuestionInfo questionInfo = questionBaseMapper.getQuestionInfoById(dto.question_id);
            if (questionInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            dto.update_time = GetformatData.getformatData();

            // 更新问题
            Integer res = questionBaseMapper.editQuestionInfoById(dto);
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"修改失败！").toJson();
            }

            // 为了防止出现NO数量越来越大，对于OptionList， 如果是草稿状态就先删后插入，
            // 非草稿状态直接修改然后逻辑删
            if (questionInfo.question_status.equals("0")) {
                questionBaseMapper.delOptionById(dto.question_id);
                if (dto.optionList != null) {
                    int no = 0;
                    for (QuestionOptionDto qPO : dto.optionList) {
                        createOption(qPO, dto);
                        no++;
                        qPO.option_no = dto.question_no + "_OPT" + no;
                        questionBaseMapper.saveOptionInfo(qPO);
                    }
                }
            } else {

                // 新增一个Option的Dto，用作删除多余的question option
                QuestionOptionDto questionOptionDto = new QuestionOptionDto();
                questionOptionDto.question_id = dto.question_id;
                questionOptionDto.update_user = dto.user_id;
                questionOptionDto.update_time = GetformatData.getformatData();
                if (dto.optionList != null) {
                    questionOptionDto.statusList = new ArrayList<>();
                    for (QuestionOptionDto qPO : dto.optionList) {
                        if (StringUtils.isEmpty(qPO.option_id)) {
                            createOption(qPO, dto);
                            String maxNo = questionBaseMapper.getMaxOptionNoById(dto.question_id);
                            if (StringUtils.isEmpty(maxNo)) maxNo = "0";
                            qPO.option_no = dto.question_no + "OPT_" + (Integer.valueOf(maxNo) + 1);
                            questionBaseMapper.saveOptionInfo(qPO);
                        } else {
                            qPO.update_time = GetformatData.getformatData();
                            qPO.update_user = dto.user_id;
                            questionBaseMapper.editOptionById(qPO);
                        }
                        questionOptionDto.statusList.add(qPO.option_id);
                    }
                }
                questionBaseMapper.delOptionByLogic(questionOptionDto);
            }

            // 对于question message直接先删除后插入
            questionBaseMapper.delQmListById(dto.question_id);
            if (dto.frontMessages != null) {
                for (QuestionMessage qM : dto.frontMessages) {
                    if (!StringUtils.isEmpty(qM.message_id)) {
                        createQuestionMessage(qM, dto, 1);
                        questionBaseMapper.saveQmInfo(qM);
                    }
                }
            }
            if (dto.backMessages != null) {
                for (QuestionMessage qM : dto.backMessages) {
                    if (!StringUtils.isEmpty(qM.message_id)) {
                        createQuestionMessage(qM, dto, 2);
                        questionBaseMapper.saveQmInfo(qM);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单个问题修改成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }

    /**
     * 新增问题
     * @param dto
     * @return
     */
    @Transactional
    public String saveQuesBaseInfo(QuestionInfoDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.user_id) || StringUtils.isEmpty(dto.question_type)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始新增单个问题:user_id：" + dto.user_id);
        try {

            // 查找最大no，用于新增
            String maxNo = questionBaseMapper.getMaxQuestionNo();
            if (StringUtils.isEmpty(maxNo)) maxNo = "0";
            dto.question_no = "Q" + (Integer.valueOf(maxNo) + 1);
            dto.question_id = GetUUID32.getUUID32();
            dto.create_time = GetformatData.getformatData();
            if (dto.question_content == null) dto.question_content = "";
            if (dto.question_url == null) dto.question_url = "";
            if (dto.question_desc == null) dto.question_desc = "";
            if (dto.question_path == null) dto.question_path = "";
            if (dto.question_name == null) dto.question_name = "";

            // 设置好dto之后直接插入
            Integer res = questionBaseMapper.saveQuestionInfo(dto);
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"插入失败！").toJson();
            }

            // 新增optionList
            if (dto.optionList != null) {
                int no = 0;
                for (QuestionOptionDto qPO : dto.optionList) {
                    createOption(qPO, dto);
                    no++;
                    qPO.option_no = dto.question_no + "_OPT" + no;
                    questionBaseMapper.saveOptionInfo(qPO);
                }
            }

            // 新增question message， 分为前置话术和后置话术
            if (dto.frontMessages != null) {
                for (QuestionMessage qM : dto.frontMessages) {
                    if (!StringUtils.isEmpty(qM.message_id)) {
                        createQuestionMessage(qM, dto, 1);
                        questionBaseMapper.saveQmInfo(qM);
                    }
                }
            }
            if (dto.backMessages != null) {
                for (QuestionMessage qM : dto.backMessages) {
                    if (!StringUtils.isEmpty(qM.message_id)) {
                        createQuestionMessage(qM, dto, 2);
                        questionBaseMapper.saveQmInfo(qM);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单个问题插入成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }

    /**
     * 删除单个问题，分为物理删除和逻辑删除
     * @param dto
     * @return
     */
    @Transactional
    public String delQuesBaseInfo(QuestionInfoDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.question_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始删除单个问题:user_id：" + dto.user_id);
        try {

            // 先得到该问题的详情
            QuestionInfo questionInfo = questionBaseMapper.getQuestionInfoById(dto.question_id);
            if (questionInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            Integer res;

            // 如果是草稿状态，直接全删，否则不管questionMessage，对其他两个表做逻辑删
            if (questionInfo.question_status.equals("0")) {
                questionBaseMapper.delQmListById(dto.question_id);
                questionBaseMapper.delOptionById(dto.question_id);
                res = questionBaseMapper.delQuestionInfoById(dto.question_id);
            } else {
                QuestionOptionDto questionOptionDto = new QuestionOptionDto();
                questionOptionDto.question_id = dto.question_id;
                questionOptionDto.update_user = dto.user_id;
                questionOptionDto.update_time = GetformatData.getformatData();
                questionBaseMapper.delOptionByLogic(questionOptionDto);
                dto.update_time = GetformatData.getformatData();
                res = questionBaseMapper.delQuestionInfoByLogic(dto);
            }
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"删除失败！").toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单个问题删除成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }
}
