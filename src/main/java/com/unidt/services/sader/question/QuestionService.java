package com.unidt.services.sader.question;

import com.unidt.helper.common.Constants;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.GetformatData;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.response.ChapterAnswer;
import com.unidt.mybatis.bean.response.MessageAnswer;
import com.unidt.mybatis.bean.response.OptionAnswer;
import com.unidt.mybatis.bean.response.ResponseInfo;
import com.unidt.mybatis.bean.surveys.SurveysInfo;
import com.unidt.mybatis.dto.*;
import com.unidt.mybatis.mapper.sader.ResponseMapper;
import com.unidt.mybatis.mapper.sader.SurveysMapper;
import com.unidt.services.sader.surveys.SurveysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.Document;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class QuestionService {

    private static Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    SurveysMapper  surveysMapper;

    @Autowired
    ResponseMapper responseMapper;

    @Autowired
    SurveysService surveysService;

    /**
     * 根据问卷id和问题id获取下一问题和话术
     * 问卷获取当前题
     * @param chapterAnswerDto
     * @return
     */

    public String getQuesrionById(ChapterAnswerDto chapterAnswerDto) {


        if(chapterAnswerDto ==null ||StringUtils.isEmpty(chapterAnswerDto.surveys_next_chapter)){
            log.info("参数错误");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();

        }
        if(StringUtils.isEmpty(chapterAnswerDto.response_id)){
            log.info("应答用户匹配失败!response_id "+chapterAnswerDto.response_id);
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"应答id错误!！").toJson();
        }
       ResponseInfo responseInfo = responseMapper.getResponsesById(chapterAnswerDto.response_id);
        if(responseInfo==null){
            log.info("应答不存在");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"应答不存在！").toJson();
        }
        String surveys_id = chapterAnswerDto.surveys_id;
        String surveys_next_chapter = chapterAnswerDto.surveys_next_chapter;
        log.info("根据问卷编号："+surveys_id+",下一问题章节id："+surveys_next_chapter+"获取下一问题及话术内容");
        if(StringUtils.isEmpty(surveys_next_chapter)){
            log.info("参数错误surveys_next_chapter："+surveys_next_chapter);
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }
        Document doc = new Document();

        try{
            SurveysChapterDto dto = surveysMapper.getSurveysChapterById(surveys_next_chapter);
            if (dto ==null || StringUtils.isEmpty(dto.question_id)){
                log.info("数据格式错误surveys_next_chapter"+surveys_next_chapter);
                return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
            }
            int end_chapter = dto.is_end_chapter;
            String surveyType = dto.surveys_type;
            //非第一个问卷最后一题，均需要判断下一个方案选择，根据最后一次第二个问卷的选择判断
            if(end_chapter==1 && !surveyType.equals(Constants.SURVEYS_01)){
                 String user_id = responseInfo.user_id;
                 String surveys_no  = surveysMapper.getSurveysChapter(user_id);
                 if(!StringUtils.isEmpty(surveys_no)&&surveys_no.equals(Constants.SURVEYS_PLAN_NO_1)){
                     //TODO 增加问卷选择题答案 recommend[]
                     doc.append("next_plan_select",1);
                 }else if(!StringUtils.isEmpty(surveys_no)&&surveys_no.equals(Constants.SURVEYS_PLAN_NO_2)){
                     doc.append("next_plan_select",2);
                 }else{
                     doc.append("next_plan_select",3);
                 }
            }
            String question_id = "";

            question_id = dto.question_id;
            doc.append("response_id",chapterAnswerDto.response_id);
            doc.append("surveys_no",dto.surveys_no);
            doc.append("surveys_id",dto.surveys_id);
            doc.append("question_id",dto.question_id);
            doc.append("question_no",dto.question_no);
            doc.append("question_type",dto.question_type);
            if(Constants.SELECT_SURVEYS.equals(dto.surveys_no)&&Constants.CHAPTER_ID.equals(surveys_next_chapter)){
                Integer  option_seq = responseMapper.getOptionResponse(Constants.SELECT_CHAPTER,chapterAnswerDto.response_id);
                doc.append("S8_chapter_option",option_seq);
            }
            if(!StringUtils.isEmpty(dto.question_no)){
                if(dto.question_no.equals(Constants.AUTO_QUESTION_Q74)){
                    doc.append("auto_message_url",Constants.AUTO_MESSAGE_INTERVIEW);
                }
                if(dto.question_no.equals(Constants.AUTO_QUESTION_Q75)){
                    doc.append("auto_message_url",Constants.AUTO_MESSAGE_BODYACT);
                }
                if(dto.question_no.equals(Constants.AUTO_QUESTION_Q19)){
                    doc.append("auto_message_url",Constants.AUTO_MESSAGE_AUTHORITY);
                }
            }
            doc.append("question_content",dto.question_content);
            doc.append("question_url",dto.question_url);
            doc.append("question_path",dto.question_path);
            doc.append("start_time",GetformatData.getformatData());
            doc.append("is_end_chapter",dto.is_end_chapter);
            doc.append("surveys_next_chapter",dto.surveys_next_chapter);
            doc.append("next_surveys",dto.next_surveys);
            doc.append("surveys_chapter_id",dto.surveys_chapter_id);

            List<SurveysOptionDto> optionsList = surveysMapper.getSurveysOptionList(surveys_next_chapter);
            List<Document> optionList = new ArrayList<>();
            if(optionsList!=null && optionsList.size()>0){
                for(SurveysOptionDto surveysoptionDto :optionsList){
                    Document option =new Document();
                    SurveysOptionDto opt = surveysMapper.getSurveysOption(surveys_next_chapter,surveysoptionDto.option_id);
                    option.append("option_id",surveysoptionDto.option_id);
                    option.append("option_no",surveysoptionDto.option_no);
                    option.append("option_type",surveysoptionDto.option_type);
                    option.append("option_content",surveysoptionDto.option_content);
                    option.append("option_url",surveysoptionDto.option_url);
                    option.append("next_question","");
                    option.append("surveys_option_id","");
                    option.append("option_next_surveys","");
                    if(opt!=null){
                        option.append("next_question",opt.next_question);
                        option.append("surveys_option_id",opt.surveys_option_id);
                        option.append("option_next_surveys",opt.option_next_surveys);
                    }
                    option.append("option_answer",surveysoptionDto.option_answer);
                    option.append("option_answer_url",surveysoptionDto.option_answer_url);
                    option.append("option_seq",surveysoptionDto.option_seq);
                    optionList.add(option);
                }
            }
            doc.append("optionList",optionList);
            List<Document> frontMessages = surveysService.getSurveysMessageById(question_id,Constants.Message_local_01);
            List<Document> backMessages = surveysService.getSurveysMessageById(question_id,Constants.Message_local_02);

            doc.append("frontMessages",frontMessages);
            doc.append("backMessages",backMessages);

        }catch (Exception e){
            log.info("系统错误,异常信息:"+e);
            return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR,"系统错误!"+e).toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("查询成功,查询信息："+doc.toJson());
        return ret.append("data", doc).toJson();
    }

    /**
     * 保存问题以及话术
     * @param dto
     * @return
     */
    @Transactional
    public String saveQuestionInfo(ChapterAnswerDto dto){

        Document res = new Document();
        List<OptionAnswerDto>   optionAnswerList = new ArrayList<>();
        List<MessageAnswerDto>  frontMessages  = new ArrayList<>();
        List<MessageAnswerDto>  backMessages  = new ArrayList<>();
        if(dto==null || StringUtils.isEmpty(dto.surveys_chapter_id)||StringUtils.isEmpty(dto.response_id)){
            log.info("参数不正确"+dto.response_id);
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数不正确，保存失败！").toJson();
        }
       // SurveysChapterDto chapterDto = surveysMapper.getSurveysChapterByDto(dto);

        SurveysChapterDto surveysChapterDto = surveysMapper.getSurveysChapterById(dto.surveys_chapter_id);
        if(surveysChapterDto==null){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"问卷章节不存在，保存失败！").toJson();
        }
        try{
            ResponseInfo resInfo = new ResponseInfo();
            if(StringUtils.isEmpty(dto.response_id)){
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"应答不存在，保存失败！").toJson();
            }else{
                resInfo = responseMapper.getResponsesById(dto.response_id);
            }
            if(resInfo==null){
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数不正确，保存失败！").toJson();
            }
            String response_id =resInfo.response_id;
            String chapter_answer_id;
            //判断是否经过保存
            //章节应答id
            ChapterAnswer chapter = new ChapterAnswer();
            chapter = responseMapper.getChapterAnswerByInfo(dto);
            if(chapter!=null){
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"章节已存在，保存失败！").toJson();
            }
            ChapterAnswer chapterAnswer = new ChapterAnswer();
            if(StringUtils.isEmpty(dto.chapter_answer_id)){//未保存
                chapterAnswer.chapter_answer_id=GetUUID32.getUUID32();
                chapterAnswer.start_time = dto.start_time;
                chapterAnswer.end_time = GetformatData.getformatData();
                chapterAnswer.surveys_chapter_id =dto.surveys_chapter_id;
                chapterAnswer.response_id =response_id;
                responseMapper.insertChapter(chapterAnswer);
            }else{
                chapterAnswer = responseMapper.getChapterAnswer(dto.chapter_answer_id);
            }
            chapter_answer_id=chapterAnswer.chapter_answer_id;
            //判断当前章节是否为最后章节
            if(!StringUtils.isEmpty(surveysChapterDto.is_end_chapter) && (surveysChapterDto.is_end_chapter== Constants.IS_END_CHAPTER_1)){
                resInfo.is_end=1;
                resInfo.end_time =GetformatData.getformatData();
                resInfo.update_time = GetformatData.getformatData();
                responseMapper.updateResponseInfo(resInfo);
            }
            optionAnswerList = dto.optionAnswerList;

            if(optionAnswerList !=null && optionAnswerList.size()>0){
                responseMapper.delOptionAnswers(chapter_answer_id);//删除老的选项答案
                for(OptionAnswerDto optionAnswerDto:optionAnswerList){
                    OptionAnswer optionAnswer = new OptionAnswer();
                    optionAnswer.chapter_answer_id=chapter_answer_id;
                    optionAnswer.option_answer_id = GetUUID32.getUUID32();
                    optionAnswer.end_time =GetformatData.getformatData();
                    optionAnswer.option_id = optionAnswerDto.option_id;
                    optionAnswer.option_answer =optionAnswerDto.option_answer;
                    optionAnswer.option_answer_url =optionAnswerDto.option_answer_url;
                    optionAnswer.option_answer_seq = optionAnswerDto.option_seq;
                    responseMapper.setOptionAnswer(optionAnswer);
                }
            }
            frontMessages =dto.frontMessages;
            if(frontMessages!=null && frontMessages.size()>0){
                Boolean f =this.saveMessageAnswer(frontMessages,chapter_answer_id,Constants.Message_local_01);
                if(f){
                    log.info("应答前置话术保存成功！");
                }
            }

            backMessages = dto.backMessages;
            if(backMessages !=null && backMessages.size()>0){
                Boolean b = this.saveMessageAnswer(backMessages,chapter_answer_id,Constants.Message_local_02);
                if(b){
                    log.info("应答后置话术保存成功！");
                }
            }

        }catch (Exception e){
            log.info("系统错误，错误信息为："+e);
            return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR,"系统错误！"+e).toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        return ret.append("data", res).toJson();
    }

    /**
     * 保存话术--工具
     * @param frontMessages
     * @return
     */
    public Boolean saveMessageAnswer(List<MessageAnswerDto> frontMessages,String chapter_answer_id,int message_local){
        Boolean res = false;
        log.info("保存应答话术开始");
        try{
           if(frontMessages.size()>0){
               responseMapper.delMessageAnswers(chapter_answer_id);
               for(MessageAnswerDto messageAnswerDto:frontMessages){
                   MessageAnswer messageAnswer = new MessageAnswer();
                   messageAnswer.message_content=messageAnswerDto.message_content;
                   messageAnswer.answer_message_id = GetUUID32.getUUID32();
                   messageAnswer.message_local =message_local;
                   messageAnswer.message_seq =messageAnswerDto.message_seq;
                   messageAnswer.message_type = messageAnswerDto.message_type;
                   messageAnswer.message_url=messageAnswerDto.message_url;
                   messageAnswer.chapter_answer_id = chapter_answer_id;
                   messageAnswer.message_id =messageAnswerDto.message_id;
                   messageAnswer.message_no = messageAnswerDto.message_no;
                   responseMapper.setMessageAnswer(messageAnswer);
               }
               res=true;
           }
       }catch (Exception e){
            log.info("保存应答话术,系统错误："+e);
           e.printStackTrace();
           return false;
       }
       return  res;
    }
    /**
     * 根据问卷id获取全部响应信息
     *  根据问卷id和用户id获取响应信息，
     *  判断是否为null  为空全部获取第一题
     *      判断是否为结束  ：3 获取第一题
     *  若否为未结束获取全部+最后一题
     *  先判断是否为空
     *        为空获取第一题
     *        不为空
     *        判断是否为结束
     *              判断是否为1或2
     *                   获取全部
     *              else  获取第一题
     *        不结束   获取全部和最新一题
     *  判读是否是1或者2 ，若是直接获取全部，若
     *  user_id 用户编号
     *  surveys_type  问卷类型
     *  surveys_id
     * @return
     */
    public String getHisResponseList(ResponseSurveysDto info){
        Document res = new Document();
        if(info==null || StringUtils.isEmpty(info.user_id)){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数不正确！").toJson();
        }
        int bl =  responseMapper.checkUserId(info.user_id);
        if(bl==0){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"当前用户不存在！").toJson();
        }
        String surveys_no = info.surveys_no;
        String user_id= info.user_id;
        SurveysInfo surveysInfo = new SurveysInfo();
        ResponseSurveysDto responseSurveysDto = null;
        if (!StringUtils.isEmpty(surveys_no)){
            surveysInfo = surveysMapper.getSurveysByNo(surveys_no);
            if(surveysInfo==null){
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"问卷不存在！").toJson();
            }
            info.surveys_id =surveysInfo.surveys_id;
            responseSurveysDto =responseMapper.getResponsesByInfo(info);
        }else{ //问卷编号为空获取当前用户下最后一条未结束响应信息
            responseSurveysDto =responseMapper.getResponsesByUser(info);
            if (responseSurveysDto!=null){
                surveysInfo = surveysMapper.getSurveysById(responseSurveysDto.surveys_id);
            }else{
                surveysInfo = surveysMapper.getSurveysByNo("S1");
            }
        }
        if(surveysInfo==null){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"问卷不存在！").toJson();
        }
        try{
            if(responseSurveysDto==null){//h获取问卷第一题
                ResponseInfo resInfo = new ResponseInfo();
                String uuid = GetUUID32.getUUID32();
                resInfo.create_user = info.user_id;
                resInfo.response_id = uuid;
                resInfo.surveys_no = surveysInfo.surveys_no;
                resInfo.surveys_id= surveysInfo.surveys_id;
                resInfo.user_id = info.user_id;
                resInfo.create_time =  GetformatData.getformatData();
                resInfo.start_time =   GetformatData.getformatData();
                res.append("response_id",resInfo.response_id);
                res.append("start_time",resInfo.start_time);
                responseMapper.setResponseInfo(resInfo);
                Document  next_ques = surveysService.getSurveysQuestionBySeq(surveysInfo.surveys_id,"",user_id);
                next_ques.append("response_id",resInfo.response_id);
                res.append("new_question",next_ques);
            }else{
                Integer is_end = responseSurveysDto.is_end;
                if(is_end==Constants.IS_END_CHAPTER_1 && !(Constants.SURVEYS_03).equals(responseSurveysDto.surveys_type)){
                    Document  next_ques = surveysService.getSurveysQuestionBySeq(responseSurveysDto.next_surveys,"",user_id);
                    next_ques.append("response_id",responseSurveysDto.response_id);
                    res.append("new_question",next_ques);
                }
            }
            res.append("user_id",info.user_id);
            List<ResponseSurveysDto> responseList =  responseMapper.getResponseListByUser(info);
            int is_end= responseSurveysDto.is_end;
            int nextQueSeq =1;
            // 下一章节id
            String next_chapter_id = "";
            if(responseList!=null && responseList.size()>0){
                List<Document> hisResList = new ArrayList<>();
                for(ResponseSurveysDto dto:responseList){
                    Document hisRes = new Document();
                    hisRes.append("surveys_id",surveysInfo.surveys_id);
                    hisRes.append("surveys_no",surveysInfo.surveys_no);
                    hisRes.append("user_id",info.user_id);
                    hisRes.append("surveys_type",surveysInfo.surveys_type);
                    hisRes.append("survey" +
                            "_name",surveysInfo.surveys_name);
                    hisRes.append("start_time",dto.start_time);
                    hisRes.append("end_time",responseSurveysDto.end_time);
                    hisRes.append("response_id",responseSurveysDto.response_id);
                    //获取应答章节  （含选项）
                    List<ChapterAnswerDto> chapterAnswers = responseMapper.getChapterAnswerDtos(dto.response_id);
                    if(chapterAnswers!=null && chapterAnswers.size()>0){
                        List<Document> hisList = new ArrayList<Document>();
                        for(ChapterAnswerDto chapterAnswerDto : chapterAnswers){
                            Document chapter = new Document();
                            List<Document> optionList = new ArrayList<Document>();
                            List<OptionAnswerDto> optionAnswers =responseMapper.getOptionAnswers(chapterAnswerDto.chapter_answer_id);
                            for(OptionAnswerDto optionAnswerDto:optionAnswers){
                                Document option = new Document();
                                option.append("option_answer_id",optionAnswerDto.option_answer_id);
                                option.append("chapter_answer_id",optionAnswerDto.chapter_answer_id);
                                option.append("option_id",optionAnswerDto.option_id);
                                option.append("option_answer",optionAnswerDto.option_answer);
                                option.append("option_answer_url",optionAnswerDto.option_answer_url);
                                option.append("end_time",optionAnswerDto.end_time);
                                option.append("start_time",optionAnswerDto.start_time);
                                if(!StringUtils.isEmpty(optionAnswerDto.next_chapter_id)){
                                    next_chapter_id = optionAnswerDto.next_chapter_id;
                                }
                                optionList.add(option);
                            }
                            chapter.append("chapter_answer_id",chapterAnswerDto.chapter_answer_id);
                            chapter.append("surveys_chapter_id",chapterAnswerDto.surveys_chapter_id);
                            chapter.append("surveys_chapter_seq",chapterAnswerDto.surveys_chapter_seq);
                            chapter.append("question_content",chapterAnswerDto.question_content);
                            chapter.append("question_type",chapterAnswerDto.question_type);
                            chapter.append("question_url",chapterAnswerDto.question_url);
                            chapter.append("optionList",optionList);
                            List<Document>  answerFrontMessages =  surveysService.getMessageAnswersById(chapterAnswerDto.chapter_answer_id,Constants.Message_local_01);
                            List<Document>  answerBackMessages =  surveysService.getMessageAnswersById(chapterAnswerDto.chapter_answer_id,Constants.Message_local_02);
                            chapter.append("answerFrontMessages",answerFrontMessages);
                            chapter.append("answerBackMessages",answerBackMessages);
                            if(dto.is_end==0 && nextQueSeq <= chapterAnswerDto.surveys_chapter_seq){
                                nextQueSeq =chapterAnswerDto.surveys_chapter_seq;
                                if(!StringUtils.isEmpty(chapterAnswerDto.surveys_next_chapter)){
                                    next_chapter_id =chapterAnswerDto.surveys_next_chapter;
                                    is_end = chapterAnswerDto.is_end_chapter;
                                }
                            }
                            hisList.add(chapter);
                        }
                        hisRes.append("hisList",hisList);
                        hisResList.add(hisRes);
                        res.append("his_Response",hisResList);
                    }
                }
                String surveys_id = surveysInfo.surveys_id;
                if(is_end==0){//没结束
                    Document firstDoc=  surveysService.getSurveysQuestionBySeq(surveys_id,next_chapter_id,user_id);
                    firstDoc.append("response_id",responseSurveysDto.response_id);
                    firstDoc.append("surveys_id",responseSurveysDto.surveys_id);
                    res.append("new_question",firstDoc);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        return ret.append("data", res).toJson();
    }

    /**
     *   入参为user_id
     *   获取应答历史
     *    若应答不为空：
     *         获取所有应答
     *              判断最后一条应答是否结束
     *                    未结束，获取应答下一题
     *                    已结束：获取应答列表
     *   若应答为空
     *         获取问卷S1第1题
     *
     *   入参为user_id 和 surveys_no
     *      判断是否含有应答
     *      应答不为空
     *           判断应答最后一题是否结束
     *                 未结束，获取最后一题下一题
     *                 已结束，获取所有历史列表并获取，当前问卷第一题
     *      应答为空  返回空
     *        获取当前问卷第一题
     *   总结：根据入参user_id 获取所有应答作为历史
     *
     *         根据应答结果判断第一题内容
     *
     * @param info
     * @return
     */
    public String getHisResponse(ResponseSurveysDto info){
        Document res = new Document();
        if(info==null || StringUtils.isEmpty(info.user_id)){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数不正确！").toJson();
        }
        //判断用户是否存在
        int bl =  responseMapper.checkUserId(info.user_id);
        if(bl==0){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"当前用户不存在！").toJson();
        }
        String surveys_no = info.surveys_no;
        String user_id= info.user_id;
        SurveysInfo surveysInfo = new SurveysInfo();
        ResponseSurveysDto responseSurveysDto = null;
        //问卷不为空
        if (!StringUtils.isEmpty(surveys_no)){
            surveysInfo = surveysMapper.getSurveysByNo(surveys_no);
            if(surveysInfo==null){
                return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"问卷不存在！").toJson();
            }
            info.surveys_id =surveysInfo.surveys_id;
            responseSurveysDto =responseMapper.getResponsesByInfo(info);
        }else{ //问卷编号为空获取当前用户下最后一条未结束响应信息
            responseSurveysDto =responseMapper.getResponsesByUser(info);
            if (responseSurveysDto!=null){
                surveysInfo = surveysMapper.getSurveysById(responseSurveysDto.surveys_id);
            }else{
                surveysInfo = surveysMapper.getSurveysByNo("S1");
            }
        }
        if(surveysInfo==null){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"问卷不存在！").toJson();
        }
        try{
            if(responseSurveysDto==null||responseSurveysDto.is_end==1){//h获取问卷第一题
                responseSurveysDto =null;
                ResponseInfo resInfo = new ResponseInfo();
                String uuid = GetUUID32.getUUID32();
                resInfo.create_user = info.user_id;
                resInfo.response_id = uuid;
                resInfo.surveys_no = surveysInfo.surveys_no;
                resInfo.surveys_id= surveysInfo.surveys_id;
                resInfo.user_id = info.user_id;
                resInfo.create_time =  GetformatData.getformatData();
                resInfo.start_time =   GetformatData.getformatData();
                res.append("response_id",resInfo.response_id);
                res.append("start_time",resInfo.start_time);
                responseMapper.setResponseInfo(resInfo);
                Document  next_ques = surveysService.getSurveysQuestionBySeq(surveysInfo.surveys_id,"",user_id);
                next_ques.append("response_id",resInfo.response_id);
                res.append("new_question",next_ques);
            }
           /* else{
                Integer is_end = responseSurveysDto.is_end;
                if(is_end==Constants.IS_END_CHAPTER_1 && !(Constants.SURVEYS_03).equals(responseSurveysDto.surveys_type)){
                    Document  next_ques = surveysService.getSurveysQuestionBySeq(responseSurveysDto.next_surveys,"",user_id);
                    next_ques.append("response_id",responseSurveysDto.response_id);
                    res.append("new_question",next_ques);
                }
            }*/
            res.append("surveys_id",surveysInfo.surveys_id);
            res.append("surveys_no",surveysInfo.surveys_no);
            res.append("user_id",info.user_id);
            res.append("surveys_type",surveysInfo.surveys_type);
            res.append("surveys_name",surveysInfo.surveys_name);
            String surveys_id = surveysInfo.surveys_id;
            if(responseSurveysDto !=null){
                //无需区分，直接获取
               if( responseSurveysDto.is_end==1){
                    Document  next_ques  = surveysService.getSurveysQuestionBySeq(surveys_id,"",user_id);
                    next_ques.append("response_id",responseSurveysDto.response_id);
                    next_ques.append("surveys_id",responseSurveysDto.surveys_id);
                    res.append("new_question",next_ques);
                }
                int is_end= responseSurveysDto.is_end;
                int nextQueSeq =1;
                // 下一章节id
                String next_chapter_id = "";
                Document hisRes = new Document();
                hisRes.append("user_id",responseSurveysDto.user_id);
                hisRes.append("start_time",responseSurveysDto.start_time);
                hisRes.append("end_time",responseSurveysDto.end_time);
                hisRes.append("response_id",responseSurveysDto.response_id);

                //获取应答章节  （含选项）
                List<ChapterAnswerDto> chapterAnswers = responseMapper.getChapterAnswerDtos(responseSurveysDto.response_id);
                if(chapterAnswers!=null && chapterAnswers.size()>0){
                    List<Document> hisList = new ArrayList<Document>();
                    for(ChapterAnswerDto chapterAnswerDto : chapterAnswers){

                        if(nextQueSeq <= chapterAnswerDto.surveys_chapter_seq){
                            nextQueSeq =chapterAnswerDto.surveys_chapter_seq;
                            if(!StringUtils.isEmpty(chapterAnswerDto.surveys_next_chapter)){
                                next_chapter_id =chapterAnswerDto.surveys_next_chapter;
                            }
                            is_end = chapterAnswerDto.is_end_chapter;
                        }
                        Document chapter = new Document();
                        List<Document> optionList = new ArrayList<Document>();
                        List<OptionAnswerDto> optionAnswers =responseMapper.getOptionAnswers(chapterAnswerDto.chapter_answer_id);
                        for(OptionAnswerDto optionAnswerDto:optionAnswers){
                            Document option = new Document();
                            SurveysOptionDto opt = surveysMapper.getSurveysOption(chapterAnswerDto.surveys_chapter_id,optionAnswerDto.option_id);
                            option.append("option_answer_id",optionAnswerDto.option_answer_id);
                            option.append("chapter_answer_id",optionAnswerDto.chapter_answer_id);
                            option.append("option_id",optionAnswerDto.option_id);
                            option.append("option_answer",optionAnswerDto.option_answer);
                            option.append("option_answer_url",optionAnswerDto.option_answer_url);
                            option.append("end_time",optionAnswerDto.end_time);
                            option.append("start_time",optionAnswerDto.start_time);
                            if(opt!=null&&!StringUtils.isEmpty(opt.next_question)){
                                next_chapter_id = opt.next_question;
                            }
                            optionList.add(option);
                        }
                        chapter.append("chapter_answer_id",chapterAnswerDto.chapter_answer_id);
                        chapter.append("surveys_chapter_id",chapterAnswerDto.surveys_chapter_id);
                        chapter.append("surveys_chapter_seq",chapterAnswerDto.surveys_chapter_seq);
                        chapter.append("question_content",chapterAnswerDto.question_content);
                        chapter.append("question_type",chapterAnswerDto.question_type);
                        chapter.append("question_url",chapterAnswerDto.question_url);
                        chapter.append("optionList",optionList);
                        List<Document>  answerFrontMessages =  surveysService.getMessageAnswersById(chapterAnswerDto.chapter_answer_id,Constants.Message_local_01);
                        List<Document>  answerBackMessages =  surveysService.getMessageAnswersById(chapterAnswerDto.chapter_answer_id,Constants.Message_local_02);
                        chapter.append("answerFrontMessages",answerFrontMessages);
                        chapter.append("answerBackMessages",answerBackMessages);
                        hisList.add(chapter);
                    }
                    hisRes.append("hisList",hisList);
                    res.append("his_Response",hisRes);
                }

                if(is_end==0){
                    Document firstDoc=  surveysService.getSurveysQuestionBySeq(surveys_id,next_chapter_id,user_id);
                    firstDoc.append("response_id",responseSurveysDto.response_id);
                    firstDoc.append("surveys_id",responseSurveysDto.surveys_id);
                    if(Constants.SELECT_SURVEYS.equals(responseSurveysDto.surveys_no)&&Constants.CHAPTER_ID.equals(next_chapter_id)){
                      Integer  option_seq = responseMapper.getOptionResponse(Constants.SELECT_CHAPTER,responseSurveysDto.response_id);
                        firstDoc.append("S8_chapter_option",option_seq);
                    }
                    res.append("new_question",firstDoc);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        return ret.append("data", res).toJson();
    }

    /**
     *   根据用户id删除应答数据
     * @param info
     * @return
     */
    @Transactional
    public String  delAllResponse(ChapterAnswerDto info){
        if(info==null || StringUtils.isEmpty(info.user_id)){
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"用户不正确！").toJson();
        }
        Document doc = new Document();
        try{
            int mes =   surveysMapper.deleteMessageinfo(info.user_id);
            int opt =0;
            int cap = 0;
            int res =0;
            doc.append("删除话术个数：",mes);
            opt =   surveysMapper.deleteOptioninfo(info.user_id);
            doc.append("删除选项个数：",opt);
            cap =   surveysMapper.deleteChapterinfo(info.user_id);
            doc.append("删除章节个数：",cap);
            if(cap>0){
                res =  surveysMapper.deleteResonseinfo(info.user_id);
                doc.append("删除应答个数：",res);
            }
            if (res>0){
                doc.append("result","删除成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"ok");
        return ret.append("data", doc).toJson();
    }

    //增加获取判断，保存时判断


}
