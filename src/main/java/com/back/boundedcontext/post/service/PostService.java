package com.back.boundedcontext.post.service;

import com.back.boundedcontext.member.entity.Member;
import com.back.boundedcontext.post.entity.Post;
import com.back.boundedcontext.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public long count() {
        return postRepository.count();
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    public Post write(Member author, String title, String content) {
        Post post = new Post(author, title, content);
        author.increaseActivityScore(3);
        return postRepository.save(post);
    }
}
