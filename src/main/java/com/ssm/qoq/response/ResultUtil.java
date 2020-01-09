package com.ssm.qoq.response;

public class ResultUtil {
    private  ResultUtil(){}
    public  static  Result success(Object data,Integer length){
        Result result = new Result();
        result.setCode(0);
        result.setMessage("请求成功");
        result.setData(data);
        result.setTotalSize(length);
        return result;
    }
    public  static  Result success(String message){
        Result result = new Result();
        result.setCode(0);
        result.setMessage(message);
        result.setTotalSize(0);
        result.setData(null);
        return result;
    }
    public  static  Result error(String message){
        Result result = new Result();
        result.setCode(1);
        result.setMessage(message);
        result.setData(null);
        result.setTotalSize(0);
        return result;
    }
}
