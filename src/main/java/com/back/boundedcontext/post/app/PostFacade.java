package com.back.boundedcontext.post.app;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.boundedcontext.post.out.PostMemberRepository;
import com.back.boundedcontext.post.out.PostRepository;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MemberDto;
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
public class PostFacade {

    private final PostRepository postRepository;
    private final PostWriteUseCase postWriteUseCase;
    private final PostMemberRepository postMemberRepository;

    @Transactional(readOnly = true)
    public long count() {
        return postRepository.count();
    }

    @Transactional
    public RsData<Post> write(Member author, String title, String content) {
        return postWriteUseCase.write(author, title, content);
    }

    @Transactional
    public RsData<PostComment> writeComment(Post post, Member author, String content) {
        return postWriteUseCase.writeComment(post, author, content);
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    public PostMember syncMember(MemberDto member) {
        PostMember postMember = new PostMember(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );

        postMember.setId(member.getId());
        postMember.setCreateDate(member.getCreateDate());
        postMember.setModifyDate(member.getModifyDate());

        return postMemberRepository.save(postMember);
    }
}
