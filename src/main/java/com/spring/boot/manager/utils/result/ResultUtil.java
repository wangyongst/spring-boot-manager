package com.spring.boot.manager.utils.result;

public class ResultUtil {

    public static Result ok() {
        Result result = new Result();
        result.setStatus(1);
        return result;
    }

    public static Result okWithMessage(String message) {
        Result result = new Result();
        result.setMessage(message);
        result.setStatus(1);
        return result;
    }


    public static Result okWithData(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setStatus(1);
        return result;
    }

    public static Result okWithDataAndMessage(Object data, String message) {
        Result result = new Result();
        result.setData(data);
        result.setMessage(message);
        result.setStatus(1);
        return result;
    }

    public static Result errorWithMessage(String message) {
        Result result = new Result();
        result.setStatus(0);
        result.setMessage(message);
        return result;
    }
}
