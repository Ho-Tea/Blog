package dev.be.blog.domain.board.repository;

import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.user.dto.UserAdapter;
import dev.be.blog.domain.user.entity.Users;
import dev.be.blog.domain.user.repository.UserRepository;
import dev.be.blog.global.common.vo.Authority;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 데이터베이스를 사용하지 않음
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    private static Board board;
    private static String NAME = "boardName";
    private static Users user;


    @BeforeAll
    private static void setUp(){
        board = Board.builder().boardName(NAME).build();
        user = Users.builder()
                .id(1L)
                .email("Valid@Valid")
                .password("12345678")
                .nickName("TEST")
                .authority(Authority.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("Repository - save 메서드 검증")
    void save(){
        Board result = boardRepository.save(board);
        assertThat(result.getBoardName()).isEqualTo(NAME);
    }

    @Test
    @DisplayName("Repository - findByBoardName 메서드 검증")
    void findByBoardName(){
        Board newBoard = boardRepository.save(board);
        Optional<Board> result = boardRepository.findByBoardName(newBoard.getBoardName());

        assertThat(result.get().getBoardName()).isEqualTo(board.getBoardName());

        assertThat(result.get().getBoardId()).isEqualTo(newBoard.getBoardId());
    }

    @Test
    @DisplayName("Repository - findById 메서드 검증")
    void findById(){
        Board newBoard = boardRepository.save(board);
        Optional<Board> result = boardRepository.findById(newBoard.getBoardId());
        assertThat(result.get().getBoardId()).isEqualTo(board.getBoardId());
        assertThat(result.get().getBoardName()).isEqualTo(board.getBoardName());
    }

    @Test
    @DisplayName("Repository - findAllByOrderByBoardIdDesc 메서드 검증")
    void findAllByOrderByBoardIdDesc(){
        int page = 1;
        int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size);
        userRepository.save(user);
        for(int i = 1 ; i <= 3; i++){
            boardRepository.save(new Board((long) i, String.valueOf(i), user));
        }
        Page<Board> result = boardRepository.findAllByOrderByBoardIdDesc(pageRequest);
        assertThat(result.getNumber()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
    }

    @Test
    @DisplayName("Repository - deleteById 메서드 검증")
    void deleteById(){
        Board result = boardRepository.save(board);
        boardRepository.deleteById(result.getBoardId());
        assertFalse(boardRepository.existsById(result.getBoardId()));

    }

}