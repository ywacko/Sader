package com.unidt.helper.conf;

public class ConfFactory {

    public static IConf createPropertyConfig() {
        return new PropertiesConf();
    }
    public static IConf createPropertyConfig(String name) {
        return new PropertiesConf(name);
    }
}
