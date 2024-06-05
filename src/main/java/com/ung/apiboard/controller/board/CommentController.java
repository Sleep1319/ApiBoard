package com.ung.apiboard.controller.board;

import com.ung.apiboard.dto.board.CommentCreateRequest;
import com.ung.apiboard.dto.board.CommentDeleteRequest;
import com.ung.apiboard.exception.BoardNotFoundException;
import com.ung.apiboard.exception.CommentNotFoundException;
import com.ung.apiboard.exception.MemberNotFoundException;
import com.ung.apiboard.exception.UnauthorizedCommentAccessException;
import com.ung.apiboard.repository.CommentRepository;
import com.ung.apiboard.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment/new")
    public ResponseEntity<String> createComment(@Valid @RequestBody CommentCreateRequest req) {
        String message;
        try {
            commentService.create(req);
        } catch (BoardNotFoundException e) {
            message = "해당 게시글을 찾을 수 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (MemberNotFoundException e) {
            message = "유저 정보를 찾을 수 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/api/comment/delete")
    public ResponseEntity<String> deleteComment(@Valid @RequestBody CommentDeleteRequest req) {
        String message;
        try {
            commentService.delete(req);
        } catch (BoardNotFoundException e) {
            message = "해당 게시글을 찾을 수 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (MemberNotFoundException e) {
            message = "유저 정보를 찾을 수 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (CommentNotFoundException e) {
            message = "해당 댓글을 찾을 수 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (UnauthorizedCommentAccessException e) {
            message = "게시글 삭제 권환이 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "입력값이 올바르지 않습니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
