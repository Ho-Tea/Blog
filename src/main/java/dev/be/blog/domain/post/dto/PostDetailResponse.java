package dev.be.blog.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PostDetailResponse {
    private String breadCrumbs;
    private Long postId;
    private String title;
    private String content;
    private String writer;

    @Builder
    public PostDetailResponse(String breadCrumbs, Long postId, String title, String content, String writer) {
        this.breadCrumbs = Objects.requireNonNullElse(breadCrumbs, "ROOT");
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

}
