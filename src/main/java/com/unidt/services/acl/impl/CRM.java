package com.unidt.services.acl.impl;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.unidt.bean.constraints.CheckToken;
import com.unidt.bean.crm.CRMBean;
import com.unidt.bean.user.UserBean;
import com.unidt.helper.common.Constants;
import com.unidt.helper.common.DateHelper;
import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.interf.IFraDB;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class CRM {

    private static CRM instance = new CRM();
    public static CRM create() {
        return instance;
    }

    //
    // 数据库
    public static IFraDB<Bson, FindIterable<Document>> db   = DBFactory.createMongoDB();
    public Logger   log = LoggerFactory.getLogger(CRM.class);

    /**
     *  添加公司
     * @param bean
     * @return  0 成功; 1 重复
     */
    @CheckToken(true)
    public int addCompany(CRMBean bean) {

        log.info("添加公司:" + bean.company_name);
        String create_time = DateHelper.getDateTime();
        String update_time = create_time;

        if (isCompanyIDValid(bean.company_id)) {
            return Constants.FRA_RECORD_REPEAT;
        }

        db.collection("company_collection").insertOne(
                new Document("company_id",bean.company_id)
                .append("address", bean.address)
                .append("channel", bean.channel)
                .append("company_name", bean.company_name)
                .append("contact_email", bean.contact_email)
                .append("contact_phone", bean.contact_phone)
                .append("contactor", bean.contactor)
                .append("industry_type", bean.industry_type)
                .append("status", bean.status)
                .append("create_time", create_time)
                .append("update_time", update_time)
        );

        return Constants.FRA_SUCCESS;
    }

    /**
     * 删除指定公司
     * @return
     */
    @CheckToken(true)
    public int deleteCompany(UserBean bean){
        String company_id = bean.company_id;

        int ret = (int) db.collection("company_collection")
                .updateOne(eq("company_id", company_id), combine(set("delete",0))).getModifiedCount();
        return ret;
    }

    /**
     * 检查company_id是否唯一
     * @param company_id
     * @return
     */
    public boolean isCompanyIDValid(String company_id) {
        MongoCursor<Document> cursor = db.collection("company_collection").find(eq("company_id", company_id)).iterator();
        return cursor.hasNext();
    }

    /**
     * 修改密码
     * @param bean
     * @return
     */
    @CheckToken(true)
    public int changePwd(UserBean bean) {
        String user_id = bean.user_id,
                oldpwd = bean.oldpwd,
                newpwd = bean.newPwd;
        log.info("user: " + user_id + " 修改密码");
        return (int)(db.collection("t_user").updateMany(and(eq("user_id", user_id), eq("pwd", oldpwd)),
                combine(set("pwd",newpwd))).getModifiedCount());
    }

    /**
     * 删除指定账号
     * @param bean
     * @return
     */
    @CheckToken(true)
    public int deleteAccount(UserBean bean) {
        String user_id = bean.user_id;

        return (int)db.collection("t_user").updateOne(eq("user_id", user_id),
                combine(set("delete",0))).getModifiedCount();
    }

    private CRM(){
        super();
    }
}
