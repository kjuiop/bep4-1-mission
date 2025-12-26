package com.back.boundedcontext.market.out;

import com.back.boundedcontext.market.domain.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
