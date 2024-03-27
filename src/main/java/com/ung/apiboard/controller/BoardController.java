package com.ung.apiboard.controller;

import com.ung.apiboard.dto.board.BoardCreateRequest;
import com.ung.apiboard.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/board/new")
    public ResponseEntity<String> createBoard(@Valid @RequestBody BoardCreateRequest req) {
        String message;
        try {
            boardService.create(req);
        } catch (Exception e) {
            message = "등록중 에러 발생";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
