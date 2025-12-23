package com.back.repository;

import com.back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
