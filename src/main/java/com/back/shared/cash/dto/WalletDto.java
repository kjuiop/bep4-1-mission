package com.back.shared.cash.dto;

import com.back.boundedcontext.cash.domain.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WalletDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long holderId;
    private String holderName;
    private long balance;

    public WalletDto(Wallet wallet) {
        this(
                wallet.getId(),
                wallet.getCreateDate(),
                wallet.getModifyDate(),
                wallet.getHolder().getId(),
                wallet.getHolder().getUsername(),
                wallet.getBalance()
        );
    }
}
