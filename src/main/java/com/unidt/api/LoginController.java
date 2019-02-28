package com.unidt.api;


import com.unidt.bean.login.LogOutBean;
import com.unidt.bean.login.LoginBean;
import com.unidt.bean.user.UserBean;
import com.unidt.helper.common.Constants;
import com.unidt.helper.common.ReturnResult;
import com.unidt.helper.common.UserHelper;
import com.unidt.services.acl.impl.FraLogin;
import com.unidt.services.acl.proxy.ServiceProxy;
import com.unidt.services.acl.proxy.priv.CRMProxy;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@EnableAutoConfiguration
@ComponentScan
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 测试用
     * @return
     */
    @RequestMapping(value="/test")
    public String test() {
        return "Test";
    }

    /**
     * 登录
     * @param loginBean
     * @return
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody LoginBean loginBean) throws Exception {

        FraLogin login = FraLogin.getInstance();
        Document lgRet = login.loginWithUserID(loginBean.getUser_id(), loginBean.getPwd(), loginBean.getProduct_id());
        if (lgRet == null) {
            String rtRst = ReturnResult.createResult(Constants.API_CODE_INNER_ERROR, "auth error").toJson();
            return rtRst;
        }else{
            String token = UserHelper.createTokenByUserInfo(lgRet, loginBean.getProduct_id());
            UserHelper.saveUserToken(loginBean.getUser_id(), loginBean.getProduct_id(),token);
            String rtRst = ReturnResult.createResult(200,"ok").append("token", token).toJson();
            return rtRst;
        }
    }

    /**
     * 退出登录，清除cookie
     * @param out
     */
    @RequestMapping(value="/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logOut(@RequestBody LogOutBean out) {
        String user_id = out.getUser_id()
                ,token = out.getToken()
                ,product_id = out.getProduct_id();

        if ( UserHelper.checkToken(token,product_id)) {
            UserHelper.deleteToken(user_id, product_id);
            log.info("用户： " + user_id + " 退出登录");
            return ReturnResult.createResult(Constants.API_CODE_OK,"ok").toJson();
        }else {
            return ReturnResult.createResult(Constants.API_CODE_INNER_ERROR, "error").toJson();
        }
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    @ResponseBody
    public String passwordChange(@RequestBody UserBean bean) throws NoSuchMethodException {
        log.info("修改密码:" + UserHelper.getUserFromToken(bean.getToken()));
        if (!UserHelper.checkToken(bean)) {
            log.error("token无效");
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN,"error").toJson();
        }

        CRMProxy crm = ServiceProxy.createCRMProxy();
        int ret = crm.changePwd(bean);

        if ( ret == 0 ) {
            return ReturnResult.createResult(Constants.API_CODE_NOT_FOUND,"not changed").toJson();
        }else {
            return ReturnResult.createResult(Constants.API_CODE_OK,"OK").toJson();
        }
    }

    @RequestMapping(value = "/token/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateToken(@RequestBody UserBean bean) {
        if ( !UserHelper.checkToken(bean)) {
            log.error("token无效，无法更新");
            return ReturnResult.createResult(Constants.API_CODE_FORBIDDEN, "error").toJson();
        } else {
            //
            // 生成新的token，返回给客户端
            UUID uuid = UUID.randomUUID();
            Document token = Document.parse(bean.getToken());
            token.put("token", uuid.toString());
            //
            // redis同步更新
            return ReturnResult.createResult(200,"ok", new Document("token", token)).toJson();
        }
    }
}
