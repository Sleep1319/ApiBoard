package com.ung.apiboard.service;

import com.ung.apiboard.domain.board.Images;
import com.ung.apiboard.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final  ImagesRepository imagesRepository;

    public List<Images> findImages (Long boardId) {
        return imagesRepository.findByBoardId_Id(boardId);
    }
}
