package com.unidt.helper.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

;

public class PropertiesConf implements IConf{

    Properties prop = null;
    /**
     * 读取配置文件，获取数据库连接参数
     */
    public PropertiesConf() {
         prop = new Properties();
        InputStream instream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");

        try {
            prop.load(instream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PropertiesConf(String filename) {
        prop = new Properties();
        InputStream instream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);

        try {
            prop.load(instream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getString(String key) {
        return prop.getProperty(key);
    }

    @Override
    public int getInt(String key) {
        String ret = prop.getProperty(key);
        return Integer.parseInt(ret);
    }
    public static void main(String[] args) {
        IConf conf = new PropertiesConf();
        String value = conf.getString("host");

        System.out.println(value);
    }
}
