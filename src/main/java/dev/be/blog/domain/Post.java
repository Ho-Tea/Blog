package dev.be.blog.domain;

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


    @Override
    public String getName() {
        return title;
    }

    public String getUser() {
        return user.getNickname();
    }

    public String getText() {
        return text;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean rename(String name) {
        this.title = name;
        this.updatedDate = calculateTime();
        return true;
    }


    private Post(Builder builder) {
        this.user = builder.user;
        this.title = builder.title;
        this.text = builder.text;
        this.createdDate = calculateTime();
    }

    public static Builder builder() {
        return new Builder();
    }


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

        public Post build() {
            return new Post(this);
        }
    }

    @Override
    public String toString() {
        return '\t' + "Post : " +
                '\t' + "title='" + title + '\'';
    }
}