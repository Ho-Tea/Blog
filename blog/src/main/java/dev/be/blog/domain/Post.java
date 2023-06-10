package dev.be.blog.domain;

import dev.be.blog.dto.PostDto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Post implements Content {
    private User user;
    private String title;
    private String text;
    private String updatedDate;
    private final String createdDate;

    private String calculateTime() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        // Timestamp to String
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentTimestamp);
    }

    public static PostDto toDto(Post post) {
        return new PostDto(post.title, post.text, post.updatedDate, post.createdDate, post.user.getNickname());
    }


    @Override
    public void rename(String name) {
        this.text = name;
        this.updatedDate = calculateTime();
    }

    @Override
    public String getName() {
        return title;
    }


    private Post(Builder builder) {
        this.user = builder.user;
        this.title = builder.title;
        this.text = builder.text;
        this.createdDate = calculateTime();
    }

    // 빌더 호출, 외부에서 Post.builder() 으로 접근할 수 있도록 static 메소드로 생성
    public static Builder builder() {
        return new Builder();
    }


    // static 형태의 inner class 생성
    public static class Builder {
        private User user;
        private String title;
        private String text;
        private Builder() {
        }
        public Builder user(User user) {
            this.user = user;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder text(String text) {
            this.text = text;
            return this;
        }
        // 마지막에 build 메소드를 실행하면 this가 return 되도록 구현
        public Post build() {
            return new Post(this);
        }
    }
}