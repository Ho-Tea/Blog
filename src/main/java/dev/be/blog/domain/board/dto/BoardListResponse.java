package dev.be.blog.domain.board.dto;

import dev.be.blog.global.common.vo.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardListResponse<T> {
    private T boards;
    private PageInfo pageInfo;
}

