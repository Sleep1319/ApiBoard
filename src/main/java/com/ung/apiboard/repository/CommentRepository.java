package com.ung.apiboard.repository;

import com.ung.apiboard.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId_Id(Long boardId);
}
