package test;

import com.unidt.helper.common.MD5Helper;

import java.security.NoSuchAlgorithmException;

public class MD5Test {
    public static void main(String [] args) throws NoSuchAlgorithmException {
        System.out.println(MD5Helper.md5("123456"));
    }
}
