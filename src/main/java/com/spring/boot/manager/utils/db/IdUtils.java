package com.spring.boot.manager.utils.db;

import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultStatus;
import org.apache.commons.lang3.RandomStringUtils;

public class IdUtils {

    public static Result createId() {
        Result result = new Result();
        result.setStatus(ResultStatus.OK);
        result.setData(RandomStringUtils.randomAlphanumeric(7) + "-" + RandomStringUtils.randomAlphanumeric(7) + "-" + RandomStringUtils.randomAlphanumeric(7) + "-" + RandomStringUtils.randomAlphanumeric(8));
        return result;
    }
}
