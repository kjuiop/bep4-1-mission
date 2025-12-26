package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.domain.Product;
import com.back.boundedcontext.market.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketCreateProductUseCase {

    private final ProductRepository productRepository;

    public Product saveProduct(
            MarketMember seller,
            String sourceTypeCode,
            long sourceId,
            String name,
            String description,
            int price,
            int salePrice
    ) {
        Product product = new Product(
                sourceTypeCode,
                sourceId,
                name,
                description,
                price,
                salePrice,
                seller
        );
        return productRepository.save(product);
    }
}
