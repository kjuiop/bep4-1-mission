package com.back.boundedcontext.post.service;

import com.back.boundedcontext.member.entity.Member;
import com.back.boundedcontext.post.entity.Post;
import com.back.boundedcontext.post.event.CommentCreatedEvent;
import com.back.boundedcontext.post.event.PostCreatedEvent;
import com.back.boundedcontext.post.repository.PostRepository;
import com.back.global.event.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public long count() {
        return postRepository.count();
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post write(Member author, String title, String content) {
        Post post = new Post(author, title, content);
        Post saved = postRepository.save(post);

        eventPublisher.publishEvent(new PostCreatedEvent(saved.getId(), title, content, ActivityType.POST_CREATED, author));
        return saved;
    }

    @Transactional
    public void writeComment(long postId, Member author, String content) {
        Post post = findById(postId).orElseThrow();
        post.addComment(author, content);
        postRepository.save(post);

        eventPublisher.publishEvent(new CommentCreatedEvent(post.getId(), postId, content, ActivityType.COMMENT_CREATED, author));
    }
}
