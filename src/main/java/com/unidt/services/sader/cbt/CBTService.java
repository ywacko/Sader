package com.unidt.services.sader.cbt;

import com.unidt.helper.common.Constants;
import com.unidt.helper.common.GetUUID32;
import com.unidt.helper.common.GetformatData;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.question.CbtUser;
import com.unidt.mybatis.dto.CbtUserDto;

import com.unidt.mybatis.mapper.sader.CbtMapper;
import  org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理cbt增删改查数据
 */
@Service
public class CBTService {

    private static Logger log = LoggerFactory.getLogger(CBTService.class);
    @Autowired
    CbtMapper cbtMapper;

    /**
     * 根据用户获取cbt。列表
     * @param dto
     * @return
     */
    public String getCbtList(CbtUserDto dto){
        if (dto ==null || StringUtils.isEmpty(dto.user_id)){
            log.info("参数错误");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }
        String user_id = dto.user_id;
        List<Document> resList= new ArrayList<Document>();
        List<CbtUser> cbtuserList = new ArrayList<>();
        log.info("CBT列表查询开始:user_id："+user_id);
        try{
            cbtuserList = cbtMapper.getCbtUserList(user_id);
            if(cbtuserList.size()>0){
                for(CbtUser cbt:cbtuserList){
                    Document doc = new Document();
                     doc.append("cbt_id",cbt.cbt_id)
                         .append("response_id",cbt.response_id)
                        .append("cbt_action",cbt.cbt_action)
                        .append("cbt_mood",cbt.cbt_mood)
                        .append("cbt_name",cbt.cbt_name)
                        .append("cbt_name_select",cbt.cbt_name_select)
                        .append("cbt_phy",cbt.cbt_phy)
                        .append("cbt_think",cbt.cbt_think)
                         .append("user_id",cbt.create_user)
                        .append("create_user",cbt.create_user)
                        .append("update_num",cbt.update_num);
                        if(cbt.create_time!=null){
                            doc.append("create_time",cbt.create_time.substring(0,7));
                        }else{
                            doc.append("create_time",cbt.create_time);
                        }
                        if(cbt.update_time!=null){
                            doc.append("update_time",cbt.update_time.substring(0,7));
                        }else{
                            doc.append("update_time",cbt.update_time);
                        }
                     resList.add(doc);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"系统错误！").toJson();
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        log.info("查询成功:"+ret.append("data", resList).toJson());

        return ret.append("data", resList).toJson();
    }

    /**
     * 判断id是否存在，不存在及新增，存在即更新
     * @param dto
     * @return
     */
    public String  saveCbtInfo(CbtUserDto dto){

        Document res=new Document();
        if(dto==null){
            log.info("参数错误");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }
        log.info("保存cbtuser数据 dto:"+dto);

        /*if(StringUtils.isEmpty(dto.response_id)){
            log.info("应答id为空");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }*/
        CbtUser info = new CbtUser();
        try{
            info.cbt_action =dto.cbt_action==null?"":dto.cbt_action;
            info.cbt_mood = dto.cbt_mood == null?"":dto.cbt_mood;
            info.cbt_name = dto.cbt_name== null?"":dto.cbt_name;
            info.cbt_name_select = dto.cbt_name_select ==null?"":dto.cbt_name_select;
            info.cbt_phy = dto.cbt_phy== null?"":dto.cbt_phy;
            info.cbt_think = dto.cbt_think== null?"":dto.cbt_think;
            info.response_id = dto.response_id==null?"":dto.response_id;
            info.create_user = dto.user_id == null?"":dto.user_id;
            info.del_flag =Constants.DEL_FLAG_0;
            Integer update_num = null;
            if(!StringUtils.isEmpty(dto.cbt_id)){
                info.create_time = GetformatData.getformatData();
                info.cbt_id = dto.cbt_id;
                update_num = cbtMapper.getUpdateNum(dto.cbt_id);
            }
            if(update_num!=null){
                info.update_num = update_num+1;
                info.update_time= GetformatData.getformatData();
                cbtMapper.updateCbtUser(info);
            }else{
                String uuid = GetUUID32.getUUID32();
                info.cbt_id=uuid;
                cbtMapper.insertCbtUser(info);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("系统错误："+e);
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        return ret.append("data", res).toJson();
    }

    /**
     * 根据id更新
     * @param dto
     * @return
     */
    public String  editCbtInfo(CbtUserDto dto){

        Document res=new Document();
        if(dto==null){
            log.info("参数错误");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }
        log.info("保存cbtuser数据 dto:"+dto);

        if(StringUtils.isEmpty(dto.cbt_id)){
            log.info("应答id为空");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }
        CbtUser info = new CbtUser();
        try{
            info.cbt_action =dto.cbt_action==null?"":dto.cbt_action;
            info.cbt_mood = dto.cbt_mood == null?"":dto.cbt_mood;
            info.cbt_name = dto.cbt_name== null?"":dto.cbt_name;
            info.cbt_name_select = dto.cbt_name_select ==null?"":dto.cbt_name;
            info.cbt_phy = dto.cbt_phy== null?"":dto.cbt_phy;
            info.cbt_think = dto.cbt_think== null?"":dto.cbt_think;
            info.create_user = dto.user_id;
            info.response_id = dto.response_id==null?"":dto.response_id;
            info.update_time = GetformatData.getformatData();
            info.del_flag =Constants.DEL_FLAG_0;
            Integer update_num = null;
            info.cbt_id = dto.cbt_id;
            update_num = cbtMapper.getUpdateNum(dto.cbt_id);
            info.update_num = update_num+1;
            cbtMapper.updateCbtUser(info);
        }catch (Exception e){
            e.printStackTrace();
            log.info("系统错误："+e);
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        return ret.append("data", res).toJson();
    }

    /**
     * 根据id获取信息
     * @param dto
     * @return
     */
    public String  getCbtUser(CbtUserDto dto){
        if (dto ==null || StringUtils.isEmpty(dto.cbt_id)){
            log.info("参数错误");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }
        Document res=new Document();
        try{

            String cbt_id = dto.cbt_id;
            log.info("查询cbt详情 cbt_id:"+cbt_id);
            if(StringUtils.isEmpty(cbt_id)){
                log.info("参数错误 cbt_id:"+cbt_id);
                return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
            }
            CbtUser info = cbtMapper.getCbtUserById(cbt_id);
            if(info!=null){
                res.append("cbt_id",info.cbt_id)
                        .append("cbt_think",info.cbt_think)
                        .append("response_id",info.response_id)
                        .append("cbt_phy",info.cbt_phy)
                        .append("cbt_name_select",info.cbt_name_select)
                        .append("cbt_name",info.cbt_name)
                        .append("cbt_mood",info.cbt_mood)
                        .append("cbt_action",info.cbt_action)
                        .append("user_id",info.create_user)
                        .append("update_num",info.update_num);

                        if(info.create_time!=null){
                            res.append("create_time",info.create_time.substring(0,7));
                        }else{
                            res.append("create_time",info.create_time);
                        }
                        if(info.update_time!=null){
                            res.append("update_time",info.update_time.substring(0,7));
                        }else{
                            res.append("update_time",info.update_time);
                        }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("系统错误："+e);
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        return ret.append("data", res).toJson();

    }
    /**
     * 根据id获取信息
     * @param dto
     * @return
     */
    public String  getCbtUserByResponse(CbtUserDto dto){
        if (dto ==null || StringUtils.isEmpty(dto.response_id)){
            log.info("参数错误");
            return  ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"参数错误！").toJson();
        }
        Document res=new Document();
        try{
            String response_id = dto.response_id;
            log.info("查询cbt详情 response_id:"+response_id);
            CbtUser info = cbtMapper.getCbtUserByResponseId(response_id);
            if (info!=null){
                res.append("cbt_id",info.cbt_id)
                        .append("cbt_think",info.cbt_think)
                        .append("cbt_phy",info.cbt_phy)
                        .append("cbt_name",info.cbt_name)
                        .append("cbt_name_select",info.cbt_name_select)
                        .append("response_id",info.response_id)
                        .append("cbt_mood",info.cbt_mood)
                        .append("cbt_action",info.cbt_action)
                        .append("user_id",info.create_user)
                        //.append("create_time",info.create_time.substring(0,7))
                       // .append("update_time",info.update_time.substring(0,7))
                        .append("update_num",info.update_num);
                        if(info.create_time!=null){
                            res.append("create_time",info.create_time.substring(0,7));
                        }else{
                            res.append("create_time",info.create_time);
                        }
                        if(info.update_time!=null){
                            res.append("update_time",info.update_time.substring(0,7));
                        }else{
                            res.append("update_time",info.update_time);
                        }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("系统错误："+e);
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"OK");
        return ret.append("data", res).toJson();

    }

    /**
     * 根据id逻辑删除cbt，
     * @param cbt_id
     * @return
     */
    public String  delCbtInfoById(String cbt_id){

        log.info("逻辑删除 cbt_id:"+cbt_id);
        Document res=new Document();
        try{
            cbtMapper.delCbtUserById(cbt_id);
        }catch (Exception e){
            e.printStackTrace();
            log.info("系统错误："+e);
        }
        Document ret = ReturnResult.createResult(Constants.API_CODE_OK,"删除成功！");
        return ret.append("data", res).toJson();
    }

}
