package com.ung.apiboard.controller;


import com.ung.apiboard.dto.sign.SignInRequest;
import com.ung.apiboard.dto.sign.SignInResponse;
import com.ung.apiboard.dto.sign.SignUpRequest;
import com.ung.apiboard.service.SignService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        try {
            signService.singUp(req);
            return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/sign-in").build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Location", "/").build();
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Location", "/sign-in").build();
        }
    }
}
