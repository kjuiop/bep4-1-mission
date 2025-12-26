package com.back.boundedcontext.market.out;

import com.back.boundedcontext.market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
