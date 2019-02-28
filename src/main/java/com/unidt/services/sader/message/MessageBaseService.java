package com.unidt.services.sader.message;

import com.unidt.helper.common.Constants;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.GetformatData;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.message.MessageInfo;
import com.unidt.mybatis.bean.message.MessageParam;
import com.unidt.mybatis.dto.MessageBaseDto;
import com.unidt.mybatis.mapper.sader.MessageBaseMapper;
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
public class MessageBaseService {

    private static Logger log = LoggerFactory.getLogger(com.unidt.services.sader.message.MessageBaseService.class);

    @Autowired
    MessageBaseMapper messageBaseMapper;

    /**
     * 获取话术列表，包含精确查询，模糊查询和类型查询
     * @param dto
     * @return
     */
    public String getMesBaseList(MessageBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.user_id)){
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        List<Document> resList = new ArrayList<>();
        log.info("开始查询话术列表:user_id：" + dto.user_id);
        try {

            if (StringUtils.isEmpty(dto.message_no)) dto.message_no = null;
            if (StringUtils.isEmpty(dto.message_content)) dto.message_content = null;
            if (StringUtils.isEmpty(dto.message_type)) dto.message_type = null;

            // 获取话术详情
            List<MessageInfo> messageList = messageBaseMapper.getMessageList(dto);
            for (MessageInfo info : messageList) {
                Document doc = new Document("message_id", info.message_id);
                doc.append("message_no", info.message_no);
                doc.append("message_content", info.message_content);
                doc.append("message_type", info.message_type);
                doc.append("message_status", info.message_status);
                doc.append("message_desc", info.message_desc);
                resList.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("话术列表查询成功:" + ret.append("data", resList).toJson());
        return ret.append("data", resList).toJson();
    }

    /**
     * 获取单个话术详情
     * @param dto
     * @return
     */
    public String getMesBaseInfo(MessageBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.message_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        Document res = new Document();
        log.info("开始查询单条话术详情:user_id：" + dto.user_id);
        try {

            // 通过ID精确查找话术
            MessageInfo messageInfo = messageBaseMapper.getMessageInfoById(dto.message_id);
            if (messageInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            res.append("message_id", messageInfo.message_id);
            res.append("message_no", messageInfo.message_no);
            res.append("message_type", messageInfo.message_type);
            res.append("message_url", messageInfo.message_url);
            res.append("message_content", messageInfo.message_content);
            res.append("message_status", messageInfo.message_status);
            res.append("message_local", messageInfo.message_local);
            res.append("create_time", messageInfo.create_time);
            res.append("create_user", messageInfo.create_user);
            res.append("update_time", messageInfo.update_time);
            res.append("update_user", messageInfo.update_user);
            res.append("del_flag", messageInfo.del_flag);
            res.append("message_desc", messageInfo.message_desc);

            // 查找该话术中的参数
            List<MessageParam> paramList = messageBaseMapper.getParamById(dto.message_id);
            List<Document> resList = new ArrayList<>();
            for (MessageParam param : paramList) {
                Document doc = new Document();
                doc.append("param_id", param.param_id);
                doc.append("param_no", param.param_no);
                doc.append("param_type", param.param_type);
                doc.append("param_name", param.param_name);
                doc.append("question_no", param.question_no);
                resList.add(doc);
            }
            res.append("paramList", resList);
        } catch (Exception e) {
            e.printStackTrace();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("单条话术详情查询成功:" + ret.append("data", res).toJson());
        return ret.append("data", res).toJson();
    }

    /**
     * 通过ID修改单个话术
     * @param dto
     * @return
     */
    @Transactional
    public String editMesBaseInfo(MessageBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.message_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        dto.update_time = GetformatData.getformatData();
        log.info("开始修改单条话术:user_id：" + dto.user_id);
        try {

            // 更新话术
            Integer res = messageBaseMapper.editMesBaseById(dto);
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"修改失败！").toJson();
            }

            // 对于参数列表，先删后插入
            messageBaseMapper.delParamById(dto.message_id);
            if (dto.paramList != null) {
                for (MessageParam param : dto.paramList) {
                    param.message_id = dto.message_id;
                    param.param_id = GetUUID32.getUUID32();
                    if (param.param_name == null) param.param_name = "";
                    if (param.param_no == null) param.param_no = "";
                    if (param.param_type == null) param.param_type = "";
                    if (param.question_no == null) param.question_no = "";
                    messageBaseMapper.saveParamById(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单条话术修改成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }

    /**
     * 新增话术
     * @param dto
     * @return
     */
    @Transactional
    public String saveMesBaseInfo(MessageBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始新增单条话术:user_id：" + dto.user_id);
        try {

            // 查找最大no，用于新增
            String maxNo = messageBaseMapper.getMaxMessageNo();
            if (StringUtils.isEmpty(maxNo)) {
                maxNo = "0";
            }
            dto.message_no = "M" + (Integer.valueOf(maxNo) + 1);
            dto.message_id = GetUUID32.getUUID32();
            dto.create_time = GetformatData.getformatData();
            if (StringUtils.isEmpty(dto.message_type)) dto.message_type = "MESSAGE_TEXT";
            if (dto.message_content == null) dto.message_content = "";
            if (dto.message_url == null) dto.message_url = "";
            if (dto.message_desc == null) dto.message_desc = "";

            // 新增话术
            Integer res = messageBaseMapper.saveMesBaseInfo(dto);
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"插入失败！").toJson();
            }

            // 新增参数列表
            if (dto.paramList != null) {
                for (MessageParam param : dto.paramList) {
                    param.message_id = dto.message_id;
                    param.param_id = GetUUID32.getUUID32();
                    if (param.param_name == null) param.param_name = "";
                    if (param.param_no == null) param.param_no = "";
                    if (param.param_type == null) param.param_type = "";
                    if (param.question_no == null) param.question_no = "";
                    messageBaseMapper.saveParamById(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单条话术插入成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }

    /**
     * 删除单个话术，分为物理删除和逻辑删除
     * @param dto
     * @return
     */
    @Transactional
    public String delMesBaseInfo(MessageBaseDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.message_id) || StringUtils.isEmpty(dto.user_id)) {
            log.info("参数错误");
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
        }
        log.info("开始删除单条话术:user_id：" + dto.user_id);
        try {

            // 先得到该话术的详情
            MessageInfo messageInfo = messageBaseMapper.getMessageInfoById(dto.message_id);
            if (messageInfo == null) {
                log.info("参数错误");
                return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "参数错误！").toJson();
            }
            Integer res;

            // 如果是草稿状态就物理删，否则逻辑删
            if (messageInfo.message_status.equals("0")) {
                messageBaseMapper.delParamById(dto.message_id);
                res = messageBaseMapper.delMesBaseById(dto.message_id);
            } else {
                dto.update_time = GetformatData.getformatData();
                res = messageBaseMapper.delMesBaseByLogic(dto);
            }
            if (res != 1) {
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"删除失败！").toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        log.info("单条话术删除成功:" + ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson());
        return ReturnResult.createResult(Constants.API_CODE_OK, "OK").toJson();
    }
}
