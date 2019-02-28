package com.unidt.helper.common;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.unidt.bean.user.UserBean;
import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.interf.ICache;
import com.unidt.helper.interf.IFraDB;
import com.unidt.helper.interf.IToken;
import com.unidt.services.acl.impl.FraLogin;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class UserHelper {
    //
    // 数据库
    public static  IFraDB<Bson, FindIterable<Document>> db   = DBFactory.createMongoDB();

    public static ICache cache = DBFactory.createRedis();
    private static Logger log = LoggerFactory.getLogger(FraLogin.class);


    /**
     * 根据用户ID获取公司ID
     * @param userID
     * @return
     * @throws Exception
     */
    public static String getCompanyByUserID(String userID) throws Exception {

        MongoCursor<Document> cursor = db.collection("t_user").find(eq("user_id", userID)).iterator();
        if (cursor.hasNext()) {
            return cursor.next().getString("company_id");
        } else {
            return null;
        }
    }

    /**
     * 根据用户ID获取公司ID
     * @return
     * @throws Exception
     */
    public static Map getAllUserAndCompanyID() throws Exception {
        MongoCursor<Document> cursor = db.collection("t_user").find().iterator();
        Map<String, String> result = new HashMap<>();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            result.put(doc.getString("user_id"), doc.getString("company_id"));
        }
        return result;
    }

    /**
     * 根据用户信息，生成该用户的token串
     * @param user
     * @return
     */
    public static String createTokenByUserInfo(Document user, String product_id) throws Exception {
        String role_id = user.getString("role_id");
        //
        // Token中去除敏感信息
        user.remove("pwd");
        user.remove("_id");

        //
        //  根据role_id获取用户权限列表
        FindIterable<Document> menus = db.collection("t_role").find(eq("role_id",role_id));

        if (menus.iterator().hasNext()) {
            Document menu = menus.iterator().next();
            menu.remove("_id");
            menu.remove("role_id");
            user.append("menus", menu);
        }else {
            user.append("menus", null);
        }
        user.append("product_id", product_id);
        String uid = UUID.randomUUID().toString();
        user.append("token", uid);
        return user.toJson();
    }

    @Deprecated
    private static String getKey(String user_id) {
        return user_id + "_token";
    }
    private static String getKey(String user_id, String product_id) {
        return user_id + "_" + product_id + "_token";
    }

    /**
     * 存储用户token
     * @param user_id
     * @param token
     */
    @Deprecated
    public static void saveUserToken(String user_id, String token) {
        cache.set(getKey(user_id), token);
        cache.set(getKey(user_id),3600);
    }
    public static void saveUserToken(String user_id, String product_id, String token) {
        cache.set(getKey(user_id, product_id), token);
        //
        // token两个小时候过期
        cache.expire(getKey(user_id, product_id),3600);
    }

    public static void saveUserToken(String token) {
        String user = getUserFromToken(token);
        String product = getProductIDFromToken(token);
        saveUserToken(user,product,token);
    }
    /**
     * 获取用户token
     * @param user
     * @return
     */

    @Deprecated
    public static String getUserToken(String user) {
        return (String) cache.get(getKey(user));
    }
    public static String getUserToken(String user, String product_id) {
        return (String) cache.get(getKey(user, product_id));
    }

    /**
     * 从token中提取用户id
     * @param token
     * @return
     */
    public static String getUserFromToken(String token) {
        Document doc = Document.parse(token);
        return doc.getString("user_id");
    }

    public static String getCompanyIDFromToken(String token) {
        Document doc = Document.parse(token);
        return doc.getString("company_id");
    }
    public static String getProductIDFromToken(String token) {
        Document doc = Document.parse(token);
        return doc.getString("product_id");
    }

    /**
     * 删除用户token
     * @param user
     */
    @Deprecated
    public static void deleteToken(String user) {
        cache.del(getKey(user));
    }
    public static void deleteToken(String user, String product_id) {
        cache.del(getKey(user, product_id));
    }

    /**
     * token超时设置
     * @param user
     * @param timeout
     */
    @Deprecated
    public static void tokenExpire(String user, int timeout) {
        cache.expire(getKey(user), timeout);
    }
    public static void tokenExpire(String user, String product_id, int timeout) {
        cache.expire(getKey(user, product_id), timeout);
    }

    /**
     * 检验给定的token是否合法
     *  token为 json串，里面包含了登录用户名，token随机串等信息，因此可以与token服务中的token进行比对，
     *   达到验证token的目的
     * @param token
     * @param productId
     * @return
     */
    public static boolean checkToken(String token, String productId) {
        try{
            Document doc = Document.parse(token);
            //
            // 从用户提供的token中提取user_id，然后与缓存中的对应客户的token进行比对
            String userID = doc.getString("user_id")
                    ,gvProduct = doc.getString("product_id")
                    ,gvUid = doc.getString("token");

            if (productId.compareToIgnoreCase(gvProduct) != 0) {
                log.error("来自不同产品的token，验证不通过");
                return false;
            } else{
                String cacheToken = getUserToken(userID,productId);
                if (cacheToken == null || cacheToken.isEmpty() || cacheToken.length() < 10) {
                    log.error("缓存中未发现用户的token， 该用户应该尚未登录");
                    return false;
                }

                Document cacheDoc = Document.parse(cacheToken);
                String cacheUID = cacheDoc.getString("token");

                if ( cacheUID.compareToIgnoreCase(gvUid) == 0) {
                    log.debug("Token验证通过");
                    return true;
                } else {
                    return false;
                }
            }
        }catch (Exception e){
                return false;
        }
    }

    /**
     *  同上
     * @param bean
     * @return
     */
    public static boolean checkToken(IToken bean) {
        String token = bean.getToken();
        log.info("bean中的token为：" + token);
        if ( token == null || token == "") {
            log.error("token验证失败");
            return false;
        } else {
            return checkToken(token, getProductIDFromToken(token));
        }
    }
    /**
     * 查看指定用户是否拥有访问产品的权限（是否购买过产品）
     * @param user
     * @param product
     * @return
     */
    public static  boolean checkUserProduct(String user, String product) throws Exception {

        FindIterable<Document> result = db.collection("t_user_product").find(
                and(eq("user_id",user), eq("product_id", product)));

        return result.iterator().hasNext();
    }

    /**
     * 检查某一客户是否可以继续使用问卷，应该看该用户名下的公司次数是否超限
     * @param token
     * @param code
     * @return true: 用户被限制; false 无限制
     */
    public static boolean checkIsUserLimit(String token, String code) throws Exception {

        String company_id = getCompanyIDFromToken(token);
        String product_id = getProductIDFromToken(token);

        MongoCursor<Document> cursor = db.collection("t_user_limit").find(
                and(eq("company_id", company_id),
                eq("product_id", product_id))).iterator();
        int sum = 0, max_limit = 0;
        //
        // 因为每个公司在某个产品下可能会有多个问卷同时在使用，所以，这里查到的结果不止一条，需要遍历求和
        while(cursor.hasNext()) {
            Document doc = cursor.next();
            sum += doc.getInteger("cur_times");

            //
            // 每次取到的值是相同的
            max_limit = doc.getInteger("max_limit");
        }
        log.debug("公司: " + company_id + "  产品: " + product_id + " 使用次数: " + sum);

        if ( sum > max_limit ) {
            return  true;
        } else {
            return false;
        }
    }

    /**
     * 增加某账号使用某产品的已有次数
     * @param token
     * @param product_id
     */
    public static void incrUserLimit(String token, String product_id, String code) throws Exception {
        String company_id = getCompanyIDFromToken(token);

        MongoCursor<Document> cursor = db.collection("t_user_limit").find(and(eq("company_id", company_id),
                eq("product_id", product_id), eq("code", code))).iterator();

        if (cursor.hasNext()) {
            int cur_times = cursor.next().getInteger("cur_times") + 1;
            db.collection("t_user_limit").updateMany(
                    and(
                            eq("company_id", company_id),
                            eq("product_id", product_id),
                            eq("code", code)
                    ),
                    combine(set("cur_times", cur_times)));
        } else {
            log.error("无法累加使用次数，未找到该用户");
        }
    }

    /**
     * 新增用户
     * @param user_id
     * @param pwd
     * @param product_id
     * @param company_id
     * @param max_limit  限制使用次数
     */
    @Deprecated
    public static void addUser(String user_id, String pwd, String product_id,
                               String company_id, String code,
                               int max_limit, int sex, String phone, String email) throws Exception {
        Document userDoc = new Document("user_id", user_id).append("pwd", pwd)
                                .append("status",1).append("company_id", company_id)
                                .append("email",email).append("role_id","guest")
                                .append("account_type",1).append("sex",sex)
                                .append("phone", phone);

        db.collection("t_user").insertOne(userDoc);

        db.collection("t_user_product").insertOne(new Document("company_id", company_id).append("product_id", product_id)
                                                        .append("user_id", user_id).append("product_name",null));

        db.collection("t_user_limit").insertOne(new Document("cur_times",0).append("product_id", product_id)
                                                        .append("max_limit",max_limit).append("company_id", company_id)
                                                        .append("code", code));
    }

    public static void addUser(UserBean bean) throws Exception {
        Document userDoc = new Document("user_id", bean.user_id).append("pwd", bean.pwd)
                .append("status",bean.status).append("company_id", bean.company_id)
                .append("email",bean.email).append("role_id",bean.role_id)
                .append("account_type",bean.account_type).append("sex",bean.sex)
                .append("phone", bean.phone);

        db.collection("t_user").insertOne(userDoc);

        db.collection("t_user_product").insertOne(new Document("company_id", bean.company_id)
                .append("product_id", bean.product_id)
                .append("user_id", bean.user_id).append("product_name",null));

        db.collection("t_user_limit").insertOne(new Document("cur_times",0).append("product_id", bean.product_id)
                .append("max_limit",bean.max_limit).append("company_id", bean.company_id)
                .append("code", bean.code));
    }


}
