package dev.be.blog.domain;

public class User {
    private String name;
    private String nickname;
    private int age;
    private String email;

    private User(String name, String nickname, int age, String email) {
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.email = email;
    }

    public static User create(String name, String nickname, int age, String email) {
        return new User(name, nickname, age, email);
    }

    public String getNickname() {
        return nickname;
    }
}