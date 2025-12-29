package com.back.shared.cash.out;

import com.back.shared.cash.dto.WalletDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
public class WalletApiClient {

    private static final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080/cash/api/v1")
            .build();

    public WalletDto getItemByHolderId(long holderId) {
        return restClient.get()
                .uri("/wallets/by-holder/" + holderId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public long getBalanceByHolderId(long holderId) {
        WalletDto walletDto = getItemByHolderId(holderId);
        return walletDto.getBalance();
    }
}
