package com.spring.boot.manager.utils.db;


import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static Result format(long time) {
        Result result = new Result();
        result.setStatus(ResultStatus.OK);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.setData(sdf.format(new Date(time)));
        return result;
    }
}
