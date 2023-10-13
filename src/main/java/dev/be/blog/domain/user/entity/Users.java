package dev.be.blog.domain.user.entity;

import dev.be.blog.global.common.entity.BaseTimeEntity;
import dev.be.blog.global.common.vo.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nickName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) && Objects.equals(email, users.email) && Objects.equals(password, users.password) && authority == users.authority;
    }

    @Builder
    public Users(Long id, @NotNull String nickName, @NotNull String email, @NotNull String password, Authority authority, Boolean isActive) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.isActive = isActive;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, authority);
    }

    @PrePersist
    public void prePersist() {
        this.isActive = Objects.requireNonNullElse(this.isActive, true);
    }
}
