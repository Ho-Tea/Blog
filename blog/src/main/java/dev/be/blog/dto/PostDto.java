package dev.be.blog.dto;

import dev.be.blog.domain.Post;
import dev.be.blog.domain.User;

import java.util.Optional;

public class PostDto {

    private final String title;
    private final String text;
    private final String updatedDate;
    private final String createdDate;
    private final String user;

    public PostDto(String title, String text){
        this.title = title;
        this.text = text;
        this.updatedDate = Optional.empty().toString();
        this.createdDate = Optional.empty().toString();
        this.user = Optional.empty().toString();
    }


    public PostDto(String title, String text, String updatedDate, String createdDate, String user) {
        this.title = title;
        this.text = text;
        this.updatedDate = updatedDate;
        this.createdDate = createdDate;
        this.user = user;
    }

    public static Post toEntity(PostDto postDto, User user){
        return Post.builder()
                .title(postDto.title)
                .text(postDto.text)
                .user(user)
                .build();
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
