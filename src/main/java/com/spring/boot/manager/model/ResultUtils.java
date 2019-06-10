package com.spring.boot.manager.model;

import com.spring.boot.manager.utils.result.Result;

public class ResultUtils {

    public static Result result(Result result) {
        return result;
    }

    public static Object data(Result result){
        return result.getData();
    }
}
