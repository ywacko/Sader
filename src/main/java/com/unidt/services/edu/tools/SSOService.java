package com.unidt.services.edu.tools;

import com.unidt.helper.common.Constants;
import com.unidt.helper.common.ReturnResult;
import com.unidt.mybatis.bean.SSO;
import com.unidt.mybatis.mapper.SSOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SSOService {

    @Autowired
    SSOMapper ssoMapper;

    public String tokenCheck(String token){
        if (token == null) return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "token无效").toJson();
        SSO sso = ssoMapper.getSSOByToken(token);
        if (sso == null){
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "token无效").toJson();
        } else if (Long.valueOf(sso.expire_date) >= new Date().getTime()) {
            return "True";
        } else {
            return ReturnResult.createResult(Constants.API_TOKEN_DEADLINE, "token过期").toJson();
        }
    }

    /**
     *  验证token
     *  根据token获取user_id
     *  ysc 2018-10-16
     * @param token
     * @return
     */
    public Map getUserByToken(String token){
        String user ="";
        Map tokenMap= new HashMap<>();
        tokenMap.put("token",false);
        if(!StringUtils.isEmpty(token)){
            SSO  sso =   ssoMapper.getSSOByToken(token);
            if(sso ==null){
                tokenMap.put("token",false);
                return   tokenMap;
            }
            user = sso.user_id;
            String expire_date=sso.expire_date;
            if(!StringUtils.isEmpty(expire_date)){
                Date now_date = new Date();
                long time = now_date.getTime();
                Calendar calendar = Calendar.getInstance();
                //延后两个小时
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2);
                Date endTime = calendar.getTime();
                long newDateline = endTime.getTime();
                String dateline = time + "";
                //dateline = dateline.substring(0, 10);
                Long now_num = Long.valueOf(dateline);
                Long expire_num = Long.valueOf(expire_date);
                SSO  info = new SSO();
                info.user_id = sso.user_id;
                info.expire_date = newDateline+"";
                if(now_num<expire_num){
                    tokenMap.put("token",true);
                    ssoMapper.updateDeadline(info);
                }
            }
            tokenMap.put("user",user);
        }
        return tokenMap;
    }
}
