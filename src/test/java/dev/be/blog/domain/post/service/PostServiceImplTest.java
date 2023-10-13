package dev.be.blog.domain.post.service;

import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.board.repository.BoardRepository;
import dev.be.blog.domain.board.service.BoardServiceImpl;
import dev.be.blog.domain.post.dto.PostRequest;
import dev.be.blog.domain.post.entity.Post;
import dev.be.blog.domain.post.repository.PostRepository;
import dev.be.blog.domain.user.dto.UserAdapter;
import dev.be.blog.domain.user.entity.Users;
import dev.be.blog.global.common.response.ResponseCode;
import dev.be.blog.global.common.vo.Authority;
import dev.be.blog.global.exception.GlobalException;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.be.blog.global.common.response.ResponseCode.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private static PostRequest validObject;
    private static BoardRequest boardRequest;
    private static Board board;
    @Mock
    private UserAdapter userAdapter;
    private static Users user;

    @BeforeAll
    private static void setUp() {
        boardRequest = new BoardRequest("새로운 보드");
        validObject = new PostRequest(boardRequest, null, "Title", "Content");
        board = Board.builder().boardName(boardRequest.getBoardName()).build();
        user = Users.builder()
                .email("Valid@Valid")
                .password("12345678")
                .authority(Authority.ROLE_USER)
                .build();
    }

    private Post createPost(Long postId, Post parent){
        return Post.builder()
                .postId(postId)
                .parentPost(parent)
                .board(board)
                .title(validObject.getTitle())
                .content(validObject.getContent())
                .user(user)
                .build();
    }

    @Test
    @DisplayName("게시물 생성에 성공한다")
    public void create() {
        // Given
        Long postId = 1L;
        Post post = createPost(postId, null);
        // When
        when(boardRepository.findByBoardName(boardRequest.getBoardName())).thenReturn(Optional.ofNullable(board));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        // Then
        assertThat(postService.create(validObject, userAdapter).getPostId()).isEqualTo(postId);
    }

    @Test
    @DisplayName("모든 게시물 조희를 성공한다")
    public void findAll() {
        // Given
        int page = 1;
        int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("postId").descending());
        List<Post> posts = new ArrayList<>();
        for(int i = 1 ; i <= 3; i++){
            posts.add(createPost(Long.valueOf(i), null));
        }
        Page<Post> pages = new PageImpl<>(posts);
        // When
        when(postRepository.findAllByOrderByPostIdDesc(pageRequest)).thenReturn(pages);
        // Then
        assertThat(postService.findAll(page, size).stream().map(o -> o.getPostId()).toArray())
                .containsExactly(posts.stream().map(o -> o.getPostId()).toArray());
    }

    @Test
    @DisplayName("게시물이 하나도 없는 경우 모든 게시물 조희를 실패한다")
    public void failFindAll() {
        // Given
        int page = 1;
        int size = 3;
        // When
        when(postRepository.findAllByOrderByPostIdDesc(any())).thenThrow(new GlobalException(ResponseCode.ErrorCode.NOT_EXIST_POST));
        // Then
        assertThrows(GlobalException.class, () -> postService.findAll(page, size));
    }

    @Test
    @DisplayName("특정 게시물 조회에 성공한다")
    public void findById() {
        // Given
        Long postId = 1L;
        Post post = createPost(postId, null);
        Post postWithParent = createPost(postId, post);
        // When
        when(postRepository.findById(postId)).thenReturn(Optional.of(postWithParent));
        // Then
        assertThat(postService.findById(postId).getPostId()).isEqualTo(postId);
    }

    @Test
    @DisplayName("존재하지 않는 특정 게시물 조회에 실패한다")
    public void failFindById() {
        // Given
        Long postId = 1L;
        // When
        when(postRepository.findById(postId)).thenThrow(new GlobalException(NOT_FOUND_POST));
        // Then
        assertThrows(GlobalException.class, () -> postService.findById(postId).getPostId());

    }


    @Test
    @DisplayName("특정 게시물 수정에 성공한다")
    public void update() {
        // Given
        Long postId = 1L;
        Post post = createPost(postId, null);
        Post postWithParent = createPost(postId, post);
        PostRequest newPost = new PostRequest(boardRequest, postId, "NEW_TITLE", "NEW_CONTENT");
        // When
        when(userAdapter.getUser()).thenReturn(user);
        when(postRepository.findById(postId)).thenReturn(Optional.of(postWithParent));
        // Then
        assertThat(postService.update(postId, newPost, userAdapter).getTitle()).isEqualTo(newPost.getTitle());
        assertThat(postService.update(postId, newPost, userAdapter).getContent()).isEqualTo(newPost.getContent());

    }

    @Test
    @DisplayName("특정 게시물이 존재하지 않다면 수정에 실패한다")
    public void failUpdate() {
        // Given
        Long postId = 1L;
        PostRequest newPost = new PostRequest(boardRequest, postId, "NEW_TITLE", "NEW_CONTENT");
        // When
        when(postRepository.findById(postId)).thenThrow(new GlobalException(NOT_FOUND_POST));
        // Then
        assertThrows(GlobalException.class, () -> postService.update(postId, newPost, userAdapter));

    }

    @Test
    @DisplayName("특정 게시물 삭제에 성공한다")
    public void delete() {
        // Given
        Long postId = 1L;
        // 모의 객체 생성
        PostServiceImpl postServiceImpl = mock(PostServiceImpl.class);
        // When
        postServiceImpl.delete(postId, userAdapter);
        // Then
        verify(postServiceImpl).delete(postId, userAdapter);

    }

    @Test
    @DisplayName("특정 게시물이 존재하지 않는 경우 특정게시물 삭제에 실패한다")
    public void failDelete() {
        // Given
        Long postId = 1L;
        // When
//        doThrow(new EmptyResultDataAccessException(1)).when(postRepository).deleteById(postId);
        // Then
        assertThrows(GlobalException.class, () -> postService.delete(postId, userAdapter));

    }
}