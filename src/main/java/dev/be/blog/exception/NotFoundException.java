package dev.be.blog.exception;

public class NotFoundException extends RuntimeException {
    public static final String MESSAGE_NOT_FOUND = "해당 카테고리 혹은 파일을 찾을 수 없습니다.";

    public NotFoundException() {
        this(MESSAGE_NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
