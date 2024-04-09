package com.ung.apiboard;

import com.ung.apiboard.domain.member.Member;
import com.ung.apiboard.domain.member.RoleType;
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
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initTestAdmin();
    }
    public void initTestAdmin() {
        memberRepository.save(
                new Member("admin@admin.com", passwordEncoder.encode("admin1234!"), "웅", "관리자", RoleType.ROLE_ADMIN));
    }
}
