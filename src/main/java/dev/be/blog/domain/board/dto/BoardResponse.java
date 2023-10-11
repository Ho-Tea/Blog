package dev.be.blog.domain.board.dto;


import dev.be.blog.domain.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class BoardResponse {
    private Long boardId;

    private String boardName;
    private String writer;

    public static BoardResponse from(Board board){
        return new BoardResponse(board.getBoardId(), board.getBoardName(), board.getUser().getNickName());
    }
}
