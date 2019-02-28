package test;

import com.unidt.bean.user.UserBean;
import com.unidt.services.acl.impl.CRM;
import com.unidt.services.acl.proxy.ServiceProxy;
import com.unidt.services.acl.proxy.priv.CRMProxy;

public class CRMTest {

    public void test() throws NoSuchMethodException {
        CRMProxy crm = ServiceProxy.createCRMProxy();
        UserBean bean = new UserBean();
        bean.company_id = "4";

        int ret = crm.deleteCompany(bean);
        System.out.println(ret);

    }
    public static void main(String[] args) throws NoSuchMethodException {
        new CRMTest().test();
    }
}
