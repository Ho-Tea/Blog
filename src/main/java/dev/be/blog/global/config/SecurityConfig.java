package dev.be.blog.global.config;

import dev.be.blog.global.jwt.JwtAccessDeniedHandler;
import dev.be.blog.global.jwt.JwtAuthenticationEntryPoint;
import dev.be.blog.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(o -> o.disable())
                .exceptionHandling(o -> o.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .exceptionHandling(o -> o.accessDeniedHandler(jwtAccessDeniedHandler))

                .sessionManagement(o -> o.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(o ->
                        o.requestMatchers(new AntPathRequestMatcher("/auth/login"),
                                        new AntPathRequestMatcher("/auth/signup"),
                                        new AntPathRequestMatcher("/api/boards/all"),
                                        new AntPathRequestMatcher("/api/boards/{boardId}"),
                                        new AntPathRequestMatcher("/api/posts/all"),
                                        new AntPathRequestMatcher("/api/posts/{postId}"))
                                .permitAll()
                                .anyRequest().authenticated())
                .apply(new JwtSecurityConfig(tokenProvider));


        return http.build();

    }
}
