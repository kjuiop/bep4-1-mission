package com.back.boundedcontext.post.out;

import com.back.boundedcontext.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByOrderByIdDesc();
    Optional<Post> findByTitle(String title);
}
