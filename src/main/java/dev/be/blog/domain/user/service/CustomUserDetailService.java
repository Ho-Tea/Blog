package dev.be.blog.domain.user.service;

import dev.be.blog.domain.user.dto.UserAdapter;
import dev.be.blog.domain.user.entity.Users;
import dev.be.blog.domain.user.repository.UserRepository;
import dev.be.blog.global.common.response.ResponseCode;
import dev.be.blog.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> createUser(user))
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FOUND_USER));
    }

    private User createUser(Users user) {
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getAuthority().name()));
        return new UserAdapter(user, grantedAuthorities);
    }
}
