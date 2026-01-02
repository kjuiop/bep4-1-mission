package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.CartRepository;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.global.config.GlobalConfig;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.global.rsdata.RsData;
import com.back.shared.job.dto.JobDto;
import com.back.shared.job.event.JobReadyInitEvent;
import com.back.shared.member.dto.MarketMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketCreateCartUseCase {

    private final MarketMemberRepository marketMemberRepository;
    private final CartRepository cartRepository;
    private final DomainEventPublisher eventPublisher;
    private final boolean useKafkaEvent;

    private boolean checkExecuteDataInit = true;

    public RsData<Cart> createCart(MarketMemberDto customer) {
        MarketMember _customer = marketMemberRepository.getReferenceById(customer.getId());

        Cart cart = new Cart(_customer);
        cartRepository.save(cart);

        if (useKafkaEvent && checkExecuteDataInit && isReadyInitData()) {
            eventPublisher.publish(new JobReadyInitEvent(JobDto.readyByCartForMarket()));
            checkExecuteDataInit = false;
        }

        return new RsData<>(
                "201-1",
                "장바귄가 생성되었습니다.",
                cart
        );
    }

    private boolean isReadyInitData() {
        MarketMember user1Member = marketMemberRepository.findByUsername("user1").get();
        MarketMember user2Member = marketMemberRepository.findByUsername("user2").get();
        MarketMember user3Member = marketMemberRepository.findByUsername("user3").get();

        Optional<Cart> user1Cart = cartRepository.findByCustomer(user1Member);
        Optional<Cart> user2Cart = cartRepository.findByCustomer(user2Member);
        Optional<Cart> user3Cart = cartRepository.findByCustomer(user3Member);

        return user1Cart.isPresent() && user2Cart.isPresent() && user3Cart.isPresent();
    }
}
