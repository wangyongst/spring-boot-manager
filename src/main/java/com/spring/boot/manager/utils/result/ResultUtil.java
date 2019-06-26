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

    public static boolean checkResult(Result result) {
        if (result != null && result.getStatus() == 1) {
            return true;
        }
        return false;
    }

    public static Result noPermission() {
        Result result = new Result();
        result.setStatus(2);
        result.setMessage("你没有访问权限");
        return result;
    }

    public static Result unAuth() {
        Result result = new Result();
        result.setStatus(3);
        result.setMessage("你还未登录或已经退出！");
        return result;
    }


    public static Result loginFail(String message) {
        Result result = new Result();
        result.setStatus(0);
        result.setMessage(message);
        return result;
    }


    public static Result loginOK(String token,String message) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(token);
        result.setMessage(message);
        return result;
    }
}
