package com.ung.apiboard.service;

import com.ung.apiboard.domain.board.Comment;
import com.ung.apiboard.dto.board.CommentCreateRequest;
import com.ung.apiboard.exception.BoardNotFoundException;
import com.ung.apiboard.exception.MemberNotFoundException;
import com.ung.apiboard.repository.BoardRepository;
import com.ung.apiboard.repository.CommentRepository;
import com.ung.apiboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public List<Comment> findCommentByBoardId(Long boardId) {
        return commentRepository.findByBoardId_Id(boardId);
    }

    @Transactional
    public void create(CommentCreateRequest req) {
        commentRepository.save(CommentCreateRequest.toEntity(
                boardRepository.findById(req.getBoardId()).orElseThrow(BoardNotFoundException::new),
                memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new),
                req));
    }
}
