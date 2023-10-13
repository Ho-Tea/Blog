package dev.be.blog.domain.post.dto;

import dev.be.blog.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;

    public static PostResponse from(Post post) {
        return new PostResponse(post.getPostId(), post.getTitle());
    }
}
