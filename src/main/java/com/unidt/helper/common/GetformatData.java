package com.unidt.helper.common;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetformatData {
    public static String getformatData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
