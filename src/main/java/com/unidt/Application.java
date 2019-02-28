package com.unidt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//在部署到外部的Tomcat时，需要将classpath的引入文件去掉，因为在web.xml已经配置过一次了
//@ImportResource("classpath:dispatcher-servlet.xml")

public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }
}
