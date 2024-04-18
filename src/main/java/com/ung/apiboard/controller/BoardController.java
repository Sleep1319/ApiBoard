package com.ung.apiboard.controller;

import com.ung.apiboard.dto.board.BoardCreateRequest;
import com.ung.apiboard.dto.board.BoardUpdateRequest;
import com.ung.apiboard.exception.BoardNotFoundException;
import com.ung.apiboard.exception.FileStorageException;
import com.ung.apiboard.exception.UnauthorizedBoardAccessException;
import com.ung.apiboard.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/board/new")
    public ResponseEntity<String> createBoard(@Valid @RequestBody BoardCreateRequest req) {
        String message;
        try {
            boardService.create(req);
        } catch (FileStorageException e) {
            message = "파일 등록 에러";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (Exception e) {
            message = "등록중 에러 발생";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/api/board/edit")
    private ResponseEntity<String> updateBoard(@Valid @RequestBody BoardUpdateRequest req) {
        String message;
        try {
            boardService.update(req);
        } catch (BoardNotFoundException e) {
            message = "게시글을 찾을 수 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (UnauthorizedBoardAccessException e) {
            message = "게시글 수정 권한이 없습니다.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
