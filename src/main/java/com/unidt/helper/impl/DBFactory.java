package com.unidt.helper.impl;

import com.mongodb.client.FindIterable;
import com.unidt.helper.conf.ConfFactory;
import com.unidt.helper.conf.IConf;
import com.unidt.helper.interf.ICache;
import com.unidt.helper.interf.IFraDB;
import org.bson.Document;

public class DBFactory {

    /**
     * 获取mongo DB数据库的操作代理
     * @return
     */
    @Deprecated
    public static IFraDB createMongoDB(String database) {
        IFraDB<Document,FindIterable<Document>> db = new FraMongoDB<>();

        IConf conf = ConfFactory.createPropertyConfig();
        String user = conf.getString("username");
        String pwd = conf.getString("password");
        String host = conf.getString("host");
        int port = Integer.parseInt(conf.getString("port"));

        try {
            db.connect(host,port,user,pwd);
            db.setDbname(database);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static IFraDB createMongoDB() {
        IFraDB<Document,FindIterable<Document>> db = new FraMongoDB<>();

        IConf conf = ConfFactory.createPropertyConfig();
        String user = conf.getString("username");
        String pwd = conf.getString("password");
        String host = conf.getString("host");
        String dbname = conf.getString("dbname");
        int port = Integer.parseInt(conf.getString("port"));

        try {
            db.connect(host,port,user,pwd);
            db.setDbname(dbname);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 创建Redis的操作对象
     * @return
     */
    public static ICache createRedis() {
        IConf conf = ConfFactory.createPropertyConfig();
        String pwd = conf.getString("redis_pwd");
        String host = conf.getString("redis_host");
        int port = Integer.parseInt(conf.getString("redis_port"));

        ICache cache = new RedisCache();
        cache.connect(host,port,"",pwd);
        return cache;
    }
}
