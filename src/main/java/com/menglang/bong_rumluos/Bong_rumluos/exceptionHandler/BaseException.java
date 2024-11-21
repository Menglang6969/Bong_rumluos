package com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler;

public abstract class BaseException extends RuntimeException {


    public BaseException(String message) {
        super(message);
    }
    public BaseException(Throwable cause){
        super(cause);
    }
    public BaseException(String message,Throwable cause){
        super(message, cause);
    }
    public BaseException(String message,Throwable cause,Boolean enabledSuppression,boolean writeAbleStackTrace){
        super(message,cause,enabledSuppression,writeAbleStackTrace);
    }


}
