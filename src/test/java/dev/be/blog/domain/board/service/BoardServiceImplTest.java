package dev.be.blog.domain.board.service;

import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.dto.BoardResponse;
import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.board.repository.BoardRepository;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static dev.be.blog.global.common.response.ResponseCode.ErrorCode.NOT_FOUND_BOARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardServiceImpl boardService;
    @Mock
    private UserAdapter userAdapter;

    private static BoardRequest validObject;
    private static Users user;


    @BeforeAll
    private static void setUp() {
        validObject = new BoardRequest("새로운 보드");
        user = Users.builder()
                .email("Valid@Valid")
                .password("12345678")
                .authority(Authority.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("보드 생성에 성공한다")
    public void create() {
        // Given
        Long boardId = 1L;
        Board board = Board.builder().id(boardId).boardName(validObject.getBoardName()).user(user).build();
        // When
        when(userAdapter.getUser()).thenReturn(user);
        when(boardRepository.save(any(Board.class))).thenReturn(board);
        // Then
        assertThat(boardService.create(validObject, userAdapter).getBoardId()).isEqualTo(boardId);
    }

    @Test
    @DisplayName("중복된 보드의 생성은 실패한다")
    public void failCreate() {
        // Given
        // When
        when(boardRepository.findByBoardName(validObject.getBoardName())).thenThrow(new GlobalException(ResponseCode.ErrorCode.DUPLICATE_BOARD));
        // Then
        assertThrows(GlobalException.class, () -> boardService.create(validObject, userAdapter));
    }

    @Test
    @DisplayName("모든 보드 조희를 성공한다")
    public void findAll() {
        // Given
        int page = 1;
        int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardId").descending());
        List<Board> boards = new ArrayList<>();
        for(int i = 1 ; i <= 3; i++){
            boards.add(new Board(Long.valueOf(i), String.valueOf(i), user));
        }
        Page<Board> pages = new PageImpl<>(boards);
        // When
        when(boardRepository.findAllByOrderByBoardIdDesc(pageRequest)).thenReturn(pages);
        // Then
        assertThat(boardService.findAll(page, size).stream().map(o -> o.getBoardId()).toArray())
                .containsExactly(boards.stream().map(o -> o.getBoardId()).toArray());
        assertThat(boardService.findAll(page, size).stream().map(o -> o.getBoardName()).toArray())
                .containsExactly(boards.stream().map(o -> o.getBoardName()).toArray());
    }

    @Test
    @DisplayName("보드가 하나도 없는 경우 모든 보드 조희를 실패한다")
    public void failFindAll() {
        // Given
        int page = 1;
        int size = 3;
        // When
        when(boardRepository.findAllByOrderByBoardIdDesc(any())).thenThrow(new GlobalException(ResponseCode.ErrorCode.NOT_EXIST_BOARD));
        // Then
        assertThrows(GlobalException.class, () -> boardService.findAll(page, size));
    }

    @Test
    @DisplayName("특정 보드 조회에 성공한다")
    public void findById() {
        // Given
        Long boardId = 1L;
        Board board = new Board(boardId, validObject.getBoardName(), user);
        // When
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        // Then
        assertThat(boardService.findById(boardId).getBoardId()).isEqualTo(boardId);
        assertThat(boardService.findById(boardId).getBoardName()).isEqualTo(validObject.getBoardName());
    }

    @Test
    @DisplayName("존재하지 않는 특정 보드 조회에 실패한다")
    public void failFindById() {
        // Given
        Long boardId = 1L;
        // When
        when(boardRepository.findById(boardId)).thenThrow(new GlobalException(NOT_FOUND_BOARD));
        // Then
        assertThrows(GlobalException.class, () -> boardService.findById(boardId).getBoardId());

    }


    @Test
    @DisplayName("특정 보드 수정에 성공한다")
    public void update() {
        // Given
        Long boardId = 1L;
        Board board = new Board(boardId, validObject.getBoardName(), user);
        BoardRequest newBoard = new BoardRequest("새로운 보드 이름");
        // When
        when(userAdapter.getUser()).thenReturn(user);
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        // Then
        assertThat(boardService.update(boardId, newBoard, userAdapter).getBoardName()).isEqualTo(newBoard.getBoardName());

    }

    @Test
    @DisplayName("특정 보드가 존재하지 않다면 수정에 실패한다")
    public void failUpdate() {
        // Given
        Long boardId = 1L;
        BoardRequest boardRequest = new BoardRequest("새로운 보드 이름");
        // When
        when(boardRepository.findById(boardId)).thenThrow(new GlobalException(ResponseCode.ErrorCode.NOT_FOUND_BOARD));
        // Then
        assertThrows(GlobalException.class, () -> boardService.update(boardId, boardRequest, userAdapter));

    }

    @Test
    @DisplayName("특정 보드 삭제에 성공한다")
    public void delete() {
        // Given
        Long boardId = 1L;
        // 모의 객체 생성
        BoardServiceImpl boardServiceImpl = mock(BoardServiceImpl.class);
        // When
        boardServiceImpl.delete(boardId, userAdapter);
        // Then
        verify(boardServiceImpl).delete(boardId, userAdapter);

    }

    @Test
    @DisplayName("특정 보드가 존재하지 않는 경우 특정보드 삭제에 실패한다")
    public void failDelete() {
        // Given
        Long boardId = 1L;
        // When
//        doThrow(new GlobalException(NOT_FOUND_BOARD)).when(boardRepository).deleteById(boardId);
        // Then
        assertThrows(GlobalException.class, () -> boardService.delete(boardId, userAdapter));

    }




}