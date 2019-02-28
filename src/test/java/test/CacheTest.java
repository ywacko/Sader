package test;

import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.interf.ICache;

public class CacheTest {

    public static void testpush() {

        ICache cache = DBFactory.createRedis();

        System.out.println(cache.get("dev01_hr_token"));
    }


    public static void main(String[] args) {
        CacheTest.testpush();
    }
}
