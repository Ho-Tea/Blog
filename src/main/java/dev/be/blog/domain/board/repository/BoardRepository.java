package dev.be.blog.domain.board.repository;

import dev.be.blog.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardName(@Param("boardName") String boardName);


    Page<Board> findAllByOrderByBoardIdDesc(Pageable pageable);
}
