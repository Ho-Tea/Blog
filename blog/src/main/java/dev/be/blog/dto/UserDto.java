package dev.be.blog.dto;

import dev.be.blog.domain.User;

public class UserDto {
    private String name;
    private String nickname;
    private int age;
    private String email;

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public UserDto(String name, String nickname, int age, String email) {
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.email = email;
    }

    public static User toEntity(UserDto userDto) {
        return User.create(userDto.getName(),
                userDto.getNickname(),
                userDto.getAge(),
                userDto.getEmail());
    }

}
