package dev.be.blog.dto;

import dev.be.blog.domain.Post;
import dev.be.blog.domain.User;

public class PostDto {

    private final String title;
    private final String text;

    public PostDto(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }


    public String getText() {
        return text;
    }

    public Post upload(User user){
        return Post.builder()
                .title(title)
                .text(text)
                .user(user)
                .build();
    }
}
