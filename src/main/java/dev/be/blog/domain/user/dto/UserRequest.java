package dev.be.blog.domain.user.dto;

import dev.be.blog.domain.user.entity.Users;
import dev.be.blog.global.common.vo.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotEmpty
    private String nickName;


    public static Users toEntity(UserRequest userRequest, String password, Authority authority) {
        return Users.builder()
                .email(userRequest.email)
                .nickName(userRequest.nickName)
                .password(password)
                .authority(authority)
                .build();
    }

}