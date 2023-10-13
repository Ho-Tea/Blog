package dev.be.blog.domain.post.service;

import dev.be.blog.domain.post.dto.PostDetailResponse;
import dev.be.blog.domain.post.dto.PostRequest;
import dev.be.blog.domain.post.dto.PostResponse;
import dev.be.blog.domain.user.dto.UserAdapter;
import org.springframework.data.domain.Page;

public interface PostService {
    PostDetailResponse create(PostRequest request, UserAdapter userAdapter);

    Page<PostResponse> findAll(int page, int size);

    PostDetailResponse findById(Long postId);

    PostDetailResponse update(Long postId, PostRequest request, UserAdapter userAdapter);

    void delete(Long boardId, UserAdapter userAdapter);
}
