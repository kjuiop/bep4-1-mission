package com.back.boundedcontext.cash.in.api.v1;

import com.back.boundedcontext.cash.app.CashFacade;
import com.back.shared.cash.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@RestController
@RequestMapping("/api/v1/cash")
@RequiredArgsConstructor
public class CashController {

    private final CashFacade cashFacade;

    @GetMapping("/wallets/by-holder/{holderId}")
    @Transactional(readOnly = true)
    public WalletDto getItemByHolder(@PathVariable long holderId) {
        return cashFacade
                .findWalletByHolderId(holderId)
                .map(WalletDto::new)
                .get();
    }
}
