package com.ssm.qoq.exception;

public class MyException extends RuntimeException {
    public  MyException(String message){
        super(message);
    }
    public  MyException(){
        super();
    }
}
