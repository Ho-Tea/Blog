package dev.be.blog.domain.board.entity;

import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.user.entity.Users;
import dev.be.blog.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "boards")
@Getter
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotNull
    private String boardName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Builder
    public Board(Long id, @NotNull String boardName, Users user) {
        this.boardId = id;
        this.boardName = boardName;
        this.user = user;
    }

    public void update(BoardRequest boardRequest){
        this.boardName = Objects.requireNonNullElse(boardRequest.getBoardName(), this.boardName);
    }
}