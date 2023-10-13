package dev.be.blog.domain.user.controller;

import dev.be.blog.domain.user.dto.UserRequest;
import dev.be.blog.domain.user.dto.UserResponse;
import dev.be.blog.domain.user.service.UserService;
import dev.be.blog.global.common.response.ApiResponse;
import dev.be.blog.global.common.response.LoginResponse;
import dev.be.blog.global.common.response.ResponseCode;
import dev.be.blog.global.jwt.JwtFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody UserRequest userDto) {
        String jwt = userService.login(userDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(ApiResponse.ok(ResponseCode.Normal.JOIN, new LoginResponse(jwt)));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> signup(@Valid @RequestBody UserRequest userDto) {
        return ApiResponse.ok(ResponseCode.Normal.SIGN_UP, userService.join(userDto));
    }

}