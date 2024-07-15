package com.iceford.heartguard.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
    // 将时间戳转换为北京时间
    public static String timestampToBeijingTime(long timestamp) {
        // 创建一个Date对象并设置时间戳
        Date date = new Date(timestamp);
        // 创建SimpleDateFormat对象，并设置时区为东八区
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 将Date对象格式化为字符串
        return sdf.format(date);
    }

}

