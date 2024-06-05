package com.ung.apiboard.service;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.domain.board.Images;
import com.ung.apiboard.dto.board.BoardCreateRequest;
import com.ung.apiboard.dto.board.BoardDeleteRequest;
import com.ung.apiboard.dto.board.BoardUpdateRequest;
import com.ung.apiboard.exception.*;
import com.ung.apiboard.repository.BoardRepository;
import com.ung.apiboard.repository.ImagesRepository;
import com.ung.apiboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ImagesRepository imagesRepository;
//    private final FileStorageService fileStorageService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    public void create(BoardCreateRequest req, List<MultipartFile> files) throws IOException {
        Long boardId = boardRepository.save(
                BoardCreateRequest.toEntity(
                        memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new), req)).getId();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) { // 각 파일이 비어 있지 않은지 확인
                    String originalFilename = file.getOriginalFilename();
                    String storedFileName = generateFileName(originalFilename);
                    String fullPath = uploadDir + "/" + storedFileName; // 따로 yml경로 수정 필요
                    file.transferTo(new File(fullPath));

                    Images images = new Images(
                            boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new),
                            originalFilename,
                            storedFileName,
                            fullPath
                    );
                    imagesRepository.save(images);
                }
            }
        }
    }
    // 실저장 이름이랑 디비 저장 이름 구분 필요
    private String generateFileName(String  fileName) {
        return UUID.randomUUID() + "_" + fileName;
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
        validateBoard(req.getMemberId(), board);
        boardRepository.updateBoard(board.getId(), req.getContent());
    }

    @Transactional
    public void delete(BoardDeleteRequest req) {
        Board board = boardRepository.findById(req.getBoardId()).orElseThrow(BoardNotFoundException::new);
        validateBoard(req.getMemberId(), board);
        List<Images> imagesList = imagesRepository.findByBoardId_Id(board.getId());
        if(!imagesList.isEmpty()) {
            for (Images images : imagesList) {
                File file = new File(images.getFullPath());
                if (file.exists()) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("파일 삭제 성공, 디비 기록 삭제");
                        imagesRepository.deleteById(images.getId());
                    } else {
                        System.out.println("파일 삭제 실패, 번호: " + images.getId() + ", 실 저장 이름: " + images.getStoredFileName());
                    }
                } else {
                    System.out.println("해당 파일 없음");
                }
            }
            System.out.println("저장된 이미지가 없음");
        }
        boardRepository.deleteById(board.getId());
    }

    private void validateBoard(Long memberId, Board board) {
        if(!Objects.equals(memberId, board.getMember().getId())) {
            throw new UnauthorizedBoardAccessException();
        }
    }

}
