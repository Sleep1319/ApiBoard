package com.ung.apiboard.dto.sign;

import com.ung.apiboard.domain.member.MemberRole;
import com.ung.apiboard.domain.member.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SignInResponse implements Serializable {
    private Long id;
    private String email;
    private String username;
    private String nickname;
    private String roleName;
}
