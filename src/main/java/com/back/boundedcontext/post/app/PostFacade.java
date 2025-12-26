package com.back.boundedcontext.post.app;

import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Service
@RequiredArgsConstructor
public class PostFacade {

    private final PostWriteUseCase postWriteUseCase;
    private final PostSupport postSupport;
    private final PostSyncMemberUseCase postSyncMemberUseCase;

    @Transactional
    public RsData<Post> write(PostMember author, String title, String content) {
        return postWriteUseCase.write(author, title, content);
    }

    @Transactional
    public RsData<PostComment> writeComment(Post post, PostMember author, String content) {
        return postWriteUseCase.writeComment(post, author, content);
    }

    @Transactional
    public PostMember syncMember(MemberDto member) {
        return postSyncMemberUseCase.syncMember(member);
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(long id) {
        return postSupport.findById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return postSupport.count();
    }

    @Transactional(readOnly = true)
    public Optional<PostMember> findMemberByUsername(String username) {
        return postSupport.findPostMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Post> findByOrderByIdDesc() {
        return postSupport.findByOrderByIdDesc();
    }
}
