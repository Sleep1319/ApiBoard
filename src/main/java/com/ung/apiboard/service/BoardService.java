package com.ung.apiboard.service;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.dto.board.BoardCreateRequest;
import com.ung.apiboard.dto.board.BoardUpdateRequest;
import com.ung.apiboard.exception.BoardNotFoundException;
import com.ung.apiboard.exception.LoginFailureException;
import com.ung.apiboard.exception.MemberNotFoundException;
import com.ung.apiboard.exception.UnauthorizedBoardAccessException;
import com.ung.apiboard.repository.BoardRepository;
import com.ung.apiboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileStorageService fileStorageService;

    public void create(BoardCreateRequest req) {
        List<String> filePaths = new ArrayList<>();
        List<MultipartFile> files = req.getFiles();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String filePath = fileStorageService.storeFile(file);
                filePaths.add(filePath);
            }
        }
        boardRepository.save(
                BoardCreateRequest.toEntity(
                        memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException :: new), req, filePaths));
    }


    public List<Board> allBoard() {
        return boardRepository.findAll();
    }

    public Optional<Board> selectBoard(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public void update(BoardUpdateRequest req) {
        Board board = boardRepository.findById(req.getBoardId()).orElseThrow(BoardNotFoundException::new);
        validateBoard(req, board);
        boardRepository.updateBoard(board.getId(), req.getTitle(), req.getContent());
    }

    private void validateBoard(BoardUpdateRequest req, Board board) {
        if(!Objects.equals(req.getMemberId(), board.getMember().getId())) {
            throw new UnauthorizedBoardAccessException();
        }

    }
}
