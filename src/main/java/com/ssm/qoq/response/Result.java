package com.ssm.qoq.response;

import lombok.Data;

@Data
public class Result<T> {
    private  Integer code;
    private  String message;
    private  Integer totalSize;
    private T data;
}
