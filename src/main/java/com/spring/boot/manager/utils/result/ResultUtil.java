package com.spring.boot.manager.utils.result;

public class ResultUtil {

    public static Result ok() {
        Result result = new Result();
        result.setStatus(ResultStatus.OK);
        return result;
    }

    public static Result okWithDataAndMessage(String data, String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.OK);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static Result okWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.OK);
        result.setMessage(message);
        return result;
    }


    public static Result queryErrorWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.QUERY_ERROR);
        result.setMessage(message);
        return result;
    }

    public static Result createErrorWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.CREATE_ERROR);
        result.setMessage(message);
        return result;
    }

    public static Result updateErrorWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.UPDATE_ERROR);
        result.setMessage(message);
        return result;
    }

    public static Result deleteErrorWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.DELETE_ERROR);
        result.setMessage(message);
        return result;
    }

    public static Result parameterNotEnoughWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.PARAMETER_NOT_ENOUGH);
        result.setMessage(message);
        return result;
    }

    public static Result parameterErrorWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.PARAMETER_ERROR);
        result.setMessage(message);
        return result;
    }

    public static Result restClientExceptionWithUrlAndMessage(String url, String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.REST_CLIENT_EXCEPTION);
        result.setData(url);
        result.setMessage(message);
        return result;
    }

    public static Result isNullExceptionWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.IS_NULL_EXCEPTION);
        result.setMessage(message);
        return result;
    }

    public static Result isNullExceptionWithUrlAndMessage(String data, String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.IS_NULL_EXCEPTION);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static Result exceptionWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.EXCEPTION);
        result.setMessage(message);
        return result;
    }

    public static Result ioExceptionWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.IO_EXCEPTION);
        result.setMessage(message);
        return result;
    }

    public static Result jsonProcessingExceptionWithMessage(String message) {
        Result result = new Result();
        result.setStatus(ResultStatus.JSON_PROCESSING_EXCEPTION);
        result.setMessage(message);
        return result;
    }
}
