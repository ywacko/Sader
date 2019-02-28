package com.unidt.helper.common;

import org.bson.Document;

/**
 * 返回结果json结构
 * {"code":200, "msg":"ok", "data":{}}
 */
public class ReturnResult {

    public static Document createResult() {
        Document doc = new Document("status",200).append("msg", "ok").append("data", new Document());
        return doc;
    }

    public static Document createResult(int code, String msg) {
        Document doc = new Document("status",code).append("msg", msg).append("data", new Document());
        return doc;
    }


    public static Document createResult(int code, String msg, Document data) {
        Document doc = new Document("status",code).append("msg", msg).append("data", data);
        return doc;
    }
}
