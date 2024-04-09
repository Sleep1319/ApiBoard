package com.ung.apiboard.repository;

import com.ung.apiboard.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("UPDATE Board b SET b.title = :title, b.content = :content WHERE b.id = :id")
    void updateBoard(@Param("id") Long id, @Param("title") String title, @Param("content") String content);
}
