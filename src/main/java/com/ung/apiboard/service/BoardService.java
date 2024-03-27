package com.ung.apiboard.service;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.dto.board.BoardCreateRequest;
import com.ung.apiboard.exception.MemberNotFoundException;
import com.ung.apiboard.repository.BoardRepository;
import com.ung.apiboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public void create(BoardCreateRequest req) {
        boardRepository.save(BoardCreateRequest.toEntity(memberRepository.findById(req.getId()).orElseThrow(MemberNotFoundException :: new), req));

    }


    public List<Board> allBoard() {
        return boardRepository.findAll();
    }

    public Optional<Board> selectBoard(Long id) {
        return boardRepository.findById(id);
    }
}
