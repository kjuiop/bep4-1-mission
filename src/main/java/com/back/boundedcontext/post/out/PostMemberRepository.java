package com.back.boundedcontext.post.out;

import com.back.boundedcontext.post.domain.PostMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 24.
 */
public interface PostMemberRepository extends JpaRepository<PostMember, Long> {
    Optional<PostMember> findByUsername(String username);
}
