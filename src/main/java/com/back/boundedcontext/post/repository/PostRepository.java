package com.back.boundedcontext.post.repository;

import com.back.boundedcontext.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
