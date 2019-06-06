package com.spring.boot.manager.utils.result;

public class ResultStatus {

    //正常结果
    public final static int OK = 1;

    //查询数据失败
    public final static int QUERY_ERROR = 1002;
    //新增数据失败
    public final static int CREATE_ERROR = 1003;
    //修改数据失败
    public final static int UPDATE_ERROR = 1004;
    //删除数据失败
    public final static int DELETE_ERROR = 1005;

    //缺少必需参数
    public final static int PARAMETER_NOT_ENOUGH = 1010;
    //参数错误
    public final static int PARAMETER_ERROR = 1011;


    //REST_CLIENT_EXCEPTION 异常
    public final static int REST_CLIENT_EXCEPTION = 1020;

    //结果为空异常
    public final static int IS_NULL_EXCEPTION = 1030;

    //Exception异常
    public final static int EXCEPTION = 1050;
    //IO异常
    public final static int IO_EXCEPTION = 1051;
    //IO异常
    public final static int JSON_PROCESSING_EXCEPTION = 1052;




}
