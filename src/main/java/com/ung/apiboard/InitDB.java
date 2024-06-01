package com.ung.apiboard;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.domain.member.Member;
import com.ung.apiboard.domain.member.RoleType;
import com.ung.apiboard.exception.MemberNotFoundException;
import com.ung.apiboard.repository.BoardRepository;
import com.ung.apiboard.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initTestAdmin();
        initTestBoard();
    }
    public void initTestAdmin() {
        memberRepository.save(
                new Member("admin@admin.com", passwordEncoder.encode("admin1234!"), "관리자이름", "관리자", RoleType.ROLE_ADMIN));
        memberRepository.save(
                new Member("sleep1205@gmail.com", passwordEncoder.encode("sleep1205!"), "웅", "Sleep", RoleType.ROLE_NORMAL));
    }

    public void initTestBoard() {
        boardRepository.save( new Board(memberRepository.findById(1L).orElseThrow(MemberNotFoundException :: new), "테스트 제목", "테스트 내용"));
    }
}
