package cn.dayutec.pigeon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTransfUtil {
    // 获取当前日期，如：2022-03-04
    public static String getNowDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    // long 转 String
    public static String long2StringTime(long tsp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(tsp);
    }
}
