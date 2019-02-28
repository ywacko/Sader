package com.unidt.api.manage;

import com.unidt.mybatis.dto.SurveysBaseDto;
import com.unidt.services.sader.surveys.SurveysBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

/**
 * 组卷管理
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class SurveysBaseController {

    private static Logger log = LoggerFactory.getLogger(com.unidt.api.manage.SurveysBaseController.class);

    @Autowired
    SurveysBaseService surveysBaseService;

    /**
     * 根据输入获取问卷列表
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/surveysBase/getSurveyBaseList", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getSurveyBaseList(@RequestBody SurveysBaseDto dto) {
        log.info("根据输入获取问卷列表");
        return surveysBaseService.getSurvBaseList(dto);
    }

    /**
     * 根据ID获取单个问卷
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/surveysBase/getSurveyBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getSurveyBaseInfo(@RequestBody SurveysBaseDto dto) {
        log.info("根据ID获取单个问卷");
        return surveysBaseService.getSurvBaseInfo(dto);
    }

    /**
     * 根据ID修改单个问卷
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/surveysBase/editSurveyBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String editSurveyBaseInfo(@RequestBody SurveysBaseDto dto) {
        log.info("根据ID修改单个问卷");
        return surveysBaseService.editSurvBaseInfo(dto);
    }

    /**
     * 新增单个问卷
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/surveysBase/saveSurveyBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String saveSurveyBaseInfo(@RequestBody SurveysBaseDto dto) {
        log.info("新增单个问卷");
        return surveysBaseService.saveSurvBaseInfo(dto);
    }

    /**
     * 根据ID删除单个问卷
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/surveysBase/delSurveyBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String delSurveyBaseInfo(@RequestBody SurveysBaseDto dto) {
        log.info("根据ID删除单个问卷");
        return surveysBaseService.delSurvBaseInfo(dto);
    }

}
