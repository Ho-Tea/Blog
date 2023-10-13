package dev.be.blog.domain.user.dto;

import dev.be.blog.domain.user.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserAdapter extends User {
    private final Users user;

    public UserAdapter(Users user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }
}
