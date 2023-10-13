package dev.be.blog.domain.post.service;

import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.board.repository.BoardRepository;
import dev.be.blog.domain.post.dto.PostDetailResponse;
import dev.be.blog.domain.post.dto.PostRequest;
import dev.be.blog.domain.post.dto.PostResponse;
import dev.be.blog.domain.post.entity.Post;
import dev.be.blog.domain.post.repository.PostRepository;
import dev.be.blog.domain.user.dto.UserAdapter;
import dev.be.blog.global.common.response.ResponseCode;
import dev.be.blog.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public PostDetailResponse create(PostRequest request, UserAdapter userAdapter) {
        Board board = findBoardByName(request.getBoardRequest().getBoardName());
        if (request.getParentId() == null) {
            Post post = postRepository.save(PostRequest.toEntity(request, board, null, userAdapter.getUser()));
            return PostDetailResponse.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .writer(post.getUser().getNickName())
                    .content(post.getContent()).build();
        }
        Post parentPost = findPostById(request.getParentId());
        Post post = postRepository.save(PostRequest.toEntity(request, board, parentPost, userAdapter.getUser()));
        parentPost.getSubPost().add(post);
        return PostDetailResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUser().getNickName())
                .breadCrumbs(getBreadCrumbs(post))
                .build();
    }

    @Override
    public Page<PostResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("postId").descending());
        return findAllByPageInfo(pageRequest);
    }

    @Override
    public PostDetailResponse findById(Long postId) {
        Post post = findPostById(postId);
        return PostDetailResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUser().getNickName())
                .breadCrumbs(getBreadCrumbs(post))
                .build();
    }

    @Override
    @Transactional
    public PostDetailResponse update(Long postId, PostRequest request, UserAdapter userAdapter) {
        Post post = findPostById(postId);
        validateWriter(post, userAdapter, ResponseCode.ErrorCode.NOT_AUTHORITY_UPDATE);
        checkParentChanged(request, post);
        post.update(request);
        return PostDetailResponse.builder().postId(postId)
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUser().getNickName())
                .breadCrumbs(getBreadCrumbs(post)).build();
    }

    @Override
    @Transactional
    public void delete(Long postId, UserAdapter userAdapter) {
        Post post = findPostById(postId);
        validateWriter(post, userAdapter, ResponseCode.ErrorCode.NOT_AUTHORITY_DELETE);
        postRepository.delete(post);
    }

    private void checkParentChanged(PostRequest postRequest, Post post) {
        if (!postRequest.getParentId().equals(post.getPostId())) {
            Post newParent = findPostById(postRequest.getParentId());
            newParent.getSubPost().remove(post);
            post.changeParent(newParent);
        }
    }

    private void validateWriter(Post post, UserAdapter userAdapter, ResponseCode.ErrorCode errorCode) {
        if (!post.getUser().equals(userAdapter.getUser())) {
            throw new GlobalException(errorCode);
        }
    }

    private String getBreadCrumbs(Post post) {
        Set<Post> breadCrumbs = new HashSet<>();
        bridgeBreadCrumbs(post, breadCrumbs);
        return breadCrumbs.stream()
                .map(Post::getPostId)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining("/"));
    }

    private void bridgeBreadCrumbs(Post post, Set<Post> breadCrumbs) {
        breadCrumbs.add(post);
        if (!post.getPostId().equals(post.getParentPost().getPostId())) {
            Post parentPost = findPostById(post.getParentPost().getPostId());
            breadCrumbs.add(parentPost);
            bridgeBreadCrumbs(parentPost, breadCrumbs);
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FOUND_POST));
    }

    private Board findBoardByName(String boardName) {
        return boardRepository.findByBoardName(boardName)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FOUND_BOARD));
    }

    private Page<PostResponse> findAllByPageInfo(PageRequest pageRequest) {
        return Optional.of(postRepository.findAllByOrderByPostIdDesc(pageRequest).map(PostResponse::from))
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_EXIST_POST));
    }


}
