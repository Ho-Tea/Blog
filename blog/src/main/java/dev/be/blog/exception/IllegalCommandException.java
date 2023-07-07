package dev.be.blog.exception;

public class IllegalCommandException extends RuntimeException{

    public static final String MESSAGE_ILLEGAL_ARGUMENT_TYPE = "적합하지 않은 명령입니다.";
    public IllegalCommandException() {
        this(MESSAGE_ILLEGAL_ARGUMENT_TYPE);
    }

    public IllegalCommandException(String message) {
        super(message);
    }
}
