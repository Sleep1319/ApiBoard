package com.ung.apiboard.controller.htmlController;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.domain.board.Comment;
import com.ung.apiboard.service.BoardService;
import com.ung.apiboard.service.CommentService;
import jakarta.servlet.http.HttpSession;
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
    private final CommentService commentService;

    @GetMapping("/")
    public String index(@RequestParam(required = false, name = "message") String message, Model model, HttpSession session) {
        List<Board> boardList = boardService.allBoard();
        model.addAttribute("boardList", boardList);
        model.addAttribute("userInfo", session.getAttribute("userInfo"));
        model.addAttribute("message", message);
        return "index";
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
        return "signUp";
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
        return "signIn";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) throws UnsupportedEncodingException {
        session.invalidate();
        String signOut = "로그아웃 성공";
        String encodedSignOutUser = URLEncoder.encode(signOut, "UTF-8");
        return "redirect:/sign-in?message=" + encodedSignOutUser;
    }

    //게시판
    @GetMapping("/board/show/{id}")
    public String selectBoard(@PathVariable Long id, Model model) {
        Optional<Board> findBoard = boardService.selectBoard(id);
        List<Comment> commentList = commentService.findCommentByBoardId(id);
        Board board = findBoard.orElse(null);
        model.addAttribute("board", board);
        model.addAttribute("commentList", commentList);
        return "show";
    }

    @GetMapping("/board/new")
    public String createBoard(HttpSession session) throws UnsupportedEncodingException {
        if(session.getAttribute("userInfo") == null){
            String signInUser = "로그인후 이용 가능합니다";
            String encodedSignInUser = URLEncoder.encode(signInUser, "UTF-8");
            return "redirect:/sign-in?message=" + encodedSignInUser;
        }
        return "new";
    }
}
