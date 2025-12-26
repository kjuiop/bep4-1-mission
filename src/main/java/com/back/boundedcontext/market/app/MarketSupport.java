package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.Cart;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.domain.Product;
import com.back.boundedcontext.market.out.CartRepository;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.boundedcontext.market.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketSupport {

    private final ProductRepository productRepository;
    private final MarketMemberRepository marketMemberRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public long countProducts() {
        return productRepository.count();
    }

    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketMemberRepository.findByUsername(username);
    }

    public Optional<Cart> findCartByCustomer(MarketMember customer) {
        return cartRepository.findByCustomer(customer);
    }

    public Optional<Product> findProductById(long id) {
        return productRepository.findById(id);
    }

    public long countOrders() {
        return orderRepository.count();
    }
}
