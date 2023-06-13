package dev.be.blog.exception;

public class IllegalContentTypeException extends RuntimeException{
    public static final String MESSAGE_ILLEGAL_ARGUMENT_TYPE = "적합하지 않은 카테고리 혹은 포스트의 이름입니다.";

    public IllegalContentTypeException() {
        this(MESSAGE_ILLEGAL_ARGUMENT_TYPE);
    }

    public IllegalContentTypeException(String message) {
        super(message);
    }
}