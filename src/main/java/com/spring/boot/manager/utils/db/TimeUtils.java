package com.spring.boot.manager.utils.db;


import com.spring.boot.manager.utils.result.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String format(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
