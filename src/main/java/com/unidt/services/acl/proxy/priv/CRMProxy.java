package com.unidt.services.acl.proxy.priv;

import com.unidt.bean.constraints.CheckToken;
import com.unidt.bean.crm.CRMBean;
import com.unidt.bean.user.UserBean;
import com.unidt.helper.common.Constants;
import com.unidt.helper.common.UserHelper;
import com.unidt.helper.interf.IToken;
import com.unidt.services.acl.impl.CRM;

import java.lang.reflect.Method;

public class CRMProxy {
    private CRM crm = CRM.create();
    private static CRMProxy proxy = new CRMProxy();

    private CRMProxy() {
        super();
    }

    public static CRMProxy create() {
        return proxy;
    }

    /**
     * 检查clz类的token 是否在method方法中检查通过
     * @param token
     * @param clz
     * @param method
     * @param args
     * @return
     */
    public static boolean isTokenOK(IToken token, Class<?> clz, String method, Class<?>... args) throws NoSuchMethodException {
        Method md = clz.getDeclaredMethod(method, args);
        // 添加了检查token注解
        if (md.isAnnotationPresent(CheckToken.class)) {
            CheckToken ckt = (CheckToken)md.getAnnotation(CheckToken.class);
            boolean ischeck = ckt.value();
            //
            // 明确要求检查token
            if (ischeck) {
                return UserHelper.checkToken(token);
            }
        }
        return true;
    }

    public int addCompany(CRMBean bean) throws NoSuchMethodException {
        if (isTokenOK(bean,CRM.class, "addCompany", CRMBean.class)) {
            crm.addCompany(bean);
            return Constants.FRA_SUCCESS;
        } else {
            return Constants.FRA_RECORD_REPEAT;
        }
    }
    public int deleteCompany(UserBean bean) throws NoSuchMethodException {
        if (isTokenOK(bean,CRM.class, "deleteCompany", UserBean.class)) {
            return crm.deleteCompany(bean);
        } else {
            return 0;
        }
    }

    public int changePwd(UserBean bean) throws NoSuchMethodException {
        if (isTokenOK(bean,CRM.class, "changePwd", UserBean.class)) {

            return crm.changePwd(bean);
        } else {
            return 0;
        }
    }

    public int deleteAccount(UserBean bean) throws NoSuchMethodException {
        if (isTokenOK(bean,CRM.class, "deleteAccount", UserBean.class)) {

            return crm.deleteAccount(bean);
        } else {
            return 0;
        }
    }


}
