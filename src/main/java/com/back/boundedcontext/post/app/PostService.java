package com.back.boundedcontext.post.app;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.post.dto.PostCommentDto;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
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
    private final EventPublisher eventPublisher;

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

        eventPublisher.publish(
                new PostCreatedEvent(new PostDto(saved))
        );
        return saved;
    }

    @Transactional
    public void writeComment(long postId, Member author, String content) {
        Post post = findById(postId).orElseThrow();
        PostComment saved = post.addComment(author, content);
        postRepository.save(post);

        eventPublisher.publish(
                new PostCommentCreatedEvent(new PostCommentDto(saved))
        );
    }
}
