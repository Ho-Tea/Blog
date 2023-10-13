package dev.be.blog.domain.user.dto;

import dev.be.blog.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String nickName;

    public static UserResponse from(Users user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }
}
