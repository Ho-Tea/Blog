package dev.be.blog.domain.board.service;

import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.dto.BoardResponse;
import dev.be.blog.domain.user.dto.UserAdapter;
import org.springframework.data.domain.Page;

public interface BoardService {
    BoardResponse create(BoardRequest request, UserAdapter userAdapter);
    Page<BoardResponse> findAll(int page, int size);
    BoardResponse findById(Long boardId);
    BoardResponse update(Long boardId, BoardRequest request, UserAdapter userAdapter);
    void delete(Long boardId, UserAdapter userAdapter);

}
