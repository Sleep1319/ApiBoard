package com.ung.apiboard.controller;


import com.ung.apiboard.dto.sign.SignInRequest;
import com.ung.apiboard.dto.sign.SignInResponse;
import com.ung.apiboard.dto.sign.SignUpRequest;
import com.ung.apiboard.exception.DuplicateEmailException;
import com.ung.apiboard.exception.DuplicateNickNameException;
import com.ung.apiboard.exception.LoginFailureException;
import com.ung.apiboard.service.SignService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest req) {
        String message;
        try {
            signService.singUp(req);
            message = "회원가입 성공";
            return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/sign-in?message=" + message).build();
        }
        catch (Exception e) {
            if(e instanceof DuplicateEmailException){
                message = "이미 가입된 이메일 입니다.";
            } else if (e instanceof DuplicateNickNameException) {
                message = "이미 사용중인 닉네임 입니다.";
            } else {
                message = "유효하지 않은 요청입니다.";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Location", "sign-up?message=" + message).build();
        }
    }
    @PostMapping("/api/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> signIn(@Valid @RequestBody SignInRequest req, HttpSession session) {
        try {
            SignInResponse res = signService.signIn(req);
            session.setAttribute("userInfo", res);//필요한 정보 개수가 변동될 가능성 있음, 하나로 통합
            return ResponseEntity.status(HttpStatus.OK).header("Location", "/").build();
        } catch (Exception e) {
            String message;
            if(e instanceof LoginFailureException) {
                message = "아이디 또는 비밀번호가 다릅니다.";
            } else {
                message = "유효하지 않은 요청입니다.";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Location", "/sign-in?message=" + message).build();
        }
    }
}
