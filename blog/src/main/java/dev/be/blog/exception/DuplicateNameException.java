package dev.be.blog.exception;

public class DuplicateNameException extends RuntimeException{
    public static final String MESSAGE_DUPLICATE_NAME = "카테고리에 중복된 이름이 존재합니다.";

    public DuplicateNameException() {
        this(MESSAGE_DUPLICATE_NAME);
    }

    public DuplicateNameException(String message) {
        super(message);
    }
}
