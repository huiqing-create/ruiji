package com.huiqing.ruiji.common;

/**
 * 自定义一个业务异常
 */
public class CustomException extends RuntimeException{
    /**
     * message是自己业务中的异常，在会产生异常的地方自己写的一段信息。
     * @param message
     */
    public CustomException(String message){
        super(message);
    }
}
