package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
    private final MarketMemberRepository marketMemberRepository;

    public MarketMember syncMember(MemberDto member) {
        MarketMember marketMember = new MarketMember(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );

        return marketMemberRepository.save(marketMember);
    }
}
