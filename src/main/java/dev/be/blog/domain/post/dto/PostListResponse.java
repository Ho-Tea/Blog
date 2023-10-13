package dev.be.blog.domain.post.dto;

import dev.be.blog.global.common.vo.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponse<T> {
    private T posts;
    private PageInfo pageInfo;
}
