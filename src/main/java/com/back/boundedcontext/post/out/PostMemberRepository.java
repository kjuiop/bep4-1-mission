package com.back.boundedcontext.post.out;

import com.back.boundedcontext.post.domain.PostMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : JAKE
 * @date : 25. 12. 24.
 */
public interface PostMemberRepository extends JpaRepository<PostMember, Long> {
}
