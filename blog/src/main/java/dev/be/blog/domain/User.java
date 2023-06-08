package dev.be.blog.domain;

import dev.be.blog.dto.UserDto;

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

    public static User create(UserDto userDto){
        return new User(userDto.getName(),
                userDto.getNickname(),
                userDto.getAge(),
                userDto.getEmail());
    }


}