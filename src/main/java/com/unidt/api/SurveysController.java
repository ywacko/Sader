package com.unidt.api;

import com.unidt.mybatis.dto.ChapterAnswerDto;

import com.unidt.mybatis.dto.ResponseSurveysDto;
import com.unidt.services.sader.question.QuestionService;
import com.unidt.services.sader.surveys.SurveysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class SurveysController {

    private static Logger log = LoggerFactory.getLogger(SurveysController.class);

    @Autowired
    SurveysService surveysService;

    @Autowired
    QuestionService questionService;

    /**
     * 获取问卷历史问题
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/surveys/getQuestionByCode", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getSurveysByNo(@RequestBody ResponseSurveysDto dto){
        log.info("访问问卷信息，问卷编号："+ dto.surveys_no);
       return questionService.getHisResponse(dto);
    }

    /**
     * 获取问卷历史问题
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/surveys/getResponseListByCode", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getResponseListByNo(@RequestBody ResponseSurveysDto dto){
        log.info("访问问卷信息，问卷编号："+ dto.surveys_no+"user_id:"+dto.user_id);
        return questionService.getHisResponseList(dto);
    }

    /**
     *  保存问题
     * @param info
     * @return
     */
    @RequestMapping(value="/sader/surveys/saveAnswerById", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String saveAnswerById(@RequestBody ChapterAnswerDto info){
        log.info("保存问题答案："+ info.surveys_id);
        return questionService.saveQuestionInfo(info);
    }

    /**
     * 获取下一题
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/surveys/getNextQuestionById", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getNextQuestionById(@RequestBody ChapterAnswerDto dto){

        log.info("根据问题id和问卷id获取下一问题："+ dto.surveys_id);

        return questionService.getQuesrionById(dto);
    }
    /**
     * 获取下一题
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/getNextQuestion", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getNextQuestion(@RequestBody ChapterAnswerDto dto){

        log.info("根据问题id和问卷id获取下一问题：surveys_id"+ dto.surveys_id+",response_id:"+dto.response_id+",surveys_chapter_id:"+dto.surveys_chapter_id);

        return questionService.getQuesrionById(dto);
    }

    /**
     * 根据用户id，删除对应用户应答
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/surveys/delAllResponse", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String delAllResponse(@RequestBody ChapterAnswerDto dto){

        log.info("调用删除用户下应答："+ dto.user_id);

        return questionService.delAllResponse(dto);
    }

}
