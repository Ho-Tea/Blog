package dev.be.blog.global.common.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ResponseCode {

    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {
        DUPLICATE_BOARD(HttpStatus.CONFLICT, "중복된 보드 입니다"),
        DUPLICATE_POST(HttpStatus.CONFLICT, "중복된 게시물 입니다"),
        DUPLICATE_USER(HttpStatus.CONFLICT, "중복된 이메일 입니다"),
        NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 보드 혹은 게시물을 찾을 수 없습니다"),
        NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당하는 사용자를 찾을 수 없습니다"),
        NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당하는 게시물을 찾을 수 없습니다"),
        NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "해당하는 보드를 찾을 수 없습니다"),
        NOT_EXIST_BOARD(HttpStatus.NOT_FOUND, "존재하는 보드가 없습니다"),
        NOT_EXIST_POST(HttpStatus.NOT_FOUND, "존재하는 게시물이 없습니다"),
        NOT_AUTHORITY(HttpStatus.FORBIDDEN, "권한이 없습니다"),
        NOT_AUTHORITY_UPDATE(HttpStatus.FORBIDDEN, "게시글 작성자만 수정할 수 있습니다"),
        NOT_AUTHORITY_DELETE(HttpStatus.FORBIDDEN, "게시글 작성자만 삭제할 수 있습니다"),
        NOT_VALID(HttpStatus.BAD_REQUEST, "적합하지 않은 입력입니다");

        private final HttpStatus status;
        private final String MESSAGE;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Normal {
        UPDATE(HttpStatus.OK, "수정 되었습니다"),
        CREATE(HttpStatus.CREATED, "생성 되었습니다"),
        DELETE(HttpStatus.OK, "삭제 되었습니다"),
        RETRIEVE(HttpStatus.OK, "조회 되었습니다"),
        JOIN(HttpStatus.OK, "로그인 되었습니다"),
        SIGN_UP(HttpStatus.CREATED, "회원가입 되었습니다");
        private final HttpStatus status;
        private final String MESSAGE;
    }
}
