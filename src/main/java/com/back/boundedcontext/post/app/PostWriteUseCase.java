package com.back.boundedcontext.post.app;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.member.out.apiclient.MemberApiClient;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.global.rsdata.RsData;
import com.back.shared.post.dto.PostCommentDto;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Service
@RequiredArgsConstructor
public class PostWriteUseCase {

    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;
    private final MemberApiClient memberApiClient;

    @Transactional
    public RsData<Post> write(Member author, String title, String content) {
        Post post = new Post(author, title, content);
        Post saved = postRepository.save(post);

        eventPublisher.publish(
                new PostCreatedEvent(new PostDto(saved))
        );
        return new RsData<>("201-1", "%d번 글이 생성되었습니다.".formatted(post.getId()), post);
    }

    @Transactional
    public RsData<PostComment> writeComment(Post post, Member author, String content) {
        PostComment comment = post.addComment(author, content);
        postRepository.save(post);

        eventPublisher.publish(
                new PostCommentCreatedEvent(new PostCommentDto(comment))
        );

        String randomSecureTip = memberApiClient.getRandomSecureTip();

        return new RsData<>("201-1",
                "%d번 댓글이 생성되었습니다. 보안 팁 : %s".formatted(comment.getId(), randomSecureTip), comment);
    }
}
