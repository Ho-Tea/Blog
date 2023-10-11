package dev.be.blog.domain.board.controller;

import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.dto.BoardListResponse;
import dev.be.blog.domain.board.dto.BoardResponse;
import dev.be.blog.domain.board.service.BoardService;
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
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Validated
public class BoardController {
    private final BoardService boardService;

    // 보드 셍성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResponse<BoardResponse> create(@RequestBody @Valid BoardRequest boardRequest, @AuthenticationPrincipal UserAdapter presentUser) {
        return ApiResponse.ok(ResponseCode.Normal.CREATE, boardService.create(boardRequest, presentUser));
    }

    // 모든 보드 조회
    @GetMapping("/all")
    public ApiResponse<BoardListResponse> findAll(@PageableDefault(size=5, sort="boardId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponse> posts = boardService.findAll(pageable.getPageNumber(), pageable.getPageSize());
        PageInfo pageInfo = new PageInfo(pageable.getPageSize(), pageable.getPageNumber(), posts.getTotalElements(), posts.getTotalPages());
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE, new BoardListResponse(posts.getContent(), pageInfo));
    }

    // 특정 보드 조회
    @GetMapping("/{boardId}")
    public ApiResponse<BoardResponse> find(@PathVariable @Min(1L) Long boardId) {
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE, boardService.findById(boardId));
    }

    // 특정 보드 수정
    @PutMapping("/{boardId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResponse<BoardResponse> update(@PathVariable @Min(1L) Long boardId,
                                         @Valid @RequestBody BoardRequest boardRequest,
                                             @AuthenticationPrincipal UserAdapter presentUser) {
        return ApiResponse.ok(ResponseCode.Normal.UPDATE, boardService.update(boardId, boardRequest, presentUser));
    }

    // 특정 보드 삭제
    @DeleteMapping(value = "/{boardId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResponse<?> delete(@PathVariable @Min(1L) Long boardId, @AuthenticationPrincipal UserAdapter presentUser) {
        boardService.delete(boardId, presentUser);
        return ApiResponse.ok(ResponseCode.Normal.DELETE, String.format("BoardId = %d", boardId));
    }
}
