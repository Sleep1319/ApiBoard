package com.ung.apiboard.repository;

import com.ung.apiboard.domain.member.Role;
import com.ung.apiboard.domain.member.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
