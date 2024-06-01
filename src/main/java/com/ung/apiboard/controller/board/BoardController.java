package com.ung.apiboard.controller.board;

import com.ung.apiboard.dto.board.BoardCreateRequest;
import com.ung.apiboard.dto.board.BoardDeleteRequest;
import com.ung.apiboard.dto.board.BoardUpdateRequest;
import com.ung.apiboard.exception.BoardNotFoundException;
import com.ung.apiboard.exception.FileStorageException;
import com.ung.apiboard.exception.PostDeleteForbiddenException;
import com.ung.apiboard.exception.UnauthorizedBoardAccessException;
import com.ung.apiboard.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //form형식 요청
//    @PostMapping("/board/new")
//    public String newBoard(@RequestParam Long memberId,
//                           @RequestParam String title,
//                           @RequestParam String content,
//                           @RequestParam("files") List<MultipartFile> files) throws IOException {
//        String message = null;
//        //DTO이용으로 연결 되어 있기에 테스트 일단 DTO클래스에 담아서 서비스로 이동
//        BoardCreateRequest req = new BoardCreateRequest(memberId, title, content);
//        try {
//            boardService.create(req, files);
//        } catch (FileStorageException e) {
//            message = "파일 등록 에러";
//            String encodedMessage = URLEncoder.encode(message, "UTF-8");
//            return "redirect:/board/new?message=" + encodedMessage;
//        } catch (Exception e) {
//            message = "등록중 에러 발생";
//            String encodedMessage = URLEncoder.encode(message, "UTF-8");
//            return "redirect:/board/new?message=" + encodedMessage;
//        }
//        message = "등록 성공";
//        String encodedMessage = URLEncoder.encode(message, "UTF-8");
//        return "redirect:/?message=" + encodedMessage;
//    }

    @PostMapping("/api/board/new")
    public ResponseEntity<String> newBoard(@RequestParam Long memberId,
                                           @RequestParam String title,
                                           @RequestParam String content,
                                           @RequestParam("files") List<MultipartFile> files) throws IOException {
        String message;
        try {
            BoardCreateRequest req = new BoardCreateRequest(memberId, title, content);
            boardService.create(req, files);
            message = "등록 성공";
            return ResponseEntity.ok()
//                    .cacheControl(CacheControl.noCache()) // 캐시 무효화 설정
                    .body(message);
        } catch (FileStorageException e) {
            message = "파일 등록 에러";
        } catch (Exception e) {
            message = "등록 중 에러 발생";
        }
        return ResponseEntity.badRequest()
//                .cacheControl(CacheControl.noCache()) // 캐시 무효화 설정
                .body(message);
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

    @DeleteMapping("/api/board/delete")
    private ResponseEntity<String> deleteBoard(@Valid @RequestBody BoardDeleteRequest req) {
        String message;
        try {
            boardService.delete(req);
        } catch (BoardNotFoundException e) {
            message = "게시글을 찾을 수 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (UnauthorizedBoardAccessException e) {
            message = "게시글 삭제 권환이 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
