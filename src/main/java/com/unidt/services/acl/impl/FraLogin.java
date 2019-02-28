package com.unidt.services.acl.impl;

import com.mongodb.client.FindIterable;
import com.unidt.helper.common.UserHelper;
import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.interf.IFraDB;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class FraLogin {

    //
    // 数据库
    public static  IFraDB<Bson, FindIterable<Document>> db   = DBFactory.createMongoDB();

    private static  Logger log = LoggerFactory.getLogger(FraLogin.class);

    private static FraLogin instance = new FraLogin();

    public static FraLogin getInstance() {
        return instance;
    }

    /**
     * 使用userID登录
     * @param userID
     * @param pwd
     * @return
     * @throws Exception
     */
    public Document loginWithUserID(String userID, String pwd, String product_id) throws Exception {
        log.info("login with user ID:" + userID + " 产品:" + product_id);
        //
        // 首先验证用户名密码
        FindIterable<Document> result = db.collection("t_user").find(and(eq("user_id", userID), eq("pwd", pwd)));
        if (!result.iterator().hasNext()) {
            log.info("登录失败,用户名密码错误");
            return null;
        } else if ( !UserHelper.checkUserProduct(userID,product_id) ) {
            log.info("用户: " + userID + " 未购买过该产品，不允许访问");
            return null;
        } else {
            return result.iterator().next();
        }
    }

    /**
     * 通过邮箱和密码登录
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public Document loginWithEmailAndPwd(String email, String pwd, String product_id) throws Exception {
        log.info("login with user Email:" + email);
        FindIterable<Document> result = db.collection("t_user").find(new Document("email", email).append("pwd", pwd));
        if (!result.iterator().hasNext()) {
            log.info("登录失败,用户名密码错误");
            return null;
        }
        Document ret = result.iterator().next();
        String userID = ret.getString("user_id");
        if (!result.iterator().hasNext()) {
            log.info("登录失败,用户名密码错误");
            return null;
        } else if ( !UserHelper.checkUserProduct(userID,product_id) ) {
            log.info("用户: " + userID + " 未购买过该产品，不允许访问");
            return null;
        } else {
            return result.iterator().next();
        }
    }

    /**
     * 使用电话号码和密码登录
     * @param phone
     * @param pwd
     * @return
     * @throws Exception
     */
    public Document loginWithPhoneAndPwd(String phone, String pwd, String product_id) throws Exception {
        log.info("login with phone:" + phone);
        FindIterable<Document> result = db.collection("t_user").find(new Document("phone", phone).append("pwd", pwd));
        Document ret = result.iterator().next();
        String userID = ret.getString("user_id");
        if (!result.iterator().hasNext()) {
            log.info("登录失败,用户名密码错误");
            return null;
        } else if ( !UserHelper.checkUserProduct(userID,product_id) ) {
            log.info("用户: " + userID + " 未购买过该产品，不允许访问");
            return null;
        } else {
            return result.iterator().next();
        }
    }

}
