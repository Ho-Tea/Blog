package dev.be.blog.domain.post.repository;

import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.board.repository.BoardRepository;
import dev.be.blog.domain.post.entity.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 데이터베이스를 사용하지 않음
class PostRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    private static Post post;
    private static Board board;
    @Autowired
    private PostRepository postRepository;

    @BeforeAll
    private static void setUp(){
        board = Board.builder().boardName("board").build();
        post = Post.builder()
                    .parentPost(null)
                    .board(board)
                    .title("Title")
                    .content("Content")
                    .build();
    }
    private Post createPost(Long postId,Board board, Post parent){
        return Post.builder()
                .postId(postId)
                .parentPost(parent)
                .board(board)
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    @Test
    @DisplayName("Repository - save 메서드 검증")
    void save(){
        Post result = postRepository.save(post);
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getContent()).isEqualTo(post.getContent());
    }


    @Test
    @DisplayName("Repository - findById 메서드 검증")
    void findById(){
        Post newPost = postRepository.save(post);
        Optional<Post> result = postRepository.findById(newPost.getPostId());
        assertThat(result.get().getPostId()).isEqualTo(newPost.getPostId());
        assertThat(result.get().getTitle()).isEqualTo(newPost.getTitle());
        assertThat(result.get().getContent()).isEqualTo(newPost.getContent());
    }

    @Test
    @DisplayName("Repository - findAllByOrderByPostIdDesc 메서드 검증")
    void findAllByOrderByPostIdDesc(){
        int page = 1;
        int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size);
        boardRepository.save(post.getBoard());
        for(int i = 1 ; i <= 3; i++){
            postRepository.save(createPost(Long.valueOf(i),board, null));
        }
        Page<Post> result = postRepository.findAllByOrderByPostIdDesc(pageRequest);
        assertThat(result.getNumber()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
    }

    @Test
    @DisplayName("Repository - deleteById 메서드 검증")
    void deleteById(){
        Board newBoard = Board.builder().boardName("NEW_BOARD").build();
        Post result = postRepository.save(createPost(post.getPostId(), newBoard, null));
        postRepository.deleteById(result.getPostId());
        assertFalse(postRepository.existsById(result.getPostId()));

    }
}