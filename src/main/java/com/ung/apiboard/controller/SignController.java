package com.ung.apiboard.controller;


import com.ung.apiboard.dto.sign.SignInRequest;
import com.ung.apiboard.dto.sign.SignInResponse;
import com.ung.apiboard.dto.sign.SignUpRequest;
import com.ung.apiboard.exception.*;
import com.ung.apiboard.service.SignService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * 클라이언트에서 처리 해야할 부분도 현제 컨트롤러에서 부담 하고 있음
 * 단순한 알림 내역이나 이동은 클라이언트에서 처리 하는 방향으로 변경 필요
 * ex)회원 가입 실패시 서버에서 겟 컨트롤러에 다시 요청을 보내서 돌아오는 상황이다
 */
@RestController
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest req) {
        String message;
        try {
            signService.singUp(req);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (Exception e) {
            if(e instanceof MemberEmailAlreadyExistsException){
                message = "이미 가입된 이메일 입니다.";
            } else if (e instanceof MemberNicknameAlreadyExistsException) {
                message = "이미 사용중인 닉네임 입니다.";
            } else {
                message = "유효하지 않은 요청 입니다.";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
    
    //@Valid에서 발생하는 예외를 처리하기 위해 따로 생성 추후 예외처리 전용 클래스 생성
    //@Valid에서 발생하는 예외는 스프링에서 먼저 처리하기에 내가 위 캐치문처럼 수정할 수 없음
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "입력값이 올바르지 않습니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PostMapping("/api/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> signIn(@Valid @RequestBody SignInRequest req, HttpSession session) {
        try {
            SignInResponse res = signService.signIn(req);
            session.setAttribute("userInfo", res);//필요한 정보 개수가 변동될 가능성 있음, 하나로 통합
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            String message;
            if(e instanceof LoginFailureException) {
                message = "아이디 또는 비밀번호가 다릅니다.";
            } else {
                message = "유효하지 않은 요청입니다.";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
}
