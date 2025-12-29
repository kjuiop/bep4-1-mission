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
@RequestMapping("/cash/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final CashFacade cashFacade;

    @GetMapping("/by-holder/{holderId}")
    @Transactional(readOnly = true)
    public WalletDto getItemByHolder(@PathVariable long holderId) {
        return cashFacade
                .findWalletByHolderId(holderId)
                .map(WalletDto::new)
                .get();
    }
}
