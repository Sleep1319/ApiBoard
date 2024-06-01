package com.ung.apiboard.repository;

import com.ung.apiboard.domain.board.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByBoardId_Id(Long boardId);
}
