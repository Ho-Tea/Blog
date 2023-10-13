package dev.be.blog.domain.board.dto;


import dev.be.blog.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponse {
    private Long boardId;

    private String boardName;
    private String writer;

    public static BoardResponse from(Board board) {
        return new BoardResponse(board.getBoardId(), board.getBoardName(), board.getUser().getNickName());
    }
}
