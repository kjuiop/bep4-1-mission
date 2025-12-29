package com.back.boundedcontext.member.out.apiclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Service
public class MemberApiClient {

    private final RestClient restClient;

    public MemberApiClient(@Value("${custom.global.internalBackUrl}") String internalBackUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(internalBackUrl + "/api/v1/members")
                .build();
    }

    public String getRandomSecureTip() {
        return restClient.get()
                .uri("/random-secure-tip")
                .retrieve()
                .body(String.class);
    }
}
