package com.unidt.api;

import com.mongodb.client.FindIterable;
import com.unidt.bean.crm.CRMBean;
import com.unidt.bean.user.UserBean;
import com.unidt.helper.common.Constants;
import com.unidt.helper.common.ReturnResult;
import com.unidt.helper.common.UserHelper;
import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.interf.IFraDB;
import com.unidt.services.acl.impl.CRM;
import com.unidt.services.acl.proxy.ServiceProxy;
import com.unidt.services.acl.proxy.priv.CRMProxy;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class CRMController {

    //
    // 数据库
    public static IFraDB<Bson, FindIterable<Document>> db   = DBFactory.createMongoDB("test");
    // 日志
    public static Logger                               log  = LoggerFactory.getLogger(CRMController.class);

    @RequestMapping(value = "/crm/add", method = RequestMethod.POST)
    @ResponseBody
    public String addCustomer(@RequestBody CRMBean bean) throws NoSuchMethodException {
        CRMProxy crm = ServiceProxy.createCRMProxy();
        int ret = crm.addCompany(bean);
        log.info("添加客户返回值: " + ret);

        if (ret == Constants.FRA_SUCCESS) {
            return ReturnResult.createResult(Constants.API_CODE_OK, "ok").toJson();
        }else {
            return ReturnResult.createResult(Constants.API_CODE_CORRUPT, "add company error").toJson();
        }
    }

    /**
     * 用户注册
     * @param bean
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody UserBean bean) {
        try {
            UserHelper.addUser(bean);
            return ReturnResult.createResult(Constants.API_CODE_OK,"ok").toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR,"error").toJson();
        }
    }

    /**
     * 删除公司
     * @param bean
     * @return
     */
    @RequestMapping(value = "/custom/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCustom(@RequestBody UserBean bean) throws NoSuchMethodException {
        int ret = ServiceProxy.createCRMProxy().deleteCompany(bean);
        if (ret == 0) {
            log.error("无匹配数据，未删除");
            return ReturnResult.createResult(Constants.API_CODE_NOT_FOUND, "ok").toJson();
        }
        return ReturnResult.createResult(Constants.API_CODE_OK,"ok").toJson();
    }

    /**
     * 删除账号
     * @param bean
     * @return
     */
    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteAccount(@RequestBody UserBean bean) throws NoSuchMethodException {

        int ret = ServiceProxy.createCRMProxy().deleteAccount(bean);

        if (ret == 0) {
            log.error("无匹配数据，未删除");
            return ReturnResult.createResult(Constants.API_CODE_NOT_FOUND, "404 not found").toJson();
        }
        return ReturnResult.createResult(Constants.API_CODE_OK,"ok").toJson();
    }
}
