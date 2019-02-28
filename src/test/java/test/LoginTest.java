package test;

import com.unidt.helper.common.MD5Helper;
import com.unidt.services.acl.impl.FraLogin;
import com.unidt.helper.common.UserHelper;
import org.bson.Document;


public class LoginTest {
    public static void main(String[] args) throws Exception {

        FraLogin login = new FraLogin();
        Document doc = null;
        System.out.println(MD5Helper.md5("123456"));
        if( (doc=login.loginWithUserID("dev01", MD5Helper.md5("123456"), "hr")) != null) {
            System.out.println("登录成功");
            String token = UserHelper.createTokenByUserInfo(doc,"hr");
            UserHelper.saveUserToken("dev01", token);
            System.out.println(token);
            if (UserHelper.checkToken(token,"hr")) {
                System.out.println("OK");
            }
        }else {
            System.out.println("登录失败");
        }
    }
}
