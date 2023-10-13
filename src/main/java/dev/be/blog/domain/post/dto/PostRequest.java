package dev.be.blog.domain.post.dto;

import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.post.entity.Post;
import dev.be.blog.domain.user.entity.Users;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotNull(message = "Board가 설정되지 않았습니다")
    private BoardRequest boardRequest;

    private Long parentId;

    @NotNull(message = "제목이 입력되지않았습니다.")
    private String title;
    @NotNull(message = "내용이 입력되지않았습니다.")
    private String content;

    public static Post toEntity(PostRequest postRequest, Board board, Post parentPost, Users user) {
        return Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .parentPost(parentPost)
                .board(board).build();
    }


}
