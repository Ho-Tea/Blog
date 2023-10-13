package dev.be.blog.domain.post.entity;

import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.post.dto.PostRequest;
import dev.be.blog.domain.user.entity.Users;
import dev.be.blog.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    // 부모 정의 (셀프 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_post")
    private Post parentPost;

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPost", cascade = CascadeType.ALL)
    private List<Post> subPost = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @PrePersist
    public void prePersist() {
        this.parentPost = Objects.requireNonNullElse(this.parentPost, this);
        this.title = Objects.requireNonNullElse(this.title, "제목 없음");
        this.content = Objects.requireNonNullElse(this.content, "내용 없음");
    }

    @Builder
    public Post(Long postId, Users user, @NotNull String title, @NotNull String content, Board board, Post parentPost, List<Post> subPost) {
        this.postId = postId;
        this.title = title;
        this.user = user;
        this.content = content;
        this.board = board;
        this.parentPost = parentPost;
        this.subPost = subPost;
    }

    public void update(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
    }

    public void changeParent(Post parentPost) {
        this.parentPost = parentPost;
    }
}
