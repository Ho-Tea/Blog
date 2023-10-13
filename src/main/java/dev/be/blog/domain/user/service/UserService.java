package dev.be.blog.domain.user.service;

import dev.be.blog.domain.user.dto.UserRequest;
import dev.be.blog.domain.user.dto.UserResponse;
import dev.be.blog.domain.user.entity.Users;
import dev.be.blog.domain.user.repository.UserRepository;
import dev.be.blog.global.common.response.ResponseCode;
import dev.be.blog.global.common.vo.Authority;
import dev.be.blog.global.exception.GlobalException;
import dev.be.blog.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @Transactional
    public String login(UserRequest userDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);
    }

    @Transactional
    public UserResponse join(UserRequest userDto) {
        validateDuplicateUser(userDto);
        Users user = userDto.
                toEntity(userDto, passwordEncoder.encode(userDto.getPassword()), Authority.ROLE_USER);
        return UserResponse.from(userRepository.save(user));
    }

    private void validateDuplicateUser(UserRequest userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(o -> {
                    throw new GlobalException(ResponseCode.ErrorCode.DUPLICATE_USER);
                });
    }
}
