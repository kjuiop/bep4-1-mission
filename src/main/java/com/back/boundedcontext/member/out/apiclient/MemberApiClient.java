package com.back.boundedcontext.member.out.apiclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Service
public class MemberApiClient {

    private static final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080/api/v1/members")
            .build();

    public static String getRandomSecureTip() {
        return restClient.get()
                .uri("/random-secure-tip")
                .retrieve()
                .body(String.class);
    }
}
