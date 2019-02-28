package com.unidt.services.sader.surveys;

import com.unidt.helper.common.Constants;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.question.QuestionMessage;

import com.unidt.mybatis.dto.*;
import com.unidt.mybatis.mapper.sader.MessageMapper;
import com.unidt.mybatis.mapper.sader.ResponseMapper;
import com.unidt.mybatis.mapper.sader.SurveysMapper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveysService {
    private static Logger log = LoggerFactory.getLogger(SurveysService.class);
    @Autowired
    SurveysMapper surveysMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    ResponseMapper responseMapper;
    /**
     * 获取问卷第一题
     * 获取问卷第一题章节 -及问题id 获取问题
     */
    public Document getSurveysQuestionBySeq(String surveys_id,String next_chapter_id,String user_id){
        Document res = new Document();
        int question_seq =1;
        //获取问卷第一条问题
        SurveysChapterDto chapterDto = new  SurveysChapterDto();
        if(StringUtils.isEmpty(next_chapter_id)){
            question_seq =1;
            chapterDto=  surveysMapper.getSurveysFirstChaptersById(surveys_id,question_seq);
        }else{
            chapterDto=  surveysMapper.getSurveysChaptersById(next_chapter_id);
        }
        String surveyType = surveysMapper.getSurveysTypeById(chapterDto.surveys_id);
        Integer end_chapter = chapterDto.is_end_chapter;
        //非第一个问卷最后一题，均需要判断下一个方案选择，根据最后一次第二个问卷的选择判断
        if(end_chapter==1 && !surveyType.equals(Constants.SURVEYS_01)){
            //判断当前用户之前最后一次的问卷编号，是S2、S3、S4
            String surveys_no  = surveysMapper.getSurveysChapter(user_id);
            if(!StringUtils.isEmpty(surveys_no)&&surveys_no.equals(Constants.SURVEYS_PLAN_NO_1)){
                //TODO 增加问卷选择题答案
                //判断
                res.append("next_plan_select",1);
            }else if(!StringUtils.isEmpty(surveys_no)&&surveys_no.equals(Constants.SURVEYS_PLAN_NO_2)){
                res.append("next_plan_select",2);
            }else{
                res.append("next_plan_select",3);
            }
        }
         res.append("surveys_chapter_id",chapterDto.surveys_chapter_id)
            .append("question_id",chapterDto.question_id)
            .append("question_no",chapterDto.question_no)
            .append("question_type",chapterDto.question_type)
            .append("question_url",chapterDto.question_url)
             .append("question_path",chapterDto.question_path)
            .append("question_content",chapterDto.question_content)
            .append("surveys_chapter_seq",chapterDto.surveys_chapter_seq)
            .append("surveys_next_chapter",chapterDto.surveys_next_chapter)
            .append("next_surveys",chapterDto.next_surveys)
            .append("surveys_id",chapterDto.surveys_id)
            .append("is_end_chapter",chapterDto.is_end_chapter)
            .append("surveys_chapter_type",chapterDto.surveys_chapter_type);
            if(!StringUtils.isEmpty(chapterDto.question_no)){
                if(chapterDto.question_no.equals(Constants.AUTO_QUESTION_Q74)){
                    res.append("auto_message_url",Constants.AUTO_MESSAGE_INTERVIEW);
                }
                if(chapterDto.question_no.equals(Constants.AUTO_QUESTION_Q75)){
                    res.append("auto_message_url",Constants.AUTO_MESSAGE_BODYACT);
                }
                if(chapterDto.question_no.equals(Constants.AUTO_QUESTION_Q19)){
                    res.append("auto_message_url",Constants.AUTO_MESSAGE_AUTHORITY);
                }
            }
        if(!StringUtils.isEmpty(chapterDto.surveys_chapter_id)){
            List<SurveysOptionDto>   optionDtoList  =  surveysMapper.getSurveysOptionList(chapterDto.surveys_chapter_id);
            List<Document> optionList = new ArrayList<>();
            if(optionDtoList.size()>0){
                for(SurveysOptionDto optionDto:optionDtoList){
                    Document optionDoc = new Document();
                    SurveysOptionDto opt = surveysMapper.getSurveysOption(chapterDto.surveys_chapter_id,optionDto.option_id);
                    optionDoc.append("next_question",optionDto.next_question)
                            .append("option_id",optionDto.option_id)
                            .append("surveys_option_id",optionDto.surveys_option_id)
                            .append("option_content",optionDto.option_content)
                            .append("option_url",optionDto.option_url)
                            .append("option_type",optionDto.option_type)
                            .append("option_seq",optionDto.option_seq)
                            .append("option_next_surveys",optionDto.option_next_surveys)
                            .append("option_answer",optionDto.option_answer)
                            .append("option_answer_url",optionDto.option_answer_url)
                            .append("next_question","")
                            .append("surveys_option_id","")
                            .append("option_next_surveys","");
                    if(opt!=null){
                        optionDoc.append("next_question",opt.next_question);
                        optionDoc.append("surveys_option_id",opt.surveys_option_id);
                        optionDoc.append("option_next_surveys",opt.option_next_surveys);
                    }
                    optionList.add(optionDoc);
                }
            }
            res.append("optionList",optionList);
        }
        //获取事前话术
        List<Document> frontMessages = getSurveysMessageById(chapterDto.question_id,Constants.Message_local_01);
        res.append("frontMessages",frontMessages);
        //获取时候话术
        List<Document> backMessages = getSurveysMessageById(chapterDto.question_id,Constants.Message_local_02);
        res.append("backMessages",backMessages);
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");

        return res;
    }

    /**
     * 根据问题获取问题话术集
     * @param question_id
     *
     * @return
     */
    public List<Document> getSurveysMessageById(String question_id,int message_local){
        Document res = new Document();
        QuestionMessage queMes = new QuestionMessage();
        queMes.message_local = message_local;
        queMes.question_id = question_id;
        List<QuestionMessageDto> messageList = messageMapper.getQuestionMessageList(queMes);
        List<Document> docList = new ArrayList<>();
        if(messageList.size()>0){
            for(QuestionMessageDto mesDto:messageList){
                Document doc = new Document();
                     doc.append("question_message_id",mesDto.question_message_id)
                        .append("message_id",mesDto.message_id)
                        .append("message_local",mesDto.message_local)
                        .append("message_local_seq",mesDto.message_local_seq)
                        .append("message_no",mesDto.message_no)
                        .append("message_type",mesDto.message_type)
                        .append("message_url",mesDto.message_url)
                        .append("message_content",mesDto.message_content);
                docList.add(doc);
            }
        }
        return docList;
    }

    /**
     * 根据应答章节id获取应答话术
     * dto  中 chapter_answer_id,
     *      answer_message_local 中 1 前置，2后置
     */
    public List<Document> getMessageAnswersById(String chapter_answer_id, int message_chapter_local){

        MessageAnswerDto dto = new MessageAnswerDto();
        dto.chapter_answer_id = chapter_answer_id;
        dto.message_local = message_chapter_local;
        List<MessageAnswerDto>  messageAnswerDtoList = responseMapper.getMessageAnswerListById(dto);
        List<Document> docList = new ArrayList<>();
        if(messageAnswerDtoList.size()>0){
            for(MessageAnswerDto messDto:messageAnswerDtoList){

                Document  messDoc =new Document();
                messDoc.append("message_content",messDto.message_content);
                messDoc.append("message_id",messDto.message_id);
                messDoc.append("answer_message_id",messDto.answer_message_id);
                messDoc.append("message_local",messDto.message_local);
                messDoc.append("message_seq",messDto.message_seq);
                messDoc.append("message_type",messDto.message_type);
                messDoc.append("message_url",messDto.message_url);
                messDoc.append("message_no",messDto.message_no);
                docList.add(messDoc);
            }
        }
        return docList;
    }

}
