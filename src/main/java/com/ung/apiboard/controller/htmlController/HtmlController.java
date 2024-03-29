package com.ung.apiboard.controller.htmlController;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HtmlController {
    private final BoardService boardService;
    @GetMapping("/")
    public String index(@RequestParam(required = false, name = "message") String message, Model model, HttpSession session) {
        model.addAttribute("userInfo", session.getAttribute("userInfo"));
        model.addAttribute("message", message);
        return "/Index";
    }

    @GetMapping("/sign-up")
    public String SignUp(@RequestParam(required = false, name = "message") String message, Model model, HttpSession session) throws UnsupportedEncodingException {
        if(session.getAttribute("userInfo") != null){
            String signInUser = "이미 로그인이 되어 있습니다";
            String encodedSignInUser = URLEncoder.encode(signInUser, "UTF-8");
            return "redirect:/?message=" + encodedSignInUser;
        }
        if(message != null && !message.isEmpty()) {
        model.addAttribute("message", message);
        }
        return "/SignUp";
    }

    @GetMapping("/sign-in")
    public String signIn(@RequestParam(required = false, name = "message") String message , Model model, HttpSession session) throws UnsupportedEncodingException {
        if(session.getAttribute("userInfo") != null){
            String signInUser = "이미 로그인이 되어 있습니다";
            String encodedSignInUser = URLEncoder.encode(signInUser, "UTF-8");
            return "redirect:/?message=" + encodedSignInUser;
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

    //게시판
    @GetMapping("/board")
    public String board(Model model) {
        List<Board> boardList = boardService.allBoard();
        model.addAttribute("boardList", boardList);
        return "/Board";
    }

    @GetMapping("/board/show/{id}")
    public String selectBoard(@PathVariable Long id, Model model) {
        Optional<Board> findBoard = boardService.selectBoard(id);
        Board board = findBoard.orElse(null);
        model.addAttribute("board", board);
        return "/Show";
    }

    @GetMapping("/board/new")
    public String createBoard(HttpSession session) throws UnsupportedEncodingException {
        if(session.getAttribute("userInfo") == null){
            String signInUser = "로그인후 이용 가능합니다";
            String encodedSignInUser = URLEncoder.encode(signInUser, "UTF-8");
            return "redirect:/sign-in?message=" + encodedSignInUser;
        }
        return "/New";
    }
}
