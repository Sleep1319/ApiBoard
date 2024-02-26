package com.ung.apiboard.controller.htmlController;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HtmlController {

    @GetMapping("/")
    public String index(@RequestParam(required = false, name = "message") String message, Model model, HttpSession session) {
        model.addAttribute("userInfo", session.getAttribute("userInfo"));
        model.addAttribute("message", message);
        return "/Index";
    }

    @GetMapping("/sign-up")
    public String SignUp(@RequestParam(required = false, name = "message") String message, Model model, HttpSession session) {
        if(session.getAttribute("email") != null){
            String signInUser = "이미 로그인이 되어 있습니다";
            return "redirect:/?message=" + signInUser;
        }
        if(message != null && !message.isEmpty()) {
        model.addAttribute("message", message);
        }
        return "/SignUp";
    }

    @GetMapping("/sign-in")
    public String signIn(@RequestParam(required = false, name = "message") String message , Model model, HttpSession session) {
        if(session.getAttribute("email") != null){
            String signInUser = "이미 로그인이 되어 있습니다";
            return "redirect:/?message=" + signInUser;
        }
        if(message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "/SignIn";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {
        session.invalidate();
        return "redirect:/sign-in";
    }
}
