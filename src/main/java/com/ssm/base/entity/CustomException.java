package com.ssm.base.entity;

public class CustomException extends Exception {

    private String message; //异常信息

    //提供有参构造
    public CustomException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
