package dev.be.blog.domain.post.controller;


import dev.be.blog.domain.board.dto.BoardListResponse;
import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.dto.BoardResponse;
import dev.be.blog.domain.post.dto.PostDetailResponse;
import dev.be.blog.domain.post.dto.PostListResponse;
import dev.be.blog.domain.post.dto.PostRequest;
import dev.be.blog.domain.post.dto.PostResponse;
import dev.be.blog.domain.post.service.PostService;
import dev.be.blog.domain.user.dto.UserAdapter;
import dev.be.blog.global.common.response.ApiResponse;
import dev.be.blog.global.common.response.ResponseCode;
import dev.be.blog.global.common.vo.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;

    // 게시물 셍성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResponse<PostDetailResponse> create(@RequestBody @Valid PostRequest postRequest, @AuthenticationPrincipal UserAdapter presentUser) {
        return ApiResponse.ok(ResponseCode.Normal.CREATE, postService.create(postRequest, presentUser));
    }

    // 모든 게시물 조회
    @GetMapping("/all")
    public ApiResponse<PostListResponse> findAll(@PageableDefault(size=5, sort="postId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> posts = postService.findAll(pageable.getPageNumber(), pageable.getPageSize());
        PageInfo pageInfo = new PageInfo(pageable.getPageSize(), pageable.getPageNumber(), posts.getTotalElements(), posts.getTotalPages());
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE, new PostListResponse(posts.getContent(), pageInfo));
    }

    // 특정 게시물 조회
    @GetMapping(value = "/{postId}")
    public ApiResponse<PostDetailResponse> find(@PathVariable @Min(1L) Long postId) {
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE, postService.findById(postId));
    }

    // 특정 게시물 수정
    @PutMapping("/{postId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResponse<PostDetailResponse> update(@PathVariable @Min(1L) Long postId,
                                             @Valid @RequestBody PostRequest postRequest,
                                                  @AuthenticationPrincipal UserAdapter presentUser) {
        return ApiResponse.ok(ResponseCode.Normal.UPDATE, postService.update(postId, postRequest, presentUser));
    }

    // 특정 게시물 삭제
    @DeleteMapping(value = "/{postId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResponse<?> delete(@PathVariable @Min(1L) Long postId, @AuthenticationPrincipal UserAdapter presentUser) {
        postService.delete(postId, presentUser);
        return ApiResponse.ok(ResponseCode.Normal.DELETE, String.format("PostId = %d", postId));
    }


}
