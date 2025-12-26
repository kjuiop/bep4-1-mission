package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.domain.Product;
import com.back.global.rsdata.RsData;
import com.back.shared.member.dto.MarketMemberDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketFacade {

    private final MarketSupport marketSupport;
    private final MarketSyncMemberUseCase marketSyncMemberUseCase;
    private final MarketCreateProductUseCase marketCreateProductUseCase;
    private final MarketCreateCartUseCase marketCreateCartUseCase;
    private final MarketCreateOrderUseCase marketCreateOrderUseCase;

    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return marketSyncMemberUseCase.syncMember(member);
    }

    @Transactional(readOnly = true)
    public long productsCount() {
        return marketSupport.countProducts();
    }

    @Transactional
    public Product createProduct(
            String sourceTypeCode,
            long sourceId,
            String name,
            String description,
            int price,
            int salePrice,
            MarketMember seller
    ) {
        return marketCreateProductUseCase.saveProduct(
                seller,
                sourceTypeCode,
                sourceId,
                name,
                description,
                price,
                salePrice
        );
    }

    @Transactional
    public RsData<Cart> createCart(MarketMemberDto customer) {
        return marketCreateCartUseCase.createCart(customer);
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketSupport.findMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByCustomer(MarketMember customer) {
        return marketSupport.findCartByCustomer(customer);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(long id) {
        return marketSupport.findProductById(id);
    }

    @Transactional(readOnly = true)
    public long ordersCount() {
        return marketSupport.countOrders();
    }

    @Transactional(readOnly = true)
    public RsData<Order> createOrder(Cart cart) {
        return marketCreateOrderUseCase.createOrder(cart);
    }
}
