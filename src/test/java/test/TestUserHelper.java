package test;

import com.mongodb.client.FindIterable;
import com.unidt.bean.user.UserBean;
import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.impl.FraMongoDB;
import com.unidt.helper.common.MD5Helper;
import com.unidt.services.acl.impl.FraLogin;
import com.unidt.helper.common.UserHelper;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

public class TestUserHelper {
    public void insertUser() throws Exception {
        UserBean bean = new UserBean();
        bean.user_id = "dev01";
        bean.code = "dev";
        bean.pwd = MD5Helper.md5("123456");
        bean.account_type = 1;
        bean.company_id = "unidt";
        bean.email = "dev@mail.com";
        bean.max_limit = 20;
        bean.phone = "110";
        bean.role_id = "guest";
        bean.sex = 1;
        bean.status = 1;
        bean.product_id = "hr";

        UserHelper.addUser(bean);
    }
    public void createIndex() {
        FraMongoDB<Bson, FindIterable<Document>> db = (FraMongoDB<Bson, FindIterable<Document>>) DBFactory.createMongoDB("test").getRawDB();

        db.createAscendingIndex("test","t_user", Arrays.asList("user_id","company_id"));
        db.createAscendingIndex("test","t_user_product", Arrays.asList("user_id","company_id","product_id"));
        db.createAscendingIndex("test","t_user_limit", Arrays.asList("product_id","company_id"));

    }

    public void testGetUserLimit(String user, String pwd, String product) throws Exception {

        FraLogin login = new FraLogin();
        Document docLogin = login.loginWithUserID(user,pwd,product);
        String token = UserHelper.createTokenByUserInfo(docLogin, product);

        UserHelper.incrUserLimit(token,product,"dev");
        boolean isLimit = UserHelper.checkIsUserLimit(token,"dev");
        if (isLimit) {
            System.out.println("已达使用次数上限");
        }else{
            System.out.println("可以继续使用");
        }
    }


    public static void main(String[] args) throws Exception {
        TestUserHelper testUserHelper = new TestUserHelper();

//        testUserHelper.insertUser();
        testUserHelper.createIndex();
//        testUserHelper.testGetUserLimit("dev01",MD5Helper.md5("123456"),"hr");

    }
}
