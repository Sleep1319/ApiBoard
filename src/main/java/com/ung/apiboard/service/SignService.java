package com.ung.apiboard.service;

import com.ung.apiboard.domain.member.Member;
import com.ung.apiboard.domain.member.MemberRole;
import com.ung.apiboard.domain.member.RoleType;
import com.ung.apiboard.dto.sign.SignInRequest;
import com.ung.apiboard.dto.sign.SignInResponse;
import com.ung.apiboard.dto.sign.SignUpRequest;
import com.ung.apiboard.exception.LoginFailureException;
import com.ung.apiboard.exception.MemberEmailAlreadyExistsException;
import com.ung.apiboard.exception.MemberNicknameAlreadyExistsException;
import com.ung.apiboard.exception.RoleNotFoundException;
import com.ung.apiboard.repository.MemberRepository;
import com.ung.apiboard.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    @Transactional
    public void singUp(SignUpRequest req) {
        validateSingUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(req,
                roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new)));
    }

    private void validateSingUpInfo(SignUpRequest req) {
        if(memberRepository.existsByEmail(req.getEmail())) {
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        }
        if(memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    @Transactional
    public SignInResponse signIn (SignInRequest req) {
    Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
    validateSignInPassword(req, member);
    return new SignInResponse(member.getEmail(), member.getUsername(), member.getNickname(), member.getRole().toString());
    }

    private void validateSignInPassword(SignInRequest req, Member member) {
        if(!Objects.equals(member.getPassword(), req.getPassword())) {
            throw new LoginFailureException();
        }
    }

}
