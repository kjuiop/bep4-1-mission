package com.back.shared.cash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@AllArgsConstructor
@Getter
public class WalletDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long holderId;
    private String holderName;
    private long balance;
}
