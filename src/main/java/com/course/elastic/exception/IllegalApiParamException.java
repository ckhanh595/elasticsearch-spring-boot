package com.course.elastic.exception;

public class IllegalApiParamException extends RuntimeException {
    public IllegalApiParamException(String message) {
        super(message);
    }
}
