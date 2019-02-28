package com.unidt.api.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import com.unidt.services.sader.question.QuestionBaseService;
import com.unidt.mybatis.dto.QuestionInfoDto;

/**
 * 基础问题管理
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class QuestionBaseController {

    private static Logger log = LoggerFactory.getLogger(com.unidt.api.manage.QuestionBaseController.class);

    @Autowired
    QuestionBaseService questionBaseService;

    /**
     * 根据输入获取问题列表
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/questionBase/getQuestionBaseList", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getQuestionBaseList(@RequestBody QuestionInfoDto dto) {
        log.info("根据输入获取问题列表");
        return questionBaseService.getQuesBaseList(dto);
    }

    /**
     * 根据id获取单个问题
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/questionBase/getQuestionBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getQuestionBaseInfo(@RequestBody QuestionInfoDto dto) {
        log.info("根据id获取单个问题");
        return questionBaseService.getQuesBaseInfo(dto);
    }

    /**
     * 根据id修改单个问题
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/questionBase/editQuestionBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String editQuestionBaseInfo(@RequestBody QuestionInfoDto dto) {
        log.info("根据id修改单个问题");
        return questionBaseService.editQuesBaseInfo(dto);
    }

    /**
     * 根据输入新增单个问题
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/questionBase/saveQuestionBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String saveQuestionBaseInfo(@RequestBody QuestionInfoDto dto) {
        log.info("根据输入新增单个问题");
        return questionBaseService.saveQuesBaseInfo(dto);
    }

    /**
     * 根据id删除单个问题
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/questionBase/delQuestionBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String delQuestionBaseInfo(@RequestBody QuestionInfoDto dto) {
        log.info("根据id删除单个问题");
        return questionBaseService.delQuesBaseInfo(dto);
    }

}
