package com.back.boundedcontext.cash.in;

import com.back.boundedcontext.cash.app.CashFacade;
import com.back.boundedcontext.cash.domain.CashLog;
import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@ConditionalOnProperty(name = "app.data-init.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@Slf4j
public class CashDataInit {

    private final CashDataInit self;
    private final CashFacade cashFacade;

    @Value("${app.event-publisher.type}")
    private String publisherType;

    public CashDataInit(
            @Lazy CashDataInit self,
            CashFacade cashFacade
    ) {
        this.self = self;
        this.cashFacade = cashFacade;
    }

    @Bean
    @Order(2)
    public ApplicationRunner cashDataInitApplicationRunner() {
        return args -> {

            if (!publisherType.equalsIgnoreCase("spring")) {
                return;
            }

            self.makeBaseCredits();
        };
    }

    @Transactional
    public void makeBaseCredits() {
        CashMember user1Member = cashFacade.findMemberByUsername("user1").get();
        CashMember user2Member = cashFacade.findMemberByUsername("user2").get();
        CashMember user3Member = cashFacade.findMemberByUsername("user3").get();

        Wallet user1Wallet = cashFacade.findWalletByHolder(user1Member).get();

        if (user1Wallet.hasBalance()) {
            return;
        }

        user1Wallet.credit(150_000, CashLog.EventType.충전_무통장입금);
        user1Wallet.credit(100_000, CashLog.EventType.충전_무통장입금);
        user1Wallet.credit(50_000, CashLog.EventType.충전_무통장입금);

        Wallet user2Wallet = cashFacade.findWalletByHolder(user2Member).get();

        if (user2Wallet.hasBalance()) return;

        user2Wallet.credit(150_000, CashLog.EventType.충전_무통장입금);

        Wallet user3Wallet = cashFacade.findWalletByHolder(user3Member).get();

        if (user3Wallet.hasBalance()) return;

        user3Wallet.credit(100_000, CashLog.EventType.충전_무통장입금);
    }
}
