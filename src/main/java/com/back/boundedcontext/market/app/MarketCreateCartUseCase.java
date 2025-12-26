package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.CartRepository;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MarketMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketCreateCartUseCase {

    private final MarketMemberRepository marketMemberRepository;
    private final CartRepository cartRepository;

    public RsData<Cart> createCart(MarketMemberDto customer) {
        MarketMember _customer = marketMemberRepository.getReferenceById(customer.getId());

        Cart cart = new Cart(_customer);
        cartRepository.save(cart);

        return new RsData<>(
                "201-1",
                "장바귄가 생성되었습니다.",
                cart
        );
    }
}
