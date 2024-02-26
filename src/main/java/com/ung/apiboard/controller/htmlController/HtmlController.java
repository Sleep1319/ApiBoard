package com.ung.apiboard.controller.htmlController;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HtmlController {

    @GetMapping("/")
    public String index() {
        return "/Index";
    }

    @GetMapping("/sign-up")
    public String SignUp(HttpSession session) {
        if(session.getAttribute("email") != null){
            return "redirect:/";
        }
        return "/SignUp";
    }

    @GetMapping("/sign-in")
    public String signIn(HttpSession session) {
        if(session.getAttribute("email") != null){
            return "redirect:/";
        }
        return "/SignIn";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {
        session.invalidate();
        return "redirect:/sign-in";
    }
}
