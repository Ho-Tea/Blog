package dev.be.blog.domain.board.service;

import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.dto.BoardResponse;
import dev.be.blog.domain.board.entity.Board;
import dev.be.blog.domain.board.repository.BoardRepository;
import dev.be.blog.domain.user.dto.UserAdapter;
import dev.be.blog.global.common.response.ResponseCode;
import dev.be.blog.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public BoardResponse create(BoardRequest request, UserAdapter userAdapter) {
        validateDuplicate(request.getBoardName());
        return BoardResponse.from(boardRepository.save(Board.builder().user(userAdapter.getUser()).boardName(request.getBoardName()).build()));
    }

    private void validateDuplicate(String boardName) {
        boardRepository.findByBoardName(boardName).ifPresent(o -> {
            throw new GlobalException(ResponseCode.ErrorCode.DUPLICATE_BOARD);
        });
    }

    ;

    @Override
    public Page<BoardResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardId").descending());
        return Optional.of(boardRepository.findAllByOrderByBoardIdDesc(pageRequest).map(BoardResponse::from)).orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_EXIST_BOARD));
    }

    @Override
    public BoardResponse findById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FOUND_BOARD));
        return BoardResponse.from(board);
    }

    @Override
    @Transactional
    public BoardResponse update(Long boardId, BoardRequest request, UserAdapter userAdapter) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FOUND_BOARD));
        validateWriter(board, userAdapter, ResponseCode.ErrorCode.NOT_AUTHORITY_UPDATE);
        board.update(request);
        return BoardResponse.from(board);
    }

    private void validateWriter(Board board, UserAdapter userAdapter, ResponseCode.ErrorCode errorCode) {
        if (!board.getUser().equals(userAdapter.getUser())) {
            throw new GlobalException(errorCode);
        }
    }

    @Override
    @Transactional
    public void delete(Long itemId, UserAdapter userAdapter) {
        Board board = boardRepository.findById(itemId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FOUND_BOARD));
        validateWriter(board, userAdapter, ResponseCode.ErrorCode.NOT_AUTHORITY_DELETE);
        boardRepository.delete(board);
    }

}
