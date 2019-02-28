package com.unidt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoException extends Exception {
    private Logger log = LoggerFactory.getLogger(MongoException.class);

    public MongoException(){
        super();
        log.error("MongoDB 相关异常发生");
    }
    public MongoException(String msg) {
        super(msg);
        log.error(msg);
    }
}
